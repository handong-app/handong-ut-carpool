package com.handongapp.handongutcarpool.controller;

import com.handongapp.handongutcarpool.dto.BasicDto;
import com.handongapp.handongutcarpool.dto.TbgroupDto;
import com.handongapp.handongutcarpool.service.TbgroupService;
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
    @PostMapping("/create")
    public ResponseEntity<BasicDto.IdResDto> create(@Valid @RequestBody TbgroupDto.CreateReqDto param){
        return tbgroupService.create(param)
                .map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BasicDto.IdResDto.builder().id("User Not Exists").build()));
    }

    @Operation(summary = "그룹 잠그기",
            description = "그룹에 더이상 새로운 유저를 받지 않음 <br />"
                    + "@param TbgroupDto.LockReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<BasicDto.IdResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PostMapping("/lock")
    public ResponseEntity<BasicDto.IdResDto> lock(@Valid @RequestBody TbgroupDto.LockReqDto param){
        return tbgroupService.lock(param)
                .map(res -> {
                            if (res.getId().equals("AccessDenied"))
                                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                        .body(BasicDto.IdResDto.builder().id("Access Denied").build());
                            else return ResponseEntity.ok(res);
                        }
                )
                .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(BasicDto.IdResDto.builder().id("Group Not Exists").build()));
    }
}
