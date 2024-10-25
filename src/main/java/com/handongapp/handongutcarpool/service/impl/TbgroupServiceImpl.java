package com.handongapp.handongutcarpool.service.impl;

import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbgroupDto;
import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;
import com.handongapp.handongutcarpool.dto.TbuserDto;
import com.handongapp.handongutcarpool.exception.GroupAlreadyCreatedException;
import com.handongapp.handongutcarpool.exception.NoAuthorizationException;
import com.handongapp.handongutcarpool.exception.NoMatchingDataException;
import com.handongapp.handongutcarpool.mapper.TbgroupMapper;
import com.handongapp.handongutcarpool.mapper.TbgroupTbuserMapper;
import com.handongapp.handongutcarpool.mapper.TbuserMapper;
import com.handongapp.handongutcarpool.repository.TbgroupRepository;
import com.handongapp.handongutcarpool.repository.TbgroupTbuserRepository;
import com.handongapp.handongutcarpool.repository.TbuserRepository;
import com.handongapp.handongutcarpool.service.TbgroupService;
import com.handongapp.handongutcarpool.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TbgroupServiceImpl implements TbgroupService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbgroupRepository tbgroupRepository;
    private final TbuserRepository tbuserRepository;
    private final TbgroupTbuserRepository tbgroupTbuserRepository;
    private final TbgroupMapper tbgroupMapper;
    private final TbuserMapper tbuserMapper;
    private final TbgroupTbuserMapper tbgroupTbuserMapper;

    public TbgroupServiceImpl(TbgroupRepository tbgroupRepository, TbuserRepository tbuserRepository, TbgroupTbuserRepository tbgroupTbuserRepository, TbgroupMapper tbgroupMapper, TbuserMapper tbuserMapper, TbgroupTbuserMapper tbgroupTbuserMapper) {
        this.tbgroupRepository = tbgroupRepository;
        this.tbuserRepository = tbuserRepository;
        this.tbgroupTbuserRepository = tbgroupTbuserRepository;
        this.tbgroupMapper = tbgroupMapper;
        this.tbuserMapper = tbuserMapper;
        this.tbgroupTbuserMapper = tbgroupTbuserMapper;
    }

    @Override
    public CommonDto.IdResDto create(TbgroupDto.CreateReqDto param, String currentUserId){
        return tbuserRepository.findById(currentUserId)
                .map(existingTbuser -> {
                    CommonDto.IdResDto res = tbgroupRepository.save(param.toEntity(currentUserId)).toIdResDto();
                    enterGroupAfterCreate(TbgroupDto.EnterGroupAdminReqDto.builder().tbgroupId(res.getId()).tbuserId(currentUserId).build());
                    return res;
                })
                .orElseThrow(() -> new NoMatchingDataException("User Not Exists"));
    }

    private void enterGroupAfterCreate(TbgroupDto.EnterGroupAdminReqDto param) {
        tbgroupTbuserRepository.findByTbgroupIdAndTbuserId(param.getTbuserId(), param.getTbuserId())
                .map(existingTbgroupTbuser -> {
                    throw new GroupAlreadyCreatedException("Group Already Exists");
                })
                .orElse(tbgroupTbuserRepository.save(param.toEntity()));
    }

    @Override
    public CommonDto.IdResDto toggleLock(TbgroupDto.LockReqDto param, String currentUserId){
        return tbgroupRepository.findById(param.getTbgroupId())
                .map(existingTbgroup -> {
                    if(existingTbgroup.getTbuserId().equals(currentUserId)){
                        existingTbgroup.setLocked(!existingTbgroup.getLocked());
                        return tbgroupRepository.save(existingTbgroup).toIdResDto();
                    }
                    else throw new NoAuthorizationException("Access denied to the group");
                })
                .orElseThrow(() -> new NoMatchingDataException("Group not found"));
    }

    @Override
    public CommonDto.IdResDto updateStatus(TbgroupDto.UpdateStatusReqDto param, String currentUserId){
        return tbgroupRepository.findById(param.getTbgroupId())
                .map(existingTbgroup -> {
                    if(existingTbgroup.getTbuserId().equals(currentUserId)){
                        existingTbgroup.setStatus(param.getStatus());
                        return tbgroupRepository.save(existingTbgroup).toIdResDto();
                    }
                    else throw new NoAuthorizationException("Access denied to the group");
                })
                .orElseThrow(() -> new NoMatchingDataException("Group not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public TbgroupDto.DetailResDto getDetail(TbgroupDto.DetailReqDto param, String currentUserId){
        TbgroupDto.DetailFromGroupServDto detailFromGroup = tbgroupMapper.getDetailFromGroup(param.toIdReqDtoGroup());
        if (detailFromGroup == null) throw new NoMatchingDataException("Group not found");

        TbuserDto.DetailFromUserServDto detailFromUser = tbuserMapper.getDetailFromUser(detailFromGroup.getGroupLeaderToIdReqDto());
        if (detailFromUser == null) throw new NoMatchingDataException("Group Leader not found");

        TbgroupTbuserDto.DetailFromGroupUserServDto detailFromGroupUser = tbgroupTbuserMapper.getDetailFromGroupUser(param.toIdReqDtoGroup());
        if (detailFromGroupUser == null) throw new NoMatchingDataException("Passenger not found");

        TbgroupTbuserDto.IsUserInGroupServDto isUserInGroupServDto = tbgroupTbuserMapper.isUserInGroup(param.toIdReqDtoUser(currentUserId));

        return TbgroupDto.DetailResDto.of(detailFromGroup, detailFromUser, detailFromGroupUser, isUserInGroupServDto);
    }

}
