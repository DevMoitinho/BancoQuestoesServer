package com.DevMoitinho.bancoQuestoes.exception.tag;

import com.DevMoitinho.bancoQuestoes.exception.ProjectException;


/**
 * Exception thrown when a requested discipline is not found in the system.
 */
public class TagNotFoundException extends ProjectException {
    public TagNotFoundException() {
        super("Tag informada não existe!");
    }
}
