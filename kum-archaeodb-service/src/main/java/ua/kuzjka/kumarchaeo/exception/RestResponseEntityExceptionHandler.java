package ua.kuzjka.kumarchaeo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.kuzjka.kumarchaeo.dto.ErrorMessageDto;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {SuchCategoryExistsException.class})
    protected ResponseEntity<Object> handleCategoryExistsException(
            RuntimeException ex, WebRequest request) {
        ErrorMessageDto dto = new ErrorMessageDto(ex.getMessage());
        return handleExceptionInternal(ex, dto,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = {NoSuchCategoryException.class})
    protected ResponseEntity<Object> handleNoSuchCategoryException(
            RuntimeException ex, WebRequest request) {
        ErrorMessageDto dto = new ErrorMessageDto(ex.getMessage());
        return handleExceptionInternal(ex, dto,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
