package com.handongapp.handongutcarpool.mapper;

import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;

public interface TbgroupTbuserMapper {
    TbgroupTbuserDto.PassengerCountResDto getPassengerCount(CommonDto.IdReqDto param);
    TbgroupTbuserDto.LuggageCountResDto getLuggageCount(CommonDto.IdReqDto param);
    TbgroupTbuserDto.DetailFromGroupUserServDto getDetailFromGroupUser(CommonDto.IdReqDto param);
    TbgroupTbuserDto.IsUserInGroupServDto isUserInGroup(CommonDto.IdReqDto param);
}
