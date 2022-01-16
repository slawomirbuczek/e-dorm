package com.edorm.exceptions;

import com.edorm.exceptions.reservation.ReservationNotAvailableException;
import com.edorm.exceptions.users.*;
import com.edorm.models.ResponseMessage;
import com.edorm.models.validations.ValidationExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ValidationExceptionMessages handleValidationExceptions(BindException ex) {

        List<ResponseMessage> errorMessages = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> errorMessages.add(
                new ResponseMessage(error.getDefaultMessage()
                )));

        return new ValidationExceptionMessages(errorMessages);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationExceptionMessages handleConstraintViolationException(ConstraintViolationException ex) {

        List<ResponseMessage> errorMessages = new ArrayList<>();

        ex.getConstraintViolations().forEach(violation -> errorMessages.add(
                new ResponseMessage(violation.getMessage()
                )));

        return new ValidationExceptionMessages(errorMessages);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseMessage handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseMessage(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseMessage handleHttpMessageNotReadableException() {
        return new ResponseMessage("Wrong data format");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseMessage handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseMessage(ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseMessage handleEmailAlreadyTakenException(EmailAlreadyTakenException ex) {
        return new ResponseMessage(ex.getMessage());
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseMessage handleWrongPasswordException(WrongPasswordException ex) {
        return new ResponseMessage(ex.getMessage());
    }

    @ExceptionHandler(TheSamePasswordException.class)
    public ResponseMessage handleTheSamePasswordException(TheSamePasswordException ex) {
        return new ResponseMessage(ex.getMessage());
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseMessage handleAddressNotFoundException(AddressNotFoundException ex) {
        return new ResponseMessage(ex.getMessage());
    }

    @ExceptionHandler(ReservationNotAvailableException.class)
    public ResponseMessage handleReservationNotAvailableException(ReservationNotAvailableException ex) {
        return new ResponseMessage(ex.getMessage());
    }

}
