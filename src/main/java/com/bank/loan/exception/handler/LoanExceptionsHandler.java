package com.bank.loan.exception.handler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bank.loan.exception.CustomerNotFoundException;
import com.bank.loan.exception.LoanNotFoundException;

@RestControllerAdvice
public class LoanExceptionsHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(LoanNotFoundException.class)
	public ResponseEntity<Object> handleLoanNotFoundException(LoanNotFoundException ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
	}
}
