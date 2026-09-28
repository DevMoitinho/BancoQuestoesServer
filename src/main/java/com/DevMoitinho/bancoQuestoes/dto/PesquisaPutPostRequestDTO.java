package com.DevMoitinho.bancoQuestoes.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for making a search.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PesquisaPutPostRequestDTO {

    @JsonProperty("pesquisa")
    @NotBlank(message = "Pesquisa obrigatorio")
    private String pesquisa;

}
