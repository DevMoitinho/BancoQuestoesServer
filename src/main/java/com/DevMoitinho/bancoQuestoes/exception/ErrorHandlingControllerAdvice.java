package com.DevMoitinho.bancoQuestoes.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

/**
 * Handler global de exceções da aplicação.
 * Intercepta exceções lançadas pelos controllers e converte-as em respostas
 * HTTP
 * padronizadas utilizando {@link CustomErrorType}.
 */
@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    private CustomErrorType defaultCustomErrorTypeConstruct(String message) {
        return CustomErrorType.builder()
                .timestamp(LocalDateTime.now())
                .errors(new ArrayList<>())
                .message(message)
                .build();
    }

    /**
     * Trata erros de validação de argumentos de método (ex: {@code @Valid} em
     * DTOs).
     *
     * @param e exceção contendo os erros de validação por campo.
     * @return resposta padronizada com status 400 e lista de erros.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomErrorType onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        CustomErrorType customErrorType = defaultCustomErrorTypeConstruct(
                "Erros de validacao encontrados");
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            customErrorType.getErrors().add(fieldError.getDefaultMessage());
        }
        return customErrorType;
    }

    /**
     * Trata violações de constraints do Bean Validation (ex: {@code @Size},
     * {@code @NotNull}).
     *
     * @param e exceção contendo as violações de constraint.
     * @return resposta padronizada com status 400 e lista de violações.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomErrorType onConstraintViolation(ConstraintViolationException e) {
        CustomErrorType customErrorType = defaultCustomErrorTypeConstruct(
                "Erros de validacao encontrados");
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            customErrorType.getErrors().add(violation.getMessage());
        }
        return customErrorType;
    }

    /**
     * Trata exceções de domínio do projeto ({@link ProjectException} e subclasses).
     *
     * @param e exceção de negócio lançada pelos serviços.
     * @return resposta padronizada com status 400 e mensagem da exceção.
     */
    @ExceptionHandler(ProjectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomErrorType onProjectException(ProjectException e) {
        return defaultCustomErrorTypeConstruct(
                e.getMessage());
    }

}
