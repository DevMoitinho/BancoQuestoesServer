package com.DevMoitinho.bancoQuestoes.dto.alternativa;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlternativaResponseDTO{

    @JsonProperty("enunciado")
    private String enunciado;

    @JsonProperty("correta")
    private boolean correta;  
}
