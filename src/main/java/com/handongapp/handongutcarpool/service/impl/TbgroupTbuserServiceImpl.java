package com.handongapp.handongutcarpool.service.impl;


import com.handongapp.handongutcarpool.domain.Tbgroup;
import com.handongapp.handongutcarpool.dto.BasicDto;
import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;
import com.handongapp.handongutcarpool.exception.GroupFullException;
import com.handongapp.handongutcarpool.exception.GroupLockedException;
import com.handongapp.handongutcarpool.exception.NoMatchingDataException;
import com.handongapp.handongutcarpool.exception.UserAlreadyInGroupException;
import com.handongapp.handongutcarpool.mapper.TbgroupTbuserMapper;
import com.handongapp.handongutcarpool.repository.TbgroupRepository;
import com.handongapp.handongutcarpool.repository.TbuserRepository;
import com.handongapp.handongutcarpool.repository.TbuserTbgroupRepository;
import com.handongapp.handongutcarpool.service.TbgroupTbuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TbgroupTbuserServiceImpl implements TbgroupTbuserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbuserRepository tbuserRepository;
    private final TbgroupRepository tbgroupRepository;
    private final TbuserTbgroupRepository tbuserTbgroupRepository;
    private final TbgroupTbuserMapper tbgroupTbuserMapper;

    public TbgroupTbuserServiceImpl(TbuserRepository tbuserRepository, TbgroupRepository tbgroupRepository, TbuserTbgroupRepository tbuserTbgroupRepository, TbgroupTbuserMapper tbgroupTbuserMapper) {
        this.tbuserRepository = tbuserRepository;
        this.tbgroupRepository = tbgroupRepository;
        this.tbuserTbgroupRepository = tbuserTbgroupRepository;
        this.tbgroupTbuserMapper = tbgroupTbuserMapper;
    }

    @Override
    public TbgroupTbuserDto.EnterGroupResDto enter(TbgroupTbuserDto.EnterGroupReqDto param){
        return tbgroupRepository.findById(param.getTbgroupId())
                .map(existingTbgroup -> {
                    isValid(param);
                    return tbuserTbgroupRepository.save(param.toEntity()).toEnterGroupResDto();
                })
                .orElseThrow(() -> new NoMatchingDataException("Group not found"));
    }

    private void isValid(TbgroupTbuserDto.EnterGroupReqDto param){
        if (!tbuserRepository.existsById(param.getTbuserId())) {
            throw new NoMatchingDataException("User not found");
        }
        if (Boolean.TRUE.equals(isUserInGroup(TbgroupTbuserDto.IsUserInGroupServDto.builder().tbuserId(param.getTbuserId()).tbgroupId(param.getTbgroupId()).build()))) {
            throw new UserAlreadyInGroupException("User Already Exists");
        }
        if (Boolean.TRUE.equals(isGroupFull(BasicDto.IdReqDto.builder().id(param.getTbgroupId()).build()).getIsFull())){
            throw new GroupFullException("Group Full");
        }
        if (Boolean.TRUE.equals(isGroupLock(BasicDto.IdReqDto.builder().id(param.getTbgroupId()).build()).getIsLock())){
            throw new GroupLockedException("Group Locked");
        }
    }

    public Boolean isUserInGroup(TbgroupTbuserDto.IsUserInGroupServDto param){
        return tbuserTbgroupRepository.existsByTbgroupIdAndTbuserId(param.getTbgroupId(), param.getTbuserId());
    }

    public TbgroupTbuserDto.IsFullServDto isGroupFull(BasicDto.IdReqDto param){
        return TbgroupTbuserDto.IsFullServDto.builder()
                .isFull(tbgroupTbuserMapper.userCount(param).getTbuserCount() >= tbgroupRepository.findById(param.getId()).map(Tbgroup::getMaxCount).orElseThrow(() -> new NoMatchingDataException("Group not found")))
                .build();
    }

    public TbgroupTbuserDto.IsLockServDto isGroupLock(BasicDto.IdReqDto param){
        return TbgroupTbuserDto.IsLockServDto.builder().isLock(tbgroupRepository.findById(param.getId()).map(Tbgroup::getLocked).orElseThrow(() -> new NoMatchingDataException("Group not found"))).build();
    }

    public TbgroupTbuserDto.UserCountResDto userCount(BasicDto.IdReqDto param){
        return tbgroupTbuserMapper.userCount(param);
    }

}
