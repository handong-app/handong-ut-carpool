package com.handongapp.handongutcarpool.exception;

import com.handongapp.handongutcarpool.controller.TbgroupTbuserRestController;
import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {TbgroupTbuserRestController.class})
public class GroupUserExceptionHandler {
    @ExceptionHandler(UserAlreadyInGroupException.class)
    public ResponseEntity<TbgroupTbuserDto.EnterGroupResDto> handleUserAlreadyInGroupException(UserAlreadyInGroupException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(TbgroupTbuserDto.EnterGroupResDto.builder().role(ex.getMessage()).build());
    }

    @ExceptionHandler(NoMatchingDataException.class)
    public ResponseEntity<TbgroupTbuserDto.EnterGroupResDto> handleNoMatchingDataException(NoMatchingDataException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(TbgroupTbuserDto.EnterGroupResDto.builder().role(ex.getMessage()).build());
    }

    @ExceptionHandler(GroupFullException.class)
    public ResponseEntity<TbgroupTbuserDto.EnterGroupResDto> handleGroupFullException(GroupFullException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(TbgroupTbuserDto.EnterGroupResDto.builder().role(ex.getMessage()).build());
    }

    @ExceptionHandler(GroupLockedException.class)
    public ResponseEntity<TbgroupTbuserDto.EnterGroupResDto> handleGroupLockedException(GroupLockedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(TbgroupTbuserDto.EnterGroupResDto.builder().role(ex.getMessage()).build());
    }


}
