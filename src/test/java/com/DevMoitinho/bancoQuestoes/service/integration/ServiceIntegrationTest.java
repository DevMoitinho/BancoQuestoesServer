package com.DevMoitinho.bancoQuestoes.service.integration;

import com.DevMoitinho.bancoQuestoes.dto.disciplina.DisciplinaPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.disciplina.DisciplinaResponseDTO;
import com.DevMoitinho.bancoQuestoes.dto.tag.TagPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.tag.TagResponseDTO;
import com.DevMoitinho.bancoQuestoes.service.disciplina.DisciplinaService;
import com.DevMoitinho.bancoQuestoes.service.tag.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ServiceIntegrationTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Test
    void tagLifecycle_ShouldWork() {
        TagPutPostRequestDTO dto = new TagPutPostRequestDTO();
        dto.setName("Integration Test Tag");

        TagResponseDTO created = tagService.criar(dto);
        assertNotNull(created.getId());
        assertEquals("Integration Test Tag", created.getName());

        List<TagResponseDTO> list = tagService.listar();
        assertTrue(list.stream().anyMatch(t -> t.getName().equals("Integration Test Tag")));

        TagPutPostRequestDTO updateDto = new TagPutPostRequestDTO();
        updateDto.setName("Updated Tag");
        TagResponseDTO updated = tagService.alterar(updateDto, created.getId());
        assertEquals("Updated Tag", updated.getName());

        tagService.remover(created.getId());
        List<TagResponseDTO> finalList = tagService.listar();
        assertTrue(finalList.stream().noneMatch(t -> t.getId().equals(created.getId())));
    }

    @Test
    void disciplinaLifecycle_ShouldWork() {
        DisciplinaPutPostRequestDTO dto = new DisciplinaPutPostRequestDTO();
        dto.setNome("Integration Test Disciplina");

        DisciplinaResponseDTO created = disciplinaService.criar(dto);
        assertNotNull(created.getId());
        assertEquals("Integration Test Disciplina", created.getNome());

        List<DisciplinaResponseDTO> list = disciplinaService.listar();
        assertTrue(list.stream().anyMatch(d -> d.getNome().equals("Integration Test Disciplina")));

        DisciplinaPutPostRequestDTO updateDto = new DisciplinaPutPostRequestDTO();
        updateDto.setNome("Updated Disciplina");
        DisciplinaResponseDTO updated = disciplinaService.alterar(updateDto, created.getId());
        assertEquals("Updated Disciplina", updated.getNome());

        disciplinaService.remover(created.getId());
        List<DisciplinaResponseDTO> finalList = disciplinaService.listar();
        assertTrue(finalList.stream().noneMatch(d -> d.getId().equals(created.getId())));
    }
}
