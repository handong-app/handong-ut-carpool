package com.handongapp.handongutcarpool.mapper;

import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbgroupDto;
import org.apache.ibatis.annotations.Update;

public interface TbgroupMapper {
    void groupAdminLeave(CommonDto.IdReqDto param);
    TbgroupDto.DetailFromGroupServDto getDetailFromGroup(CommonDto.IdReqDto param);
}
