package com.handongapp.handongutcarpool.service;

import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbuserDto;
import org.springframework.stereotype.Service;


@Service
public interface TbuserService {
    CommonDto.IdResDto createOrUpdate(TbuserDto.CreateReqDto param);
    CommonDto.IdResDto updatePenalty(TbuserDto.UpdatePenaltyReqDto param);
}
