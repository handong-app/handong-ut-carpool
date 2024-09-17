package com.handongapp.handongutcarpool.service;

import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;
import org.springframework.stereotype.Service;

@Service
public interface TbgroupTbuserService {
    TbgroupTbuserDto.EnterGroupResDto enter(TbgroupTbuserDto.EnterGroupReqDto param);
    TbgroupTbuserDto.LeaveGroupResDto leave(TbgroupTbuserDto.LeaveGroupReqDto param);
    TbgroupTbuserDto.UserCountResDto userCount(CommonDto.IdReqDto param);
}
