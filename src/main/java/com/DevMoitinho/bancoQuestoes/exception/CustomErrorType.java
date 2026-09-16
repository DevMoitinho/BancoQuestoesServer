package com.DevMoitinho.bancoQuestoes.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO padronizado para respostas de erro da API.
 * Contém o timestamp do erro, uma mensagem principal e uma lista
 * opcional de erros detalhados (ex: violações de validação por campo).
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorType {

    /** Momento em que o erro ocorreu. */
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    /** Mensagem descritiva principal do erro. */
    @JsonProperty("message")
    private String message;

    /** Lista de mensagens de erro detalhadas (ex: erros de validação por campo). */
    @JsonProperty("errors")
    private List<String> errors;

    /**
     * Construtor de conveniência a partir de uma {@link ProjectException}.
     *
     * @param e exceção de domínio do projeto.
     */
    public CustomErrorType(ProjectException e) {
        this.timestamp = LocalDateTime.now();
        this.message = e.getMessage();
        this.errors = new ArrayList<>();
    }
}
