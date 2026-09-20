package com.DevMoitinho.bancoQuestoes.exception.questao;

import com.DevMoitinho.bancoQuestoes.exception.ProjectException;


/**
 * Exception thrown when a discipline does not have enough questions to generate an exam of the requested size.
 */
public class QuestoesInsuficientesException extends ProjectException {
    public QuestoesInsuficientesException() {
        super("A disciplina selecionada não possui questões suficientes para gerar uma prova desse tamanho");
    }
}
