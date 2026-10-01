package com.DevMoitinho.bancoQuestoes.dto.tag;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating or updating a tag.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagPutPostRequestDTO {

    @JsonProperty("nome")
    @NotBlank(message = "Nome da tag é obrigatório")
    private String nome;
}
