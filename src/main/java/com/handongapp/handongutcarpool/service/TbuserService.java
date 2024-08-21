package com.handongapp.handongutcarpool.service;

import com.handongapp.handongutcarpool.dto.TbuserDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TbuserService {
    TbuserDto.CreateResDto createOrUpdate(TbuserDto.CreateReqDto param);
    Optional<TbuserDto.CreateResDto> updatePenalty(TbuserDto.UpdatePenaltyReqDto param);
}
