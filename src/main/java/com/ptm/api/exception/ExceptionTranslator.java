package com.ptm.api.exception;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.ptm.api.exception.code.UserExceptionCodeAndMassage;

/**
 * Controller advice to translate the server side exceptions to client-friendly
 * json structures. The error response follows RFC7807 - Problem Details for
 * HTTP APIs (https://tools.ietf.org/html/rfc7807)
 */
@ControllerAdvice
public class ExceptionTranslator {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(UserServiceException.class)
	@ResponseBody
	protected ResponseEntity<ParameterizedErrorVM> handleUserServiceException(UserServiceException ex) {
		log.info("{}", ex);

		return new ResponseEntity<>(ex.getErrorVM(), HttpStatus.OK);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseBody
	protected ResponseEntity<ParameterizedErrorVM> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		UserServiceException userserviceexception = new UserServiceException(UserExceptionCodeAndMassage.GENERAL_ERROR);
		log.info("{}", ex);

		return new ResponseEntity<>(userserviceexception.getErrorVM(), HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	protected ResponseEntity<ParameterizedErrorVM> handleAuthenticationException(Exception ex) {
		log.info("{}", ex);
		UserServiceException userserviceexception = new UserServiceException(UserExceptionCodeAndMassage.GENERAL_ERROR);
		return new ResponseEntity<>(userserviceexception.getErrorVM(), HttpStatus.OK);
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	protected ResponseEntity<ParameterizedErrorVM> handleGeneralException(Exception ex) {
		log.info("{}", ex);
		return new ResponseEntity<>(new ParameterizedErrorVM(UserExceptionCodeAndMassage.NO_USER_FOUND.getErrCode(),
				UserExceptionCodeAndMassage.NO_USER_FOUND.getErrMsg()), HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Error methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		log.info("{}", ex);

		BindingResult result = ex.getBindingResult();
		List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
		return processFieldErrors(fieldErrors);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<ParameterizedErrorVM> handleAccessDeniedException(AccessDeniedException ex,
			WebRequest request) {
		log.info("{}", ex);
		return new ResponseEntity<>(new ParameterizedErrorVM(UserExceptionCodeAndMassage.USER_PERMISSION_FAIL.getErrCode(),
				UserExceptionCodeAndMassage.USER_PERMISSION_FAIL.getErrMsg()), HttpStatus.FORBIDDEN);
	}

	private Error processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
		Error error = new Error(HttpStatus.BAD_REQUEST.value(), "validation error");
		for (org.springframework.validation.FieldError fieldError : fieldErrors) {
			error.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return error;
	}

	static class Error {
		private final int status;
		private final String message;
		private List<FieldError> fieldErrors = new ArrayList<>();

		Error(int status, String message) {
			this.status = status;
			this.message = message;
		}

		public int getStatus() {
			return status;
		}

		public String getMessage() {
			return message;
		}

		public void addFieldError(String path, String message) {
			FieldError error = new FieldError(path, message);
			fieldErrors.add(error);
		}

		public List<FieldError> getFieldErrors() {
			return fieldErrors;
		}
	}
}
