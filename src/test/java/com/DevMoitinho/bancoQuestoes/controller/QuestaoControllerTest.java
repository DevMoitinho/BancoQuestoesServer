package com.DevMoitinho.bancoQuestoes.controller;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import com.DevMoitinho.bancoQuestoes.dto.alternativa.AlternativaResponseDTO;
import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoResponseDTO;
import com.DevMoitinho.bancoQuestoes.exception.questao.QuestaoNotFoundException;
import com.DevMoitinho.bancoQuestoes.exception.questao.QuestoesInsuficientesException;
import com.DevMoitinho.bancoQuestoes.model.Usuario;
import com.DevMoitinho.bancoQuestoes.repository.UsuarioRepository;
import com.DevMoitinho.bancoQuestoes.security.JwtService;
import com.DevMoitinho.bancoQuestoes.service.questao.QuestaoService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Testa a camada web de /questao com a segurança REAL (filtro JWT, regras do
 * SecurityConfig, CORS) e o QuestaoService MOCKADO, para isolar o controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class QuestaoControllerTest {

    private static final String USERNAME = "professor";
    private static final String VERCEL = "https://banco-questoes-interface.vercel.app";

    private static final String QUESTAO_VALIDA = """
            {
              "enunciado": "Quanto é 1+1?",
              "alternativas": [
                {"enunciado": "2", "correta": true},
                {"enunciado": "3", "correta": false}
              ],
              "disciplinaId": 7,
              "tagsIds": [1, 2]
            }
            """;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JwtService jwtService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @MockitoBean
    private QuestaoService questaoService;

    private String validToken;

    @BeforeEach
    void setUp() {
        usuarioRepository.save(Usuario.builder().username(USERNAME).password("irrelevante").build());
        validToken = jwtService.generateToken(USERNAME);
    }

    private static ResultMatcher rejected() {
        return status().is(anyOf(is(401), is(403)));
    }

    private static String bearer(String token) {
        return "Bearer " + token;
    }

    // ------------------------------------------------------------------
    // SEGURANÇA
    // ------------------------------------------------------------------

    @Test
    void protectedRoute_ShouldBeRejected_WhenNoToken() throws Exception {
        mockMvc.perform(post("/questao/pesquisa")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(rejected());

        verifyNoInteractions(questaoService);
    }

    @Test
    void protectedRoute_ShouldBeRejected_WhenTokenIsGarbage() throws Exception {
        mockMvc.perform(get("/questao/1-3").header("Authorization", bearer("isso.nao.e-um-jwt")))
                .andExpect(rejected());

        verifyNoInteractions(questaoService);
    }

    @Test
    void protectedRoute_ShouldBeRejected_WhenAuthorizationHeaderIsNotBearer() throws Exception {
        mockMvc.perform(get("/questao/1-3").header("Authorization", "Basic " + validToken))
                .andExpect(rejected());

        verifyNoInteractions(questaoService);
    }

    @Test
    void protectedRoute_ShouldBeRejected_WhenTokenIsExpired() throws Exception {
        long agora = System.currentTimeMillis();
        String expirado = Jwts.builder()
                .subject(USERNAME)
                .issuedAt(new Date(agora - 7_200_000))
                .expiration(new Date(agora - 3_600_000))
                .signWith(chaveDoServidor())
                .compact();

        mockMvc.perform(get("/questao/1-3").header("Authorization", bearer(expirado)))
                .andExpect(rejected());

        verifyNoInteractions(questaoService);
    }

    @Test
    void protectedRoute_ShouldBeRejected_WhenTokenSignedWithAnotherKey() throws Exception {
        SecretKey outraChave = Keys.hmacShaKeyFor(
                "uma-chave-totalmente-diferente-com-mais-de-32-bytes".getBytes(StandardCharsets.UTF_8));
        String forjado = Jwts.builder()
                .subject(USERNAME)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3_600_000))
                .signWith(outraChave)
                .compact();

        mockMvc.perform(get("/questao/1-3").header("Authorization", bearer(forjado)))
                .andExpect(rejected());

        verifyNoInteractions(questaoService);
    }

    @Test
    void protectedRoute_ShouldBeRejected_WhenTokenBelongsToUnknownUser() throws Exception {
        String tokenDeFantasma = jwtService.generateToken("fantasma");

        mockMvc.perform(get("/questao/1-3").header("Authorization", bearer(tokenDeFantasma)))
                .andExpect(rejected());

        verifyNoInteractions(questaoService);
    }

    @Test
    void publicRoute_ShouldNotRequireToken_ForHealthEndpoint() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
    }

    @Test
    void cors_ShouldAllowPreflight_FromVercelFrontend() throws Exception {
        mockMvc.perform(options("/questao")
                .header("Origin", VERCEL)
                .header("Access-Control-Request-Method", "POST"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", VERCEL));
    }

    @Test
    void cors_ShouldRejectPreflight_FromUnknownOrigin() throws Exception {
        mockMvc.perform(options("/questao")
                .header("Origin", "https://site-malicioso.example")
                .header("Access-Control-Request-Method", "POST"))
                .andExpect(status().isForbidden());
    }

    // ------------------------------------------------------------------
    // COMPORTAMENTO DO CONTROLLER (usuário autenticado, service mockado)
    // ------------------------------------------------------------------

    @Test
    void criar_ShouldReturnQuestaoAndPassDeserializedBodyToService() throws Exception {
        QuestaoResponseDTO resposta = QuestaoResponseDTO.builder()
                .id(42L)
                .enunciado("Quanto é 1+1?")
                .alternativas(List.of(
                        AlternativaResponseDTO.builder().enunciado("2").correta(true).build(),
                        AlternativaResponseDTO.builder().enunciado("3").correta(false).build()))
                .build();
        when(questaoService.criar(any(QuestaoPutPostRequestDTO.class))).thenReturn(resposta);

        mockMvc.perform(post("/questao")
                .header("Authorization", bearer(validToken))
                .contentType(MediaType.APPLICATION_JSON)
                .content(QUESTAO_VALIDA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(42))
                .andExpect(jsonPath("$.alternativas", hasSize(2)))
                .andExpect(jsonPath("$.alternativas[0].correta").value(true))
                .andExpect(jsonPath("$.alternativas[1].correta").value(false));

        // o JSON foi realmente convertido no DTO esperado
        ArgumentCaptor<QuestaoPutPostRequestDTO> captor = ArgumentCaptor.forClass(QuestaoPutPostRequestDTO.class);
        verify(questaoService).criar(captor.capture());
        QuestaoPutPostRequestDTO enviado = captor.getValue();
        assertEquals(7L, enviado.getDisciplinaId());
        assertEquals(List.of(1L, 2L), enviado.getTagsIds());
        assertEquals(2, enviado.getAlternativas().size());
        assertEquals(true, enviado.getAlternativas().get(0).isCorreta());
    }

    @Test
    void criar_ShouldReturn400WithValidationErrors_WhenBodyIsInvalid() throws Exception {
        String invalido = """
                {"enunciado": "", "alternativas": []}
                """;

        mockMvc.perform(post("/questao")
                .header("Authorization", bearer(validToken))
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors", hasItems("Enunciado obrigatorio", "Alternativas obrigatorias")));

        verify(questaoService, never()).criar(any());
    }

    @Test
    void alterar_ShouldReturn400_WhenQuestaoDoesNotExist() throws Exception {
        when(questaoService.alterar(any(QuestaoPutPostRequestDTO.class), anyLong()))
                .thenThrow(new QuestaoNotFoundException());

        mockMvc.perform(put("/questao/999")
                .header("Authorization", bearer(validToken))
                .contentType(MediaType.APPLICATION_JSON)
                .content(QUESTAO_VALIDA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.errors", hasSize(0)));
    }

    @Test
    void deletar_ShouldReturnRemovedQuestao() throws Exception {
        QuestaoResponseDTO removida = QuestaoResponseDTO.builder().id(5L).enunciado("Removida").build();
        when(questaoService.remover(5L)).thenReturn(removida);

        mockMvc.perform(delete("/questao/5").header("Authorization", bearer(validToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));

        verify(questaoService).remover(5L);
    }

    @Test
    void pesquisar_ShouldReturnList() throws Exception {
        when(questaoService.pesquisar(any())).thenReturn(List.of(
                QuestaoResponseDTO.builder().id(1L).enunciado("A").build(),
                QuestaoResponseDTO.builder().id(2L).enunciado("B").build()));

        mockMvc.perform(post("/questao/pesquisa")
                .header("Authorization", bearer(validToken))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"pesquisa": "derivada", "disciplinaId": 3, "tags": [1]}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void gerarProva_ShouldParseDisciplinaAndTamanhoFromPath() throws Exception {
        when(questaoService.gerarProva(3L, 5)).thenReturn(List.of(
                QuestaoResponseDTO.builder().id(1L).enunciado("Q1").build()));

        mockMvc.perform(get("/questao/3-5").header("Authorization", bearer(validToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(questaoService).gerarProva(3L, 5);
    }

    @Test
    void gerarProva_ShouldReturn400_WhenNotEnoughQuestions() throws Exception {
        when(questaoService.gerarProva(3L, 50)).thenThrow(new QuestoesInsuficientesException());

        mockMvc.perform(get("/questao/3-50").header("Authorization", bearer(validToken)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    private SecretKey chaveDoServidor() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
