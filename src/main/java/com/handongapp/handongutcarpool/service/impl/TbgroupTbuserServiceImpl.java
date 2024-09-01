package com.handongapp.handongutcarpool.service.impl;


import com.handongapp.handongutcarpool.domain.Tbgroup;
import com.handongapp.handongutcarpool.domain.TbgroupTbuser;
import com.handongapp.handongutcarpool.dto.BasicDto;
import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;
import com.handongapp.handongutcarpool.exception.*;
import com.handongapp.handongutcarpool.mapper.TbgroupMapper;
import com.handongapp.handongutcarpool.mapper.TbgroupTbuserMapper;
import com.handongapp.handongutcarpool.repository.TbgroupRepository;
import com.handongapp.handongutcarpool.repository.TbuserRepository;
import com.handongapp.handongutcarpool.repository.TbgroupTbuserRepository;
import com.handongapp.handongutcarpool.service.TbgroupTbuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TbgroupTbuserServiceImpl implements TbgroupTbuserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbuserRepository tbuserRepository;
    private final TbgroupRepository tbgroupRepository;
    private final TbgroupTbuserRepository tbgroupTbuserRepository;
    private final TbgroupTbuserMapper tbgroupTbuserMapper;
    private final TbgroupMapper tbgroupMapper;

    public TbgroupTbuserServiceImpl(TbuserRepository tbuserRepository, TbgroupRepository tbgroupRepository, TbgroupTbuserRepository tbgroupTbuserRepository, TbgroupTbuserMapper tbgroupTbuserMapper, TbgroupMapper tbgroupMapper) {
        this.tbuserRepository = tbuserRepository;
        this.tbgroupRepository = tbgroupRepository;
        this.tbgroupTbuserRepository = tbgroupTbuserRepository;
        this.tbgroupTbuserMapper = tbgroupTbuserMapper;
        this.tbgroupMapper = tbgroupMapper;
    }

    @Override
    public TbgroupTbuserDto.EnterGroupResDto enter(TbgroupTbuserDto.EnterGroupReqDto param){
        return tbgroupRepository.findById(param.getTbgroupId())
                .map(existingTbgroup -> tbgroupTbuserRepository.save(
                        validatedAndReturn(param)
                                .map(tbuserTbgroup -> {
                                    tbuserTbgroup.setDeleted("N");
                                    return tbuserTbgroup;
                                })
                                .orElse(param.toEntity())
                ).toEnterGroupResDto())
                .orElseThrow(() -> new NoMatchingDataException("Group not found"));
    }

    @Override
    public TbgroupTbuserDto.LeaveGroupResDto leave(TbgroupTbuserDto.LeaveGroupReqDto param){
        tbgroupTbuserRepository.save(
                tbgroupTbuserRepository.findByTbgroupIdAndTbuserId(param.getTbgroupId(), param.getTbuserId())
                        .map(tbuserTbgroup -> {
                        if(tbuserTbgroup.getDeleted().equals("Y")) throw new UserAlreadyLeavedException("User is already leaved");
                        tbuserTbgroup.setDeleted("Y");
                        if(tbuserTbgroup.getRole().equals("group_admin")){
                            tbgroupMapper.groupAdminLeave(BasicDto.IdReqDto.builder().id(param.getTbgroupId()).build());
                        }
                        return tbuserTbgroup;
                    })
                    .orElseThrow(() -> new NoMatchingDataException("Group or User not found"))
        );
        return TbgroupTbuserDto.LeaveGroupResDto.builder().tbgroupId(param.getTbgroupId()).tbuserId(param.getTbuserId()).message("success").build();
    }


    private Optional<TbgroupTbuser> validatedAndReturn(TbgroupTbuserDto.EnterGroupReqDto param){
        if (!tbuserRepository.existsById(param.getTbuserId())) {
            throw new NoMatchingDataException("User not found");
        }
        return tbgroupTbuserRepository.findByTbgroupIdAndTbuserId(param.getTbgroupId(), param.getTbuserId())
                .map(
                    tbgroupTbuser -> {
                        if(tbgroupTbuser.getDeleted().equals("N")) throw new UserAlreadyInGroupException("User is already in group");
                        if (Boolean.TRUE.equals(isGroupFull(BasicDto.IdReqDto.builder().id(param.getTbgroupId()).build()).getIsFull())){
                            throw new GroupFullException("Group Full");
                        }
                        if (Boolean.TRUE.equals(isGroupLock(BasicDto.IdReqDto.builder().id(param.getTbgroupId()).build()).getIsLock())){
                            throw new GroupLockedException("Group Locked");
                        }
                        return tbgroupTbuser;
                    }
                );
    }

    public TbgroupTbuserDto.IsFullServDto isGroupFull(BasicDto.IdReqDto param){
        return TbgroupTbuserDto.IsFullServDto.builder()
                .isFull(tbgroupTbuserMapper.userCount(param).getTbuserCount() >= tbgroupRepository.findById(param.getId()).map(Tbgroup::getMaxCount).orElseThrow(() -> new NoMatchingDataException("Group not found")))
                .build();
    }

    public TbgroupTbuserDto.IsLockServDto isGroupLock(BasicDto.IdReqDto param){
        return TbgroupTbuserDto.IsLockServDto.builder().isLock(tbgroupRepository.findById(param.getId()).map(Tbgroup::getLocked).orElseThrow(() -> new NoMatchingDataException("Group not found"))).build();
    }

    public TbgroupTbuserDto.UserCountResDto userCount(BasicDto.IdReqDto param){
        return tbgroupTbuserMapper.userCount(param);
    }

}
