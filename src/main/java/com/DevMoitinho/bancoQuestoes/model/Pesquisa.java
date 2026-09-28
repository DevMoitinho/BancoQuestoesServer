package com.DevMoitinho.bancoQuestoes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a question in the question bank system.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pesquisa {

    /**
     * The text of the question.
     */
    private String pesquisa;

}
