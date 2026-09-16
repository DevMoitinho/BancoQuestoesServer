package com.DevMoitinho.bancoQuestoes.exception.questao;

import com.DevMoitinho.bancoQuestoes.exception.ProjectException;


public class QuestoesInsuficientesException extends ProjectException {
    public QuestoesInsuficientesException() {
        super("A disciplina selecionada não possui questões suficientes para gerar uma prova desse tamanho");
    }
}
