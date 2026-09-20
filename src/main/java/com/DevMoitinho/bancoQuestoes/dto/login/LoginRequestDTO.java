package com.DevMoitinho.bancoQuestoes.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for user login.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @JsonProperty("username")
    @NotBlank(message = "Username obrigatorio")    
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "Password obrigatorio")    
    private String password;
}
