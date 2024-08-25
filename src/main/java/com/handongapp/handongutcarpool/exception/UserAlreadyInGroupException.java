package com.handongapp.handongutcarpool.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  토큰이 정상적이지 않을 때 사용되는 예외처리
 *  HttpStatus 401
 */
@ResponseStatus(value=HttpStatus.CONFLICT)
@SuppressWarnings("serial")
@NoArgsConstructor
public class UserAlreadyInGroupException extends RuntimeException {
    public UserAlreadyInGroupException(String message) {
        super(message);
    }

    public UserAlreadyInGroupException(String message, Throwable cause) { super(message, cause); }
}
