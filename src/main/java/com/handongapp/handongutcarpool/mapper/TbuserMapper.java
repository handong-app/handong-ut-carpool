package com.handongapp.handongutcarpool.mapper;

import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbuserDto;

public interface TbuserMapper {
    TbuserDto.DetailFromUserServDto getDetailFromUser(CommonDto.IdReqDto param);

}
