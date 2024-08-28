package com.handongapp.handongutcarpool.service.impl;


import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;
import com.handongapp.handongutcarpool.exception.NoMatchingDataException;
import com.handongapp.handongutcarpool.exception.UserAlreadyInGroupException;
import com.handongapp.handongutcarpool.repository.TbgroupRepository;
import com.handongapp.handongutcarpool.repository.TbuserRepository;
import com.handongapp.handongutcarpool.repository.TbuserTbgroupRepository;
import com.handongapp.handongutcarpool.service.TbgroupTbuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TbgroupTbuserServiceImpl implements TbgroupTbuserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbuserRepository tbuserRepository;
    private final TbgroupRepository tbgroupRepository;
    private final TbuserTbgroupRepository tbuserTbgroupRepository;

    public TbgroupTbuserServiceImpl(TbuserRepository tbuserRepository, TbgroupRepository tbgroupRepository, TbuserTbgroupRepository tbuserTbgroupRepository) {
        this.tbuserRepository = tbuserRepository;
        this.tbgroupRepository = tbgroupRepository;
        this.tbuserTbgroupRepository = tbuserTbgroupRepository;
    }

    @Override
    public TbgroupTbuserDto.EnterGroupResDto enter(TbgroupTbuserDto.EnterGroupReqDto param){
        return tbgroupRepository.findById(param.getTbgroupId())
                .map(existingTbgroup -> {
                    if (!tbuserRepository.existsById(param.getTbuserId())) {
                        throw new NoMatchingDataException("User not found");
                    }
                    if (Boolean.TRUE.equals(isUserInGroup(TbgroupTbuserDto.IsUserInGroupServDto.builder().tbuserId(param.getTbuserId()).tbgroupId(param.getTbgroupId()).build()))) {
                        throw new UserAlreadyInGroupException("User Already Exists");
                    }
                    else {
                        return tbuserTbgroupRepository.save(param.toEntity()).toEnterGroupResDto();
                    }
                })
                .orElseThrow(() -> new NoMatchingDataException("Group not found"));
    }

    public Boolean isUserInGroup(TbgroupTbuserDto.IsUserInGroupServDto param){
        return tbuserTbgroupRepository.existsByTbgroupIdAndTbuserId(param.getTbgroupId(), param.getTbuserId());
    }

}