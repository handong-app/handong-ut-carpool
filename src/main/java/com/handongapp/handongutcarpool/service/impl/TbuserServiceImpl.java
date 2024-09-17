package com.handongapp.handongutcarpool.service.impl;

import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbuserDto;
import com.handongapp.handongutcarpool.exception.NoMatchingDataException;
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
    public CommonDto.IdResDto createOrUpdate(TbuserDto.CreateReqDto param){
        return tbuserRepository.findByHakbun(param.getHakbun())
                // 기존 유저가 존재시 실행
                .map(existingTbuser -> {
                    existingTbuser.setName(param.getName());
                    existingTbuser.setPhoneNumber(param.getPhoneNumber());
                    return tbuserRepository.save(existingTbuser).toIdResDto();
                })
                // 신규 유저일 때 실행
                .orElseGet(() -> tbuserRepository.save(param.toEntity()).toIdResDto());
    }

    @Override
    public CommonDto.IdResDto updatePenalty(TbuserDto.UpdatePenaltyReqDto param){
        return tbuserRepository.findById(param.getId())
                .map(existingTbuser -> {
                    existingTbuser.setPenaltyUntil(param.getPenaltyUntil());
                    return tbuserRepository.save(existingTbuser).toIdResDto();
                })
                .orElseThrow(() -> new NoMatchingDataException("User Not Exists"));
    }
}
