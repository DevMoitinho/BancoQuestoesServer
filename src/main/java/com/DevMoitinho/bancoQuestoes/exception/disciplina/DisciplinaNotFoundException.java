package com.DevMoitinho.bancoQuestoes.exception.disciplina;

import com.DevMoitinho.bancoQuestoes.exception.ProjectException;


public class DisciplinaNotFoundException extends ProjectException {
    public DisciplinaNotFoundException() {
        super("Disciplina informada não existe!");
    }
}
