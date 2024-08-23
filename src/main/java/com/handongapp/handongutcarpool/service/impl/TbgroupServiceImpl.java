package com.handongapp.handongutcarpool.service.impl;

import com.handongapp.handongutcarpool.dto.TbgroupDto;
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
    public Optional<TbgroupDto.CreateResDto> create(TbgroupDto.CreateReqDto param){

        return tbuserRepository.findById(param.getTbuserId())
                .map(existingTbuser -> tbgroupRepository.save(param.toEntity()).toCreateResDto());
    }
}
