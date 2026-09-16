package com.DevMoitinho.bancoQuestoes.dto.disciplina;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaPutPostRequestDTO {

    @JsonProperty("nome")
    @NotBlank(message = "Nome da disciplina é obrigatório")
    private String nome;
}
