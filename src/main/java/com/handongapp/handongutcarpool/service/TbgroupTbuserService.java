package com.handongapp.handongutcarpool.service;

import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;
import org.springframework.stereotype.Service;

@Service
public interface TbgroupTbuserService {
    TbgroupTbuserDto.EnterGroupResDto enter(TbgroupTbuserDto.EnterGroupReqDto param, String currentUserId);
    TbgroupTbuserDto.LeaveGroupResDto leave(TbgroupTbuserDto.LeaveGroupReqDto param, String currentUserId);
    TbgroupTbuserDto.PassengerCountResDto getCurrentPassengerCount(CommonDto.IdReqDto param);
}
