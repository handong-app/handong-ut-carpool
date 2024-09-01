package com.handongapp.handongutcarpool.mapper;

import com.handongapp.handongutcarpool.dto.BasicDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TbgroupMapper {
    @Update("UPDATE tbgroup SET deleted = 'Y' WHERE id = #{id}")
    int groupAdminLeave(BasicDto.IdReqDto param);
}
