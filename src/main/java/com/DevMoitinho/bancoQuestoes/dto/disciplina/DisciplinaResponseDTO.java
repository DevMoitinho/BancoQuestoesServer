package com.DevMoitinho.bancoQuestoes.dto.disciplina;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for a discipline.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaResponseDTO{

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;  
}
