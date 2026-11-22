package com.DevMoitinho.bancoQuestoes.controller;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import com.DevMoitinho.bancoQuestoes.model.Usuario;
import com.DevMoitinho.bancoQuestoes.repository.UsuarioRepository;
import com.DevMoitinho.bancoQuestoes.security.JwtService;
import com.jayway.jsonpath.JsonPath;

/**
 * Testes de ponta a ponta do login: filtros de segurança reais, JwtService real
 * e banco H2. Cada teste roda em uma transação que sofre rollback no final.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

    private static final String USERNAME = "maria";
    private static final String PASSWORD = "senha123";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        usuarioRepository.save(Usuario.builder()
                .username(USERNAME)
                .password(passwordEncoder.encode(PASSWORD))
                .build());
    }

    /** Aceita 401 ou 403: o importante é que a requisição foi barrada. */
    private static ResultMatcher rejected() {
        return status().is(anyOf(is(401), is(403)));
    }

    private static String loginJson(String username, String password) {
        return """
                {"username": "%s", "password": "%s"}
                """.formatted(username, password);
    }

    @Test
    void login_ShouldReturnValidToken_WhenCredentialsAreCorrect() throws Exception {
        MvcResult result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");

        // o token devolvido pertence mesmo ao usuário que fez login
        assertEquals(USERNAME, jwtService.extractUsername(token));
    }

    @Test
    void loginToken_ShouldGrantAccessToProtectedRoute() throws Exception {
        MvcResult login = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andReturn();
        String token = JsonPath.read(login.getResponse().getContentAsString(), "$.token");

        // Não há questões cadastradas, então o service responde 400 (questões
        // insuficientes). Receber 400 e não 401/403 prova que a autenticação passou.
        mockMvc.perform(get("/questao/1-1").header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_ShouldBeRejected_WhenPasswordIsWrong() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson(USERNAME, "senha-errada")))
                .andExpect(rejected());
    }

    @Test
    void login_ShouldBeRejected_WhenUserDoesNotExist() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson("fantasma", PASSWORD)))
                .andExpect(rejected());
    }

    @Test
    void login_ShouldReturn400_WhenFieldsAreBlank() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson("", "")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasItems("Username obrigatorio", "Password obrigatorio")));
    }
}
