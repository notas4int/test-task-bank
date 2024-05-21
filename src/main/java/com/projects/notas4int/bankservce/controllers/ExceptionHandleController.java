package com.projects.notas4int.bankservce.controllers;

import com.projects.notas4int.bankservce.security.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandleController extends ResponseEntityExceptionHandler {
    @ExceptionHandler({BankAccountNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleNotFoundExceptions(RuntimeException e,
                                                                      HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(request.getRequestURI(), e.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ClientExistsException.class, EmailAlreadyExistsException.class,
            EmailAddressEmptyException.class, EmailAddressEmptyException.class, IncorrectEnteredEmailAddressException.class,
            IncorrectEnteredEmailAddressException.class, IncorrectEnteredPhoneNumberException.class,
            IncorrectEnteredRequestParamException.class, InsufficientFundsInBalanceException.class,
            LastClientConnectedWayRemoveException.class, OldAndNewEmailIdenticalException.class,
            OldAndNewPhoneIdenticalException.class, PhoneAlreadyExistsException.class,
            PhoneNumberAlreadyTakenException.class, PhoneNumberEmptyException.class, RequestParamNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleIncorrectSizePostFieldsException(RuntimeException e,
                                                                                    HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(request.getRequestURI(), e.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
