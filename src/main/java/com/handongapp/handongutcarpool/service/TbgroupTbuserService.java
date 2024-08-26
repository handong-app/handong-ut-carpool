package com.handongapp.handongutcarpool.service;

import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;
import org.springframework.stereotype.Service;

@Service
public interface TbgroupTbuserService {
    TbgroupTbuserDto.EnterGroupResDto enter(TbgroupTbuserDto.EnterGroupReqDto param);
}
