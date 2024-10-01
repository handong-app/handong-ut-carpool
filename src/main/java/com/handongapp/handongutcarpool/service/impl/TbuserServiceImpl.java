package com.handongapp.handongutcarpool.service.impl;

import com.handongapp.handongutcarpool.domain.RoleType;
import com.handongapp.handongutcarpool.domain.Tbuser;
import com.handongapp.handongutcarpool.domain.TbuserRoleType;
import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbuserDto;
import com.handongapp.handongutcarpool.exception.NoMatchingDataException;
import com.handongapp.handongutcarpool.repository.RoleTypeRepository;
import com.handongapp.handongutcarpool.repository.TbuserRepository;
import com.handongapp.handongutcarpool.repository.TbuserRoleTypeRepository;
import com.handongapp.handongutcarpool.service.TbuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class TbuserServiceImpl implements TbuserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbuserRepository tbuserRepository;
    private final RoleTypeRepository roleTypeRepository;
    private final TbuserRoleTypeRepository tbuserRoleTypeRepository;

    public TbuserServiceImpl(TbuserRepository tbuserRepository, RoleTypeRepository roleTypeRepository, TbuserRoleTypeRepository tbuserRoleTypeRepository) {
        this.tbuserRepository = tbuserRepository;
        this.roleTypeRepository = roleTypeRepository;
        this.tbuserRoleTypeRepository = tbuserRoleTypeRepository;
    }

    @Override
    public CommonDto.IdResDto createOrUpdate(TbuserDto.CreateReqDto param){
        return tbuserRepository.findByHakbun(param.getHakbun())
                // 기존 유저가 존재시 실행
                .map(existingTbuser -> {
                    existingTbuser.setName(param.getName());
                    existingTbuser.setPhoneNumber(param.getPhoneNumber());
                    return tbuserRepository.save(existingTbuser).toIdResDto();
                })
                // 신규 유저일 때 실행
                .orElseGet(() -> {
                    Tbuser tbuser = tbuserRepository.save(param.toEntity());
                    RoleType roleType = roleTypeRepository.findByTypeName("ROLE_USER");
                    TbuserRoleType tbuserRoleType = TbuserRoleType.of(tbuser, roleType);
                    tbuserRoleTypeRepository.save(tbuserRoleType);
                    return tbuser.toIdResDto();
                }
                );
    }

    @Override
    public CommonDto.IdResDto updatePenalty(TbuserDto.UpdatePenaltyReqDto param){
        return tbuserRepository.findById(param.getId())
                .map(existingTbuser -> {
                    existingTbuser.setPenaltyUntil(param.getPenaltyUntil());
                    return tbuserRepository.save(existingTbuser).toIdResDto();
                })
                .orElseThrow(() -> new NoMatchingDataException("User Not Exists"));
    }
}
