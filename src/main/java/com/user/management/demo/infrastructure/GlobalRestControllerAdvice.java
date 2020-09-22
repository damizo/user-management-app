package com.user.management.demo.infrastructure;

import com.user.management.demo.application.common.exception.ErrorResponse;
import com.user.management.demo.application.common.exception.ErrorType;
import com.user.management.demo.application.common.exception.ParameterizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalRestControllerAdvice {

	@ExceptionHandler(ParameterizedException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse handleParameterizedException(ParameterizedException parameterizedException) {
		return ErrorResponse.builder()
				.code(parameterizedException.getCode())
				.params(parameterizedException.getParams())
				.build();
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse internalServerError(Exception exception) {
		return ErrorResponse.builder()
				.code(ErrorType.INTERAL_SERVER_ERROR)
				.build();
	}

}
