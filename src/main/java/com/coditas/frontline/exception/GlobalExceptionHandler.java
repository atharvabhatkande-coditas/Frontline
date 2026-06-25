package com.coditas.frontline.exception;

import com.coditas.frontline.dto.response.ApplicationResponse;
import com.coditas.frontline.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>> handleNotFoundException(NotFoundException e){
        ErrorResponse errorResponse=new ErrorResponse(e.getMessage(), Instant.now(), HttpStatus.NOT_FOUND.value());
        ApplicationResponse<List<ErrorResponse>> applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
        return new ResponseEntity<>(applicationResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>> handleAlreadyExistException(AlreadyExistException e){
        ErrorResponse errorResponse=new ErrorResponse(e.getMessage(), Instant.now(), HttpStatus.CONFLICT.value());
        ApplicationResponse<List<ErrorResponse>> applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
        return new ResponseEntity<>(applicationResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler({AuthenticationException.class, AuthorizationException.class})
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>> handleAuthenticationException(Exception e){
        ErrorResponse errorResponse=new ErrorResponse(e.getMessage(), Instant.now(), HttpStatus.UNAUTHORIZED.value());
        ApplicationResponse<List<ErrorResponse>> applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
        return new ResponseEntity<>(applicationResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>> handleForbiddenException(ForbiddenException e){
        ErrorResponse errorResponse=new ErrorResponse(e.getMessage(), Instant.now(), HttpStatus.FORBIDDEN.value());
        ApplicationResponse<List<ErrorResponse>> applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
        return new ResponseEntity<>(applicationResponse,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({SqlException.class, SQLException.class,RuntimeException.class})
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>> handleSqlException(Exception e){
        ErrorResponse errorResponse=new ErrorResponse(e.getMessage(), Instant.now(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        ApplicationResponse<List<ErrorResponse>> applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
        return new ResponseEntity<>(applicationResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handleValidationException(MethodArgumentNotValidException e){
        Map<String,String> errors=new HashMap<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(error->errors.put(error.getField(),error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
    }

}
