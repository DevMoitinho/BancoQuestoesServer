package com.DevMoitinho.bancoQuestoes.exception;

/**
 * Exceção base do projeto, estendida por todas as exceções de domínio
 * específicas.
 * É capturada pelo {@link ErrorHandlingControllerAdvice} para retornar
 * respostas HTTP 400
 * padronizadas ao cliente.
 */
public class ProjectException extends RuntimeException {

    public ProjectException() {
        super("Erro inesperado no Pits A!");
    }

    public ProjectException(String message) {
        super(message);
    }
}
