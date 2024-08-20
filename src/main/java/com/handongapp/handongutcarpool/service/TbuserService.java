package com.handongapp.handongutcarpool.service;

import com.handongapp.handongutcarpool.dto.TbuserDto;
import org.springframework.stereotype.Service;

@Service
public interface TbuserService {
    TbuserDto.CreateResDto createOrUpdate(TbuserDto.CreateReqDto param);

}
