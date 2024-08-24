package com.handongapp.handongutcarpool.service;

import com.handongapp.handongutcarpool.dto.BasicDto;
import com.handongapp.handongutcarpool.dto.TbgroupDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TbgroupService {
    Optional<BasicDto.IdResDto> create(TbgroupDto.CreateReqDto param);
    Optional<BasicDto.IdResDto> lock(TbgroupDto.LockReqDto param);
}
