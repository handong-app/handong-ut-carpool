package com.handongapp.handongutcarpool.exception;

import com.handongapp.handongutcarpool.controller.TbgroupRestController;
import com.handongapp.handongutcarpool.controller.TbuserRestController;
import com.handongapp.handongutcarpool.dto.BasicDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {TbuserRestController.class, TbgroupRestController.class})
public class GlobalExceptionHandler {

    @ExceptionHandler(NoAuthorizationException.class)
    public ResponseEntity<BasicDto.IdResDto> handleNoAuthorizationException(NoAuthorizationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(BasicDto.IdResDto.builder().id(ex.getMessage()).build());
    }

    @ExceptionHandler(NoMatchingDataException.class)
    public ResponseEntity<BasicDto.IdResDto> handleNoMatchingDataExceptionException(NoMatchingDataException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BasicDto.IdResDto.builder().id(ex.getMessage()).build());
    }

}
