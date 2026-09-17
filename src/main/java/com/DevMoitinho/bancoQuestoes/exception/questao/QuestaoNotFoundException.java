package com.DevMoitinho.bancoQuestoes.exception.questao;

import com.DevMoitinho.bancoQuestoes.exception.ProjectException;


/**
 * Exception thrown when a requested question is not found in the system.
 */
public class QuestaoNotFoundException extends ProjectException {
    public QuestaoNotFoundException() {
        super("Questão informada não existe!");
    }
}
