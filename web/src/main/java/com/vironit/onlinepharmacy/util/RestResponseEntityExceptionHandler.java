package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.service.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {AuthenticationServiceException.class, CreditCardServiceException.class, OrderServiceException.class,
            ProcurementServiceException.class, ProductServiceException.class, RecipeServiceException.class,
            StockServiceException.class, UserServiceException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

//	@ExceptionHandler({ Exception.class })
//	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
//		ApiError apiError = new ApiError(
//				HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
//		return new ResponseEntity<Object>(
//				apiError, new HttpHeaders(), apiError.getStatus());
//	}
//
//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//		return super.handleMethodArgumentNotValid(ex, headers, status, request);
//	}

}