package com.DevMoitinho.bancoQuestoes.dto.questao;

import java.util.List;

import com.DevMoitinho.bancoQuestoes.dto.alternativa.AlternativaPutPostRequestDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating or updating a question.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestaoPutPostRequestDTO {

    @JsonProperty("enunciado")
    @NotBlank(message = "Enunciado obrigatorio")
    private String enunciado;

    @JsonProperty("alternativas")
    @NotEmpty(message = "Alternativas obrigatorias")
    private List<AlternativaPutPostRequestDTO> alternativas;

    @JsonProperty("disciplinaId")
    @NotNull(message = "Disciplina é obrigatória")
    private Long disciplinaId;
}
