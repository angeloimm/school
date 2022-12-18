package it.olegna.schoolmgmt.web;

import it.olegna.schoolmgmt.dto.api.ApiError;
import it.olegna.schoolmgmt.dto.api.ApiResponse;
import it.olegna.schoolmgmt.dto.api.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class RestCallControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        log.error("Errore interno nella richiesta http", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse
                .builder()
                .error(true)
                .errori(ApiError
                        .builder()
                        .details(Collections.singletonList(ErrorDetail.builder().code("generico").message(ex.getMessage()).build()))
                        .build())
                .build());
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Errore violazione validazione {}", ex.getMessage(), ex);
        ApiResponse<Void> body = ApiResponse.<Void>builder()
                .error(true)
                .errori(ApiError.builder()
                        .path(request.getDescription(true))
                        .details(ex.getBindingResult().getFieldErrors().stream().map(error -> {
                            return ErrorDetail.builder()
                                    .field(error.getField())
                                    .code(error.getCode())
                                    .source(error.getObjectName())
                                    .message(error.getDefaultMessage())
                                    .build();
                        }).collect(Collectors.toList()))
                        .build()).build();
        return handleExceptionInternal(ex, body, headers, status, request);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        log.error("Errore violazione validazione {}", ex.getMessage(), ex);
        return handleExceptionInternal(ex, buildGenericApiResponse(status,
                        Collections.singletonList(ErrorDetail
                                .builder()
                                .code("generico")
                                .message(ex.getMessage())
                                .build())),
                headers,
                status,
                request);
    }

    private ApiResponse<Void> buildGenericApiResponse(HttpStatusCode status, List<ErrorDetail> details) {
        return ApiResponse
                .<Void>builder()
                .error(true)
                .errori(ApiError
                        .builder()
                        .details(details)
                        .build())
                .build();
    }
}