package com.handongapp.handongutcarpool.service;

import com.handongapp.handongutcarpool.dto.BasicDto;
import com.handongapp.handongutcarpool.dto.TbuserDto;
import org.springframework.stereotype.Service;


@Service
public interface TbuserService {
    BasicDto.IdResDto createOrUpdate(TbuserDto.CreateReqDto param);
    BasicDto.IdResDto updatePenalty(TbuserDto.UpdatePenaltyReqDto param);
}
