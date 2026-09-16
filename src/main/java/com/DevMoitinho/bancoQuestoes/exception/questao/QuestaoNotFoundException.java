package com.DevMoitinho.bancoQuestoes.exception.questao;

import com.DevMoitinho.bancoQuestoes.exception.ProjectException;


public class QuestaoNotFoundException extends ProjectException {
    public QuestaoNotFoundException() {
        super("Questão informada não existe!");
    }
}
