package edu.jong.board.client.handler;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import edu.jong.board.client.exception.ResourceDeactiveException;
import edu.jong.board.client.exception.ResourceExistsException;
import edu.jong.board.client.exception.ResourceNotFoundException;
import edu.jong.board.client.response.ErrorResponse;

@RestControllerAdvice
public class ErrorResponseHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({
		ConstraintViolationException.class,
		ResourceNotFoundException.class,
		ResourceExistsException.class,
		ResourceDeactiveException.class,
		Exception.class
	})
	ResponseEntity<ErrorResponse> handleCustomException(Exception e) {

		HttpStatus status = null;
		
	 	if (e instanceof ConstraintViolationException) {
	 		status = HttpStatus.BAD_REQUEST;
		}else if (e instanceof ResourceNotFoundException) {
			status = HttpStatus.NOT_FOUND;
		} else if (e instanceof ResourceExistsException) {
			status = HttpStatus.CONFLICT;
		} else if (e instanceof ResourceDeactiveException) {
			status = HttpStatus.GONE;
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
			
		return ResponseEntity.status(status)
				.body(new ErrorResponse(e));
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return super.handleExceptionInternal(ex, new ErrorResponse(ex), headers, status, request);
	}
	
}
