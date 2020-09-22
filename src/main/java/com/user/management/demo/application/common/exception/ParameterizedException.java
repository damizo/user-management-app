package com.user.management.demo.application.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class ParameterizedException extends RuntimeException {
	private static final long serialVersionUID = 7289127718563239015L;
	
	protected ErrorType code;
	protected Map<String, Object> params = new HashMap<>();

	public ParameterizedException(ErrorType code, String firstKey,
			Object firstValue) {
		super(code.name());
		this.code = code;
		params.put(firstKey, firstValue);
	}

	@Override
	public String toString() {
		return code.name();
	}
}
