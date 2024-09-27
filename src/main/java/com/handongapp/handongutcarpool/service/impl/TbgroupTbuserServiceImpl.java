package com.handongapp.handongutcarpool.service.impl;


import com.handongapp.handongutcarpool.domain.TbgroupTbuser;
import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;
import com.handongapp.handongutcarpool.exception.*;
import com.handongapp.handongutcarpool.mapper.TbgroupMapper;
import com.handongapp.handongutcarpool.mapper.TbgroupTbuserMapper;
import com.handongapp.handongutcarpool.repository.TbgroupRepository;
import com.handongapp.handongutcarpool.repository.TbuserRepository;
import com.handongapp.handongutcarpool.repository.TbgroupTbuserRepository;
import com.handongapp.handongutcarpool.service.TbgroupTbuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class TbgroupTbuserServiceImpl implements TbgroupTbuserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbuserRepository tbuserRepository;
    private final TbgroupRepository tbgroupRepository;
    private final TbgroupTbuserRepository tbgroupTbuserRepository;
    private final TbgroupTbuserMapper tbgroupTbuserMapper;
    private final TbgroupMapper tbgroupMapper;

    public TbgroupTbuserServiceImpl(TbuserRepository tbuserRepository, TbgroupRepository tbgroupRepository, TbgroupTbuserRepository tbgroupTbuserRepository, TbgroupTbuserMapper tbgroupTbuserMapper, TbgroupMapper tbgroupMapper) {
        this.tbuserRepository = tbuserRepository;
        this.tbgroupRepository = tbgroupRepository;
        this.tbgroupTbuserRepository = tbgroupTbuserRepository;
        this.tbgroupTbuserMapper = tbgroupTbuserMapper;
        this.tbgroupMapper = tbgroupMapper;
    }

    @Override
    public TbgroupTbuserDto.EnterGroupResDto enter(TbgroupTbuserDto.EnterGroupReqDto param){
        return tbgroupRepository.findById(param.getTbgroupId())
                .map(existingTbgroup -> {
                    if(existingTbgroup.getDeleted().equals("Y")) throw new NoMatchingDataException("Group deleted");
                    return tbgroupTbuserRepository
                            .save(validatedAndReturn(param.toServDto(existingTbgroup.getMaxPassengers(), existingTbgroup.getMaxLuggage(), existingTbgroup.getLocked())))
                            .toEnterGroupResDto();
                })
                .orElseThrow(() -> new NoMatchingDataException("Group not found"));
    }

    @Override
    public TbgroupTbuserDto.LeaveGroupResDto leave(TbgroupTbuserDto.LeaveGroupReqDto param){
        tbgroupTbuserRepository.save(
                tbgroupTbuserRepository.findByTbgroupIdAndTbuserId(param.getTbgroupId(), param.getTbuserId())
                        .map(tbuserTbgroup -> {
                            if(tbuserTbgroup.getDeleted().equals("Y")) throw new UserAlreadyLeavedException("User is already leaved");
                            tbuserTbgroup.setDeleted("Y");
                            if(tbuserTbgroup.getRole().equals("group_admin")){
                                tbgroupMapper.groupAdminLeave(CommonDto.IdReqDto.builder().id(param.getTbgroupId()).build());
                            }
                            return tbuserTbgroup;})
                        .orElseThrow(() -> new NoMatchingDataException("Group or User not found"))
        );
        return TbgroupTbuserDto.LeaveGroupResDto.builder().tbgroupId(param.getTbgroupId()).tbuserId(param.getTbuserId()).message("success").build();
    }

    private TbgroupTbuser validatedAndReturn(TbgroupTbuserDto.EnterGroupServDto param){
        if (!tbuserRepository.existsById(param.getTbuserId())) {
            throw new NoMatchingDataException("User not found");
        }

        TbgroupTbuserDto.UserInGroupServDto userInGroupServDto = validateIfUserInGroup(param);
        if (Boolean.TRUE.equals(userInGroupServDto.getIsRecordPresent()) && Boolean.FALSE.equals(userInGroupServDto.getIsUserLeft())){
            throw new UserAlreadyInGroupException("User is already in group");
        }
        if (Boolean.TRUE.equals(param.getGroupIsLock())){
            throw new GroupLockedException("Group Locked");
        }
        if (Boolean.TRUE.equals(isGroupOverflow(param.toIsGroupOverFlowServDto()))){
            throw new GroupFullException("Group Full");
        }

        if (Boolean.TRUE.equals(isLuggageOverflow(param.toIsLuggageOverflowServDto()))){
            throw new GroupFullException("Group Luggage Overflow");
        }

        if (Boolean.TRUE.equals(userInGroupServDto.getIsUserLeft())){
            return userInGroupServDto.getExistingTbgroupTbuser().reEnter(param);
        }
        else return param.toEntity();
    }

    public TbgroupTbuserDto.UserInGroupServDto validateIfUserInGroup(TbgroupTbuserDto.EnterGroupServDto param) {
        return tbgroupTbuserRepository.findByTbgroupIdAndTbuserId(param.getTbgroupId(), param.getTbuserId())
                .map(tbgroupTbuser -> {
                            if (tbgroupTbuser.getDeleted().equals("Y")) {
                                return TbgroupTbuserDto.UserInGroupServDto.builder()
                                        .isRecordPresent(true)
                                        .isUserLeft(true)
                                        .existingTbgroupTbuser(tbgroupTbuser)
                                        .build();
                            } else {
                                return TbgroupTbuserDto.UserInGroupServDto.builder()
                                        .isRecordPresent(true)
                                        .isUserLeft(false)
                                        .existingTbgroupTbuser(null)
                                        .build();
                            }
                        })
                .orElse(TbgroupTbuserDto.UserInGroupServDto.builder()
                        .isRecordPresent(false)
                        .isUserLeft(false)
                        .existingTbgroupTbuser(null)
                        .build());

    }

    public Boolean isGroupOverflow(TbgroupTbuserDto.IsGroupOverFlowServDto param){
        return param.getPassengers() + getCurrentPassengerCount(param.toIdReqDto()).getPassengerCount() > param.getGroupMaxPassengers();
    }

    public Boolean isLuggageOverflow(TbgroupTbuserDto.IsLuggageOverflowServDto param){
        return param.getIsLuggageOverflow(getLuggageCount(param.toIdReqDto()).getLuggageCount());
    }

    public TbgroupTbuserDto.PassengerCountResDto getCurrentPassengerCount(CommonDto.IdReqDto param){
        return tbgroupTbuserMapper.getPassengerCount(param);
    }

    public TbgroupTbuserDto.LuggageCountResDto getLuggageCount(CommonDto.IdReqDto param){
        return tbgroupTbuserMapper.getLuggageCount(param);
    }

}
