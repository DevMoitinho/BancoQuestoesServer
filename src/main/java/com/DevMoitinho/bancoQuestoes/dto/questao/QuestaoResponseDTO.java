package com.DevMoitinho.bancoQuestoes.dto.questao;

import java.util.List;

import com.DevMoitinho.bancoQuestoes.dto.alternativa.AlternativaResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for a question.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestaoResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("enunciado")
    private String enunciado;

    @JsonProperty("alternativas")
    private List<AlternativaResponseDTO> alternativas;

}
