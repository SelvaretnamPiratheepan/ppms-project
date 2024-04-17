package com.sgic.ppms.util;

import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.sgic.ppms.enums.RestApiResponseStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseWrapper<?>> handleValidationExceptions(MethodArgumentNotValidException e) {
		StringBuilder errorMessage = new StringBuilder();
		e.getBindingResult().getFieldErrors().forEach(error -> {
			errorMessage.append(error.getDefaultMessage()).append(" ");
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ResponseWrapper<>(RestApiResponseStatus.BAD_REQUEST.getCode(),
						RestApiResponseStatus.BAD_REQUEST.getStatus(), errorMessage.toString()));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ResponseWrapper<?>> handleValidationExceptions(DataIntegrityViolationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ResponseWrapper<>(RestApiResponseStatus.BAD_REQUEST.getCode(),
						RestApiResponseStatus.BAD_REQUEST.getStatus(), ValidationMessages.DUPLICATE_ENTRY));
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ResponseWrapper<?>> handleNoSuchElementException(NoSuchElementException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseWrapper<>(RestApiResponseStatus.INTERNAL_SERVER_ERROR.getCode(),
						RestApiResponseStatus.INTERNAL_SERVER_ERROR.getStatus(), ValidationMessages.INVALID_ID));
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ResponseWrapper<?>> handleNoResourceFoundException(NoResourceFoundException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ResponseWrapper<>(RestApiResponseStatus.BAD_REQUEST.getCode(),
						RestApiResponseStatus.BAD_REQUEST.getStatus(), ValidationMessages.WRONG_API_CALL));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ResponseWrapper<?>> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ResponseWrapper<>(RestApiResponseStatus.BAD_REQUEST.getCode(),
						RestApiResponseStatus.BAD_REQUEST.getStatus(), ValidationMessages.WRONG_API_CALL));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ResponseWrapper<?>> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseWrapper<>(RestApiResponseStatus.INTERNAL_SERVER_ERROR.getCode(),
						RestApiResponseStatus.INTERNAL_SERVER_ERROR.getStatus(), ValidationMessages.MISMATCH_INPUT));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseWrapper<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseWrapper<>(RestApiResponseStatus.INTERNAL_SERVER_ERROR.getCode(),
						RestApiResponseStatus.INTERNAL_SERVER_ERROR.getStatus(), ValidationMessages.WRONG_JSON));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseWrapper<?>> handleOtherExceptions(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
				RestApiResponseStatus.INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), e.toString()));
	}
}
