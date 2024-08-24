package com.handongapp.handongutcarpool.service.impl;

import com.handongapp.handongutcarpool.dto.BasicDto;
import com.handongapp.handongutcarpool.dto.TbgroupDto;
import com.handongapp.handongutcarpool.exception.NoAuthorizationException;
import com.handongapp.handongutcarpool.repository.TbgroupRepository;
import com.handongapp.handongutcarpool.repository.TbuserRepository;
import com.handongapp.handongutcarpool.service.TbgroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TbgroupServiceImpl implements TbgroupService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbgroupRepository tbgroupRepository;
    private final TbuserRepository tbuserRepository;

    public TbgroupServiceImpl(TbgroupRepository tbgroupRepository, TbuserRepository tbuserRepository) {
        this.tbgroupRepository = tbgroupRepository;
        this.tbuserRepository = tbuserRepository;
    }

    @Override
    public Optional<BasicDto.IdResDto> create(TbgroupDto.CreateReqDto param){
        return tbuserRepository.findById(param.getTbuserId())
                .map(existingTbuser -> tbgroupRepository.save(param.toEntity()).toIdResDto());
    }

    @Override
    public Optional<BasicDto.IdResDto> toggleLock(TbgroupDto.LockReqDto param){
        return tbgroupRepository.findById(param.getTbgroupId())
                .map(existingTbgroup -> {
                    if(existingTbgroup.getTbuserId().equals(param.getTbuserId())){
                        existingTbgroup.setLocked(!existingTbgroup.getLocked());
                        return tbgroupRepository.save(existingTbgroup).toIdResDto();
                    }
                    else throw new NoAuthorizationException("Access denied to the group");
                });
    }

    @Override
    public Optional<BasicDto.IdResDto> updateStatus(TbgroupDto.UpdateStatusReqDto param){
        return tbgroupRepository.findById(param.getTbgroupId())
                .map(existingTbgroup -> {
                    if(existingTbgroup.getTbuserId().equals(param.getTbuserId())){
                        existingTbgroup.setStatus(param.getStatus());
                        return tbgroupRepository.save(existingTbgroup).toIdResDto();
                    }
                    else throw new NoAuthorizationException("Access denied to the group");
                });
    }

}
