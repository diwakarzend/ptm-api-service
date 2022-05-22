package com.ptm.api.exception;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldError {
	
	private String field;
	
	private String code;
	
	private String message;
	
	
	public FieldError(String field, String code, String message) {
		this.field = field;
		this.code = code;
		this.message = message;
	}

	public FieldError(String path, String message) {
		this.field = path;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "FieldError {field=" + field + ", code=" + code + ", message=" + message + "}";
	}


	/**
	 * Converts a set of ConstraintViolations
	 * to a list of FieldErrors
	 * 
	 * @param constraintViolations
	 */
	public static List<FieldError> getErrors(
			Set<ConstraintViolation<?>> constraintViolations) {
		
		return constraintViolations.stream()
				.map(FieldError::of).collect(Collectors.toList());	
	}
	

	/**
	 * Converts a ConstraintViolation
	 * to a FieldError
	 */
	private static FieldError of(ConstraintViolation<?> constraintViolation) {
		
		String field = StringUtils.substringAfter(
				constraintViolation.getPropertyPath().toString(), ".");
		
		return new FieldError(field,
				constraintViolation.getMessageTemplate(),
				constraintViolation.getMessage());		
	}
}
