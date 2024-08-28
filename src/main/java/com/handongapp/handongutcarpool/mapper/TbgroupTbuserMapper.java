package com.handongapp.handongutcarpool.mapper;

import com.handongapp.handongutcarpool.dto.BasicDto;
import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;

public interface TbgroupTbuserMapper {
    TbgroupTbuserDto.UserCountResDto userCount(BasicDto.IdReqDto param);
}
