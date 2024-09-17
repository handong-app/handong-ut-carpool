package com.handongapp.handongutcarpool.service.impl;

import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbgroupDto;
import com.handongapp.handongutcarpool.exception.GroupAlreadyCreatedException;
import com.handongapp.handongutcarpool.exception.NoAuthorizationException;
import com.handongapp.handongutcarpool.exception.NoMatchingDataException;
import com.handongapp.handongutcarpool.repository.TbgroupRepository;
import com.handongapp.handongutcarpool.repository.TbgroupTbuserRepository;
import com.handongapp.handongutcarpool.repository.TbuserRepository;
import com.handongapp.handongutcarpool.service.TbgroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TbgroupServiceImpl implements TbgroupService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbgroupRepository tbgroupRepository;
    private final TbuserRepository tbuserRepository;
    private final TbgroupTbuserRepository tbgroupTbuserRepository;

    public TbgroupServiceImpl(TbgroupRepository tbgroupRepository, TbuserRepository tbuserRepository, TbgroupTbuserRepository tbgroupTbuserRepository) {
        this.tbgroupRepository = tbgroupRepository;
        this.tbuserRepository = tbuserRepository;
        this.tbgroupTbuserRepository = tbgroupTbuserRepository;
    }

    @Override
    public CommonDto.IdResDto create(TbgroupDto.CreateReqDto param){
        return tbuserRepository.findById(param.getTbuserId())
                .map(existingTbuser -> {
                    CommonDto.IdResDto res = tbgroupRepository.save(param.toEntity()).toIdResDto();
                    enterGroupAfterCreate(TbgroupDto.EnterGroupReqDto.builder().tbgroupId(res.getId()).tbuserId(param.getTbuserId()).build());
                    return res;
                })
                .orElseThrow(() -> new NoMatchingDataException("User Not Exists"));
    }

    private void enterGroupAfterCreate(TbgroupDto.EnterGroupReqDto param) {
        tbgroupTbuserRepository.findByTbgroupIdAndTbuserId(param.getTbuserId(), param.getTbuserId())
                .map(existingTbgroupTbuser -> {
                    throw new GroupAlreadyCreatedException("Group Already Exists");
                })
                .orElse(tbgroupTbuserRepository.save(param.toEntity()));
    }

    @Override
    public CommonDto.IdResDto toggleLock(TbgroupDto.LockReqDto param){
        return tbgroupRepository.findById(param.getTbgroupId())
                .map(existingTbgroup -> {
                    if(existingTbgroup.getTbuserId().equals(param.getTbuserId())){
                        existingTbgroup.setLocked(!existingTbgroup.getLocked());
                        return tbgroupRepository.save(existingTbgroup).toIdResDto();
                    }
                    else throw new NoAuthorizationException("Access denied to the group");
                })
                .orElseThrow(() -> new NoMatchingDataException("Group not found"));
    }

    @Override
    public CommonDto.IdResDto updateStatus(TbgroupDto.UpdateStatusReqDto param){
        return tbgroupRepository.findById(param.getTbgroupId())
                .map(existingTbgroup -> {
                    if(existingTbgroup.getTbuserId().equals(param.getTbuserId())){
                        existingTbgroup.setStatus(param.getStatus());
                        return tbgroupRepository.save(existingTbgroup).toIdResDto();
                    }
                    else throw new NoAuthorizationException("Access denied to the group");
                })
                .orElseThrow(() -> new NoMatchingDataException("Group not found"));
    }

}
