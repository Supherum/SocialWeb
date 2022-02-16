package com.repiso.sasiain.pablo.instaFake.error.controller;


import com.repiso.sasiain.pablo.instaFake.error.exceptions.*;
import com.repiso.sasiain.pablo.instaFake.error.model.GenericError;
import com.repiso.sasiain.pablo.instaFake.error.model.SubError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundExceptionCustom.class})
    public ResponseEntity<?> handleNotFoundException(EntityNotFoundExceptionCustom ex, WebRequest request) {
        return GenericErrorBuilder(HttpStatus.NOT_FOUND,ex, request);
    }
    @ExceptionHandler({EnityUniqueExceptionCustom.class})
    public ResponseEntity<?> handleUniqueException (EnityUniqueExceptionCustom ex, WebRequest request) {
        return GenericErrorBuilder(HttpStatus.BAD_REQUEST,ex, request);
    }

    @ExceptionHandler({ForbiddenCustomError.class})
    public ResponseEntity<?> handleUnAuthorizedException (ForbiddenCustomError ex, WebRequest request) {
        return GenericErrorBuilder(HttpStatus.FORBIDDEN,ex, request);
    }

    @ExceptionHandler({StorageExceptionCustom.class})
    public ResponseEntity<?> handleStorageExceptionCustom (StorageExceptionCustom ex, WebRequest request) {
        return GenericErrorBuilder(HttpStatus.CONFLICT,ex, request);
    }

    @ExceptionHandler({FollowingErrorCustom.class})
    public ResponseEntity<?> handleFollowingExceptionCustom(FollowingErrorCustom ex, WebRequest request) {
        return GenericErrorBuilder(HttpStatus.CONFLICT,ex, request);
    }

    @ExceptionHandler({FileExceptionCustom.class})
    public ResponseEntity<?> handleFileExceptionCustom(FileExceptionCustom ex, WebRequest request) {
        return GenericErrorBuilder(HttpStatus.CONFLICT,ex, request);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return GenericErrorBuilder(status, ex, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<SubError> subErrorList = new ArrayList<>();

        ex.getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                subErrorList.add(
                        SubError.builder()
                                .object(fieldError.getObjectName())
                                .field(fieldError.getField())
                                .fieldRejected(fieldError.getRejectedValue())
                                .message(fieldError.getDefaultMessage())
                                .build()
                );
            }
            else // Si no, es que se ha producido en una anotación a nivel de clase
            {
                ObjectError objectError = (ObjectError) error;
                subErrorList.add(
                        SubError.builder()
                                .object(objectError.getObjectName())
                                .message(objectError.getDefaultMessage())
                                .build()
                );
            }
        });

        return GenercErrorWithSubErrorBuilder(HttpStatus.BAD_REQUEST, "Errores de validación",
                request,
                subErrorList.isEmpty() ? null : subErrorList
        );
    }

    private ResponseEntity<Object>  GenericErrorBuilder (HttpStatus status,Exception ex, WebRequest request ){
        return ResponseEntity.status(status).body(
            GenericError.builder()
                    .status(status)
                    .message(ex.getMessage())
                    .rute(((ServletWebRequest) request).getRequest().getRequestURI())
                    .code(status.value())
                    .build()
        );
    }

    private ResponseEntity<Object> GenercErrorWithSubErrorBuilder(HttpStatus status, String message, WebRequest request, List<SubError> listaErrores){
        return  ResponseEntity
                .status(status)
                .body(
                        GenericError.builder()
                                .status(status)
                                .message(message)
                                .rute(((ServletWebRequest) request).getRequest().getRequestURI())
                                .listSubError(listaErrores)
                                .build()
                );
    }
}
