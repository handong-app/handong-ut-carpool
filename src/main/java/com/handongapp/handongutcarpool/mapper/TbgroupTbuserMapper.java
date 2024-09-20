package com.handongapp.handongutcarpool.mapper;

import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;

public interface TbgroupTbuserMapper {
    TbgroupTbuserDto.UserCountResDto getUserCount(CommonDto.IdReqDto param);
}
