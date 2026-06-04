package moe.prashast.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import moe.prashast.dto.Response;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst() // only first validation error
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation failed");

        Response response= new Response();
        response.setType(null);
        response.setMessage(errorMessage);
        response.setHttpStatus(200);
        response.setStatus(false);

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {

        String errorMessage = "Invalid request payload";

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException ife) {

            String fieldName = ife.getPath()
                    .stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .findFirst()
                    .orElse("Field");

            errorMessage = fieldName + " must be a valid number";
        }

        Response response = new Response();
        response.setType(null);
        response.setMessage(errorMessage);
        response.setHttpStatus(200);
        response.setStatus(false);

        return ResponseEntity.ok(response);
    }
}
