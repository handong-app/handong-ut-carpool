package com.handongapp.handongutcarpool.service;

import com.handongapp.handongutcarpool.dto.TbgroupDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TbgroupService {
    Optional<TbgroupDto.CreateResDto> create(TbgroupDto.CreateReqDto param);
}
