package com.DevMoitinho.bancoQuestoes.service.questao;

import com.DevMoitinho.bancoQuestoes.dto.PesquisaPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoResponseDTO;
import com.DevMoitinho.bancoQuestoes.exception.disciplina.DisciplinaNotFoundException;
import com.DevMoitinho.bancoQuestoes.exception.questao.QuestaoNotFoundException;
import com.DevMoitinho.bancoQuestoes.exception.questao.QuestoesInsuficientesException;
import com.DevMoitinho.bancoQuestoes.model.Alternativa;
import com.DevMoitinho.bancoQuestoes.model.Disciplina;
import com.DevMoitinho.bancoQuestoes.model.Questao;
import com.DevMoitinho.bancoQuestoes.model.Tag;
import com.DevMoitinho.bancoQuestoes.repository.DisciplinaRepository;
import com.DevMoitinho.bancoQuestoes.repository.QuestaoRepository;
import com.DevMoitinho.bancoQuestoes.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestaoServiceUnitTest {

    @Mock
    private QuestaoRepository questaoRepository;
    @Mock
    private DisciplinaRepository disciplinaRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private QuestaoServiceImpl questaoService;

    private QuestaoPutPostRequestDTO questaoDTO;
    private Questao questaoEntity;
    private Disciplina disciplina;

    @BeforeEach
    void setUp() {
        questaoDTO = new QuestaoPutPostRequestDTO();
        questaoDTO.setEnunciado("Teste Enunciado");
        questaoDTO.setDisciplinaId(1L);
        questaoDTO.setTagsIds(Collections.singletonList(10L));

        List<com.DevMoitinho.bancoQuestoes.dto.alternativa.AlternativaPutPostRequestDTO> altDTOs = new ArrayList<>();
        com.DevMoitinho.bancoQuestoes.dto.alternativa.AlternativaPutPostRequestDTO alt = new com.DevMoitinho.bancoQuestoes.dto.alternativa.AlternativaPutPostRequestDTO();
        alt.setEnunciado("Alt 1");
        alt.setCorreta(true);
        altDTOs.add(alt);
        questaoDTO.setAlternativas(altDTOs);

        questaoEntity = new Questao();
        questaoEntity.setId(1L);
        questaoEntity.setEnunciado("Teste Enunciado");
        questaoEntity.setAlternativas(new ArrayList<>());
        questaoEntity.setTags(new ArrayList<>());

        disciplina = new Disciplina();
        disciplina.setId(1L);
        disciplina.setNome("Matemática");
    }

    @Test
    void criar_ShouldSaveQuestao_WhenValid() {
        when(modelMapper.map(questaoDTO, Questao.class)).thenReturn(questaoEntity);
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));
        when(tagRepository.findAllById(any())).thenReturn(Collections.singletonList(new Tag()));
        when(questaoRepository.save(any())).thenReturn(questaoEntity);
        when(modelMapper.map(questaoEntity, QuestaoResponseDTO.class)).thenReturn(new QuestaoResponseDTO());

        QuestaoResponseDTO result = questaoService.criar(questaoDTO);

        assertNotNull(result);
        verify(questaoRepository).save(any());
    }

    @Test
    void criar_ShouldThrowDisciplinaNotFoundException_WhenDisciplinaMissing() {
        when(modelMapper.map(questaoDTO, Questao.class)).thenReturn(questaoEntity);
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DisciplinaNotFoundException.class, () -> questaoService.criar(questaoDTO));
    }

    @Test
    void remover_ShouldDeleteQuestao_WhenExists() {
        when(questaoRepository.findById(1L)).thenReturn(Optional.of(questaoEntity));
        when(modelMapper.map(questaoEntity, QuestaoResponseDTO.class)).thenReturn(new QuestaoResponseDTO());

        QuestaoResponseDTO result = questaoService.remover(1L);

        assertNotNull(result);
        verify(questaoRepository).delete(questaoEntity);
    }

    @Test
    void remover_ShouldThrowQuestaoNotFoundException_WhenMissing() {
        when(questaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(QuestaoNotFoundException.class, () -> questaoService.remover(1L));
    }

    @Test
    void alterar_ShouldUpdateQuestao_WhenValid() {
        // O mapper devolve a "questão nova" já com a lista de alternativas inicializada,
        // como acontece na aplicação real quando o DTO traz alternativas.
        Questao questaoNova = new Questao();
        questaoNova.setEnunciado("Enunciado Atualizado");
        questaoNova.setAlternativas(new ArrayList<>());

        when(questaoRepository.findById(1L)).thenReturn(Optional.of(questaoEntity));
        when(modelMapper.map(questaoDTO, Questao.class)).thenReturn(questaoNova);
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));
        when(tagRepository.findAllById(any())).thenReturn(Collections.singletonList(new Tag()));
        when(questaoRepository.save(any())).thenReturn(questaoEntity);
        when(modelMapper.map(questaoEntity, QuestaoResponseDTO.class)).thenReturn(new QuestaoResponseDTO());

        QuestaoResponseDTO result = questaoService.alterar(questaoDTO, 1L);

        assertNotNull(result);
        // verifica que os dados foram realmente aplicados na entidade existente
        assertEquals("Enunciado Atualizado", questaoEntity.getEnunciado());
        assertSame(disciplina, questaoEntity.getDisciplina());
        verify(questaoRepository).save(questaoEntity);
    }

    @Test
    void alterar_ShouldThrowQuestaoNotFoundException_WhenQuestaoMissing() {
        when(questaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(QuestaoNotFoundException.class, () -> questaoService.alterar(questaoDTO, 1L));
    }

    @Test
    void alterar_ShouldThrowDisciplinaNotFoundException_WhenDisciplinaMissing() {
        when(questaoRepository.findById(1L)).thenReturn(Optional.of(questaoEntity));
        when(modelMapper.map(questaoDTO, Questao.class)).thenReturn(new Questao());
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DisciplinaNotFoundException.class, () -> questaoService.alterar(questaoDTO, 1L));
    }

    @Test
    void gerarProva_ShouldReturnQuestions_WhenEnoughExist() {
        List<Questao> questions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Questao q = new Questao();
            q.setAlternativas(new ArrayList<>());
            questions.add(q);
        }
        when(questaoRepository.findByDisciplinaId(1L)).thenReturn(questions);
        when(modelMapper.map(any(), eq(QuestaoResponseDTO.class))).thenReturn(new QuestaoResponseDTO());

        List<QuestaoResponseDTO> result = questaoService.gerarProva(1L, 3);

        assertEquals(3, result.size());
    }

    @Test
    void gerarProva_ShouldThrowQuestoesInsuficientesException_WhenNotEnough() {
        when(questaoRepository.findByDisciplinaId(1L)).thenReturn(Collections.singletonList(new Questao()));

        assertThrows(QuestoesInsuficientesException.class, () -> questaoService.gerarProva(1L, 3));
    }

    @Test
    void pesquisar_ShouldCallRepository() {
        PesquisaPutPostRequestDTO pesquisaDTO = new PesquisaPutPostRequestDTO();
        pesquisaDTO.setPesquisa("teste");

        when(questaoRepository.findAll(any(org.springframework.data.jpa.domain.Specification.class))).thenReturn(Collections.singletonList(questaoEntity));
        when(modelMapper.map(any(), eq(QuestaoResponseDTO.class))).thenReturn(new QuestaoResponseDTO());

        List<QuestaoResponseDTO> result = questaoService.pesquisar(pesquisaDTO);

        assertFalse(result.isEmpty());
        verify(questaoRepository).findAll(any(org.springframework.data.jpa.domain.Specification.class));
    }
}
