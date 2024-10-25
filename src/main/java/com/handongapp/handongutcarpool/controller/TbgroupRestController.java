package com.handongapp.handongutcarpool.controller;

import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbgroupDto;
import com.handongapp.handongutcarpool.service.TbgroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "0-2. 그룹 API 안내",
        description = "그룹 관련 기능 정의한 RestController."
)
@RequestMapping("/api/tbgroup")
@RestController
public class TbgroupRestController {
    private final TbgroupService tbgroupService;
    public TbgroupRestController(TbgroupService tbgroupService) {
        this.tbgroupService = tbgroupService;
    }

    @Operation(summary = "그룹 생성",
            description = "그룹 생성 컨트롤러 <br />"
                    + "@param TbgroupDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<BasicDto.IdResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<CommonDto.IdResDto> create(@Valid @RequestBody TbgroupDto.CreateReqDto param){
        return ResponseEntity.status(HttpStatus.CREATED).body(tbgroupService.create(param));
    }

    @Operation(summary = "그룹 잠그기 토글",
            description = "그룹에 더이상 새로운 유저를 받지 않음 (토글) <br />"
                    + "@param TbgroupDto.LockReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<BasicDto.IdResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/toggle/lock")
    public ResponseEntity<CommonDto.IdResDto> lock(@Valid @RequestBody TbgroupDto.LockReqDto param){
        return ResponseEntity.status(HttpStatus.OK).body(tbgroupService.toggleLock(param));
    }

    @Operation(summary = "그룹 상태 변경",
            description = "그룹 상태 변경 ( recruiting / inprogress ) <br />"
                    + "@param TbgroupDto.UpdateStatusReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<BasicDto.IdResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/update/status")
    public ResponseEntity<CommonDto.IdResDto> updateStatus(@Valid @RequestBody TbgroupDto.UpdateStatusReqDto param){
        return ResponseEntity.status(HttpStatus.OK).body(tbgroupService.updateStatus(param));
    }

    @Operation(summary = "그룹 상세정보",
            description = "그룹 상세정보 가져오기<br />"
                    + "@param TbgroupDto.DetailReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbgroupDto.DetailResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/detail")
    public ResponseEntity<TbgroupDto.DetailResDto> getDetail(@Valid @RequestBody TbgroupDto.DetailReqDto param){
        return ResponseEntity.status(HttpStatus.OK).body(tbgroupService.getDetail(param));
    }
}
