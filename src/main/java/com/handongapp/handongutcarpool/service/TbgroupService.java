package com.handongapp.handongutcarpool.service;

import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbgroupDto;
import org.springframework.stereotype.Service;

@Service
public interface TbgroupService {
    CommonDto.IdResDto create(TbgroupDto.CreateReqDto param, String currentUserId);
    CommonDto.IdResDto toggleLock(TbgroupDto.LockReqDto param, String currentUserId);
    CommonDto.IdResDto updateStatus(TbgroupDto.UpdateStatusReqDto param, String currentUserId);
    TbgroupDto.DetailResDto getDetail(TbgroupDto.DetailReqDto param, String currentUserId);
}
