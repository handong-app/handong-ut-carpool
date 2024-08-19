package com.handongapp.handongutcarpool.service.impl;

import com.handongapp.handongutcarpool.dto.TbuserDto;
import com.handongapp.handongutcarpool.repository.TbuserRepository;
import com.handongapp.handongutcarpool.service.TbuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TbuserServiceImpl implements TbuserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbuserRepository tbuserRepository;

    public TbuserServiceImpl(TbuserRepository tbuserRepository) {
        this.tbuserRepository = tbuserRepository;
    }

    @Override
    public TbuserDto.CreateResDto create(TbuserDto.CreateReqDto param){
        return tbuserRepository.save(param.toEntity()).toCreateResDto();
    }
}
