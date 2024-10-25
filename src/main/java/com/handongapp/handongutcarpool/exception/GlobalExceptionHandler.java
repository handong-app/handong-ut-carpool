package com.handongapp.handongutcarpool.exception;

import com.handongapp.handongutcarpool.dto.CommonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice(assignableTypes = {TbuserRestController.class, TbgroupRestController.class})
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoAuthorizationException.class)
    public ResponseEntity<CommonDto.IdResDto> handleNoAuthorizationException(NoAuthorizationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(CommonDto.IdResDto.builder().id(ex.getMessage()).build());
    }
    @ExceptionHandler(NoAuthenticationException.class)
    public ResponseEntity<CommonDto.IdResDto> handleNoAuthenticationException(NoAuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(CommonDto.IdResDto.builder().id(ex.getMessage()).build());
    }

    @ExceptionHandler(NoMatchingDataException.class)
    public ResponseEntity<CommonDto.IdResDto> handleNoMatchingDataExceptionException(NoMatchingDataException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CommonDto.IdResDto.builder().id(ex.getMessage()).build());
    }

}
