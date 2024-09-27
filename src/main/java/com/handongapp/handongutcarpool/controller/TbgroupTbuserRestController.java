package com.handongapp.handongutcarpool.controller;


import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;
import com.handongapp.handongutcarpool.service.TbgroupTbuserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "0-3. 그룹_유저 API 안내",
        description = "그룹 참여 유저 관련 기능 정의한 RestController."
)
@RequestMapping("/api/group-users")
@RestController
public class TbgroupTbuserRestController {
    private final TbgroupTbuserService tbgroupTbuserService;

    public TbgroupTbuserRestController(TbgroupTbuserService tbgroupTbuserService) {
        this.tbgroupTbuserService = tbgroupTbuserService;
    }

    @Operation(summary = "그룹 유저 입장",
            description = "그룹 유저 입장 컨트롤러 <br />"
                    + "@param TbgroupTbuserDto.EnterGroupReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbgroupTbuserDto.EnterGroupResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PostMapping("/enter")
    public ResponseEntity<TbgroupTbuserDto.EnterGroupResDto> enter(@Valid @RequestBody TbgroupTbuserDto.EnterGroupReqDto param){
        if (param.getLuggage()<0) throw new ValidationException("Luggage must be a non-negative integer");
        if (param.getPassengers()<1) throw new ValidationException("Passengers must be lager then 1");
        return ResponseEntity.status(HttpStatus.CREATED).body(tbgroupTbuserService.enter(param));
    }

    @Operation(summary = "그룹 유저 퇴장",
            description = "그룹 유저 퇴장 컨트롤러 <br />"
                    + "@param TbgroupTbuserDto.LeaveGroupReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbgroupTbuserDto.LeaveGroupResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PostMapping("/leave")
    public ResponseEntity<TbgroupTbuserDto.LeaveGroupResDto> leave(@Valid @RequestBody TbgroupTbuserDto.LeaveGroupReqDto param){
        return ResponseEntity.status(HttpStatus.CREATED).body(tbgroupTbuserService.leave(param));
    }

    @Operation(summary = "그룹 유저 카운트",
            description = "그룹 유저 카운트 컨트롤러 <br />"
                    + "@param BasicDto.IdReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbgroupTbuserDto.UserCountResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PostMapping("/count")
    public ResponseEntity<TbgroupTbuserDto.PassengerCountResDto> userCount(@Valid @RequestBody CommonDto.IdReqDto param){
        return ResponseEntity.status(HttpStatus.OK).body(tbgroupTbuserService.getPassengerCount(param));
    }
}
