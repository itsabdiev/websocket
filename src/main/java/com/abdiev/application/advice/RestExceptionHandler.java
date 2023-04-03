package com.abdiev.application.advice;


import com.abdiev.application.payload.MessageResponse;
import com.abdiev.application.exception.UserAuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.Instant;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {UserAuthenticationException.class})
    @ResponseBody
    public ResponseEntity<MessageResponse> handleException(UserAuthenticationException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(MessageResponse.builder()
                        .issuedAt(Instant.now())
                        .message(exception.getMessage())
                        .build());
    }
}
