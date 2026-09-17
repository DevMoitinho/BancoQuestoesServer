package com.DevMoitinho.bancoQuestoes.exception.disciplina;

import com.DevMoitinho.bancoQuestoes.exception.ProjectException;


/**
 * Exception thrown when a requested discipline is not found in the system.
 */
public class DisciplinaNotFoundException extends ProjectException {
    public DisciplinaNotFoundException() {
        super("Disciplina informada não existe!");
    }
}
