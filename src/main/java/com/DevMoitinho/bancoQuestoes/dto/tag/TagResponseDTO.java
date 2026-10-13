package com.DevMoitinho.bancoQuestoes.dto.tag;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for a tag.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagResponseDTO{

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String name;  
}
