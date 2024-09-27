package com.handongapp.handongutcarpool.mapper;

import com.handongapp.handongutcarpool.dto.CommonDto;
import org.apache.ibatis.annotations.Update;

public interface TbgroupMapper {
    @Update("UPDATE tbgroup SET deleted = 'Y' WHERE id = #{id}")
    int groupAdminLeave(CommonDto.IdReqDto param);
    int getMaxLuggageCount(CommonDto.IdReqDto param);
    Boolean getIsLock(CommonDto.IdReqDto param);
}
