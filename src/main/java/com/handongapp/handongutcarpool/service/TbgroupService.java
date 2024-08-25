package com.handongapp.handongutcarpool.service;

import com.handongapp.handongutcarpool.dto.BasicDto;
import com.handongapp.handongutcarpool.dto.TbgroupDto;
import org.springframework.stereotype.Service;

@Service
public interface TbgroupService {
    BasicDto.IdResDto create(TbgroupDto.CreateReqDto param);
    BasicDto.IdResDto toggleLock(TbgroupDto.LockReqDto param);
    BasicDto.IdResDto updateStatus(TbgroupDto.UpdateStatusReqDto param);
}
