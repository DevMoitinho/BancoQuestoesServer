package com.DevMoitinho.bancoQuestoes.service.integration;

import com.DevMoitinho.bancoQuestoes.dto.PesquisaPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.alternativa.AlternativaPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoResponseDTO;
import com.DevMoitinho.bancoQuestoes.exception.disciplina.DisciplinaNotFoundException;
import com.DevMoitinho.bancoQuestoes.exception.questao.QuestaoNotFoundException;
import com.DevMoitinho.bancoQuestoes.exception.questao.QuestoesInsuficientesException;
import com.DevMoitinho.bancoQuestoes.model.Disciplina;
import com.DevMoitinho.bancoQuestoes.model.Tag;
import com.DevMoitinho.bancoQuestoes.repository.DisciplinaRepository;
import com.DevMoitinho.bancoQuestoes.repository.TagRepository;
import com.DevMoitinho.bancoQuestoes.service.questao.QuestaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QuestaoServiceTest {

    @Autowired
    private QuestaoService questaoService;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private TagRepository tagRepository;

    private Long disciplinaId;
    private Long tagId;

    @BeforeEach
    void setUp() {
        Disciplina d = new Disciplina();
        d.setNome("Matemática");
        disciplinaId = disciplinaRepository.save(d).getId();

        Tag t = new Tag();
        t.setName("Fácil");
        tagId = tagRepository.save(t).getId();
    }

    @Test
    void criar_ShouldPersistQuestionAndAlternatives() {
        QuestaoPutPostRequestDTO dto = new QuestaoPutPostRequestDTO();
        dto.setEnunciado("Quanto é 1+1?");
        dto.setDisciplinaId(disciplinaId);
        dto.setTagsIds(Collections.singletonList(tagId));

        List<AlternativaPutPostRequestDTO> alts = new ArrayList<>();
        AlternativaPutPostRequestDTO alt1 = new AlternativaPutPostRequestDTO();
        alt1.setEnunciado("2");
        alt1.setCorreta(true);
        alts.add(alt1);

        AlternativaPutPostRequestDTO alt2 = new AlternativaPutPostRequestDTO();
        alt2.setEnunciado("3");
        alt2.setCorreta(false);
        alts.add(alt2);

        dto.setAlternativas(alts);

        QuestaoResponseDTO result = questaoService.criar(dto);

        assertNotNull(result.getId());
        assertEquals("Quanto é 1+1?", result.getEnunciado());
    }

    @Test
    void criar_ShouldThrowDisciplinaNotFoundException_WhenDisciplinaIdInvalid() {
        QuestaoPutPostRequestDTO dto = new QuestaoPutPostRequestDTO();
        dto.setEnunciado("Erro Disciplina");
        dto.setDisciplinaId(999L); // ID inexistente
        dto.setAlternativas(new ArrayList<>());

        assertThrows(DisciplinaNotFoundException.class, () -> questaoService.criar(dto));
    }

    @Test
    void alterar_ShouldUpdateAlternativesAndTags() {
        // Setup: create initial
        QuestaoPutPostRequestDTO createDto = new QuestaoPutPostRequestDTO();
        createDto.setEnunciado("Questão Original");
        createDto.setDisciplinaId(disciplinaId);
        List<AlternativaPutPostRequestDTO> alts = new ArrayList<>();
        AlternativaPutPostRequestDTO a1 = new AlternativaPutPostRequestDTO();
        a1.setEnunciado("Alt 1");
        a1.setCorreta(true);
        alts.add(a1);
        createDto.setAlternativas(alts);
        createDto.setTagsIds(Collections.singletonList(tagId));
        QuestaoResponseDTO created = questaoService.criar(createDto);

        // Update
        QuestaoPutPostRequestDTO updateDto = new QuestaoPutPostRequestDTO();
        updateDto.setEnunciado("Questão Atualizada");
        updateDto.setDisciplinaId(disciplinaId);
        List<AlternativaPutPostRequestDTO> newAlts = new ArrayList<>();
        AlternativaPutPostRequestDTO a2 = new AlternativaPutPostRequestDTO();
        a2.setEnunciado("Alt 2");
        a2.setCorreta(true);
        newAlts.add(a2);
        updateDto.setAlternativas(newAlts);
        updateDto.setTagsIds(Collections.singletonList(tagId));

        QuestaoResponseDTO result = questaoService.alterar(updateDto, created.getId());

        assertEquals("Questão Atualizada", result.getEnunciado());
    }

    @Test
    void alterar_ShouldThrowQuestaoNotFoundException_WhenIdInvalid() {
        QuestaoPutPostRequestDTO dto = new QuestaoPutPostRequestDTO();
        dto.setEnunciado("Não existo");
        dto.setDisciplinaId(disciplinaId);
        dto.setAlternativas(new ArrayList<>());

        assertThrows(QuestaoNotFoundException.class, () -> questaoService.alterar(dto, 999L));
    }

    @Test
    void alterar_ShouldThrowDisciplinaNotFoundException_WhenNewDisciplinaInvalid() {
        QuestaoPutPostRequestDTO createDto = new QuestaoPutPostRequestDTO();
        createDto.setEnunciado("Existe");
        createDto.setDisciplinaId(disciplinaId);
        createDto.setAlternativas(new ArrayList<>());
        QuestaoResponseDTO created = questaoService.criar(createDto);

        QuestaoPutPostRequestDTO updateDto = new QuestaoPutPostRequestDTO();
        updateDto.setEnunciado("Atualizando");
        updateDto.setDisciplinaId(999L);
        updateDto.setAlternativas(new ArrayList<>());

        assertThrows(DisciplinaNotFoundException.class, () -> questaoService.alterar(updateDto, created.getId()));
    }

    @Test
    void remover_ShouldThrowQuestaoNotFoundException_WhenIdInvalid() {
        assertThrows(QuestaoNotFoundException.class, () -> questaoService.remover(999L));
    }

    @Test
    void gerarProva_ShouldReturnCorrectSize() {
        // Setup: Create 5 questions
        for (int i = 0; i < 5; i++) {
            QuestaoPutPostRequestDTO dto = new QuestaoPutPostRequestDTO();
            dto.setEnunciado("Questão " + i);
            dto.setDisciplinaId(disciplinaId);
            dto.setAlternativas(new ArrayList<>());
            questaoService.criar(dto);
        }

        List<QuestaoResponseDTO> prova = questaoService.gerarProva(disciplinaId, 3);
        assertEquals(3, prova.size());
    }

    @Test
    void gerarProva_ShouldThrowExceptionWhenInsufficient() {
        // Setup: Create 2 questions
        for (int i = 0; i < 2; i++) {
            QuestaoPutPostRequestDTO dto = new QuestaoPutPostRequestDTO();
            dto.setEnunciado("Questão " + i);
            dto.setDisciplinaId(disciplinaId);
            dto.setAlternativas(new ArrayList<>());
            questaoService.criar(dto);
        }

        assertThrows(QuestoesInsuficientesException.class, () -> questaoService.gerarProva(disciplinaId, 5));
    }
}
