package com.DevMoitinho.bancoQuestoes.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private String pesquisa;
    @JsonProperty("disciplina")
    private Long disciplinaId;
    @JsonProperty("tags")
    private List<Long> tags;

}
