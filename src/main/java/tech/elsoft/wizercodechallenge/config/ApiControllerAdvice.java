package tech.elsoft.wizercodechallenge.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.elsoft.wizercodechallenge.DTOs.ApiResponse;
import tech.elsoft.wizercodechallenge.exceptions.ApiBadRequestException;
import tech.elsoft.wizercodechallenge.exceptions.ApiNotFoundException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
@Slf4j
public class ApiControllerAdvice  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleException(Exception ex) {
        log.error("Exception handler for All Exception ", ex);
        return new ResponseEntity<>(new ApiResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(ApiNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> handleApiNotFound(ApiNotFoundException ex){
        log.error("Exception handler for ApiNotFoundException ", ex);
        return new ResponseEntity<>(new ApiResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiBadRequestException.class)
    public ResponseEntity<ApiResponse> handleApiBadRequest(ApiBadRequestException ex){
        log.error("Exception handler for ApiBadRequestException ", ex);
        return new ResponseEntity<>(new ApiResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, Set<String>> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(e ->{
            String field = ((FieldError) e).getField();
            String message = e.getDefaultMessage() == null ? "" : e.getDefaultMessage() ;
            message=  message.replace("{0}", field);
            Set<String> messages = errors.get(field);
            if(messages == null)
            {
                messages = new HashSet<>();
            }

            messages.add(message);

            errors.put(field, messages);
        });
        ApiResponse response = new ApiResponse(errors, "Invalid Request");

        return ResponseEntity.badRequest().body(response);
    }
}
