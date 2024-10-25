package com.handongapp.handongutcarpool.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  해당 메서드 권한 없을 경우 사용되는 예외처리
 *  HttpStatus FORBIDDEN
 */
@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
@SuppressWarnings("serial")
@NoArgsConstructor
public class NoAuthenticationException extends RuntimeException {
	public NoAuthenticationException(String message) {
		super(message);
	}
}