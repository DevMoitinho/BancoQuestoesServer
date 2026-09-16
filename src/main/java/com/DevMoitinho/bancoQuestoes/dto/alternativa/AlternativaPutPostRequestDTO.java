package com.DevMoitinho.bancoQuestoes.dto.alternativa;

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
public class AlternativaPutPostRequestDTO{

    @JsonProperty("enunciado")
    @NotBlank(message = "Enunciado obrigatorio")    
    private String enunciado;

    @JsonProperty("correta")
    private boolean correta;  
}
