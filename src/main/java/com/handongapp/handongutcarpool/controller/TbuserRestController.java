package com.handongapp.handongutcarpool.controller;

import com.handongapp.handongutcarpool.dto.TbuserDto;
import com.handongapp.handongutcarpool.service.TbuserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "0-1. 유저 API 안내",
        description = "유저 관련 기능 정의한 RestController."
)
@RequestMapping("/api/tbuser")
@RestController
public class TbuserRestController {
    private final TbuserService tbuserService;
    public TbuserRestController(TbuserService tbuserService) {
        this.tbuserService = tbuserService;
    }

    @Operation(summary = "사용자 생성",
            description = "사용자 생성 컨트롤러 <br />"
                    + "@param TbuserDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbuserDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PostMapping("/create")
    public ResponseEntity<TbuserDto.CreateResDto> create(@Valid @RequestBody TbuserDto.CreateReqDto param){
        return ResponseEntity.status(HttpStatus.CREATED).body(tbuserService.createOrUpdate(param));
    }

    @Operation(summary = "사용자 패널티 부여",
            description = "사용자 패널티 부여 컨트롤러 <br />"
                    + "@param TbuserDto.UpdatePenaltyReq <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbuserDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때, 사용자가 없을 때 등 <br />"
    )
    @PostMapping("/update/penalty")
    public ResponseEntity<TbuserDto.CreateResDto> updateStatus(@Valid @RequestBody TbuserDto.UpdatePenaltyReqDto param){
        return tbuserService.updatePenalty(param)
                .map(res -> ResponseEntity.ok(res))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(TbuserDto.CreateResDto.builder().id("User Not Exists").build()));
    }
}
