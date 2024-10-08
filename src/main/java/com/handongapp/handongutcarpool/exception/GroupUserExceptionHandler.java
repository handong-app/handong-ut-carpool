package com.handongapp.handongutcarpool.exception;

import com.handongapp.handongutcarpool.controller.TbgroupRestController;
import com.handongapp.handongutcarpool.controller.TbgroupTbuserRestController;
import com.handongapp.handongutcarpool.dto.CommonDto;
import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {TbgroupRestController.class, TbgroupTbuserRestController.class})
public class GroupUserExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<CommonDto.IdResDto> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CommonDto.IdResDto.builder().id(ex.getMessage()).build());
    }

    @ExceptionHandler(UserAlreadyInGroupException.class)
    public ResponseEntity<TbgroupTbuserDto.EnterGroupResDto> handleUserAlreadyInGroupException(UserAlreadyInGroupException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(TbgroupTbuserDto.EnterGroupResDto.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(NoMatchingDataException.class)
    public ResponseEntity<TbgroupTbuserDto.EnterGroupResDto> handleNoMatchingDataException(NoMatchingDataException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(TbgroupTbuserDto.EnterGroupResDto.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(GroupOverflowException.class)
    public ResponseEntity<TbgroupTbuserDto.EnterGroupResDto> handleGroupFullException(GroupOverflowException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(TbgroupTbuserDto.EnterGroupResDto.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(GroupLockedException.class)
    public ResponseEntity<TbgroupTbuserDto.EnterGroupResDto> handleGroupLockedException(GroupLockedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(TbgroupTbuserDto.EnterGroupResDto.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(UserAlreadyLeavedException.class)
    public ResponseEntity<TbgroupTbuserDto.LeaveGroupResDto> handleUserAlreadyLeavedException(UserAlreadyLeavedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(TbgroupTbuserDto.LeaveGroupResDto.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(GroupAlreadyCreatedException.class)
    public ResponseEntity<CommonDto.IdResDto> handleGroupAlreadyCreatedException(GroupAlreadyCreatedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(CommonDto.IdResDto.builder().id(ex.getMessage()).build());
    }
}
