package com.DevMoitinho.bancoQuestoes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DevMoitinho.bancoQuestoes.dto.disciplina.DisciplinaPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.service.disciplina.DisciplinaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing disciplines.
 */
@RestController
@RequestMapping(value = "/disciplina")
@RequiredArgsConstructor
public class DisciplinaController{

    private final DisciplinaService disciplinaService;

    /**
     * Retrieves a list of all disciplines.
     *
     * @return A response entity containing the list of disciplines.
     */
    @GetMapping("/list")
    public ResponseEntity<?> listarDisciplinas(){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(disciplinaService.listar());
    }

    /**
     * Creates a new discipline.
     *
     * @param disciplina The discipline data to create.
     * @return A response entity containing the created discipline.
     */
    @PostMapping()
    public ResponseEntity<?> criarDisciplina(@RequestBody @Valid DisciplinaPutPostRequestDTO disciplina){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(disciplinaService.criar(disciplina));
    }

    /**
     * Deletes a discipline by its ID.
     *
     * @param id The ID of the discipline to delete.
     * @return A response entity containing the deleted discipline.
     */
    @DeleteMapping("/{idQuestao}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(disciplinaService.remover(id));
    }
    /**
     * Updates an existing discipline.
     *
     * @param disciplina The updated discipline data.
     * @param id The ID of the discipline to update.
     * @return A response entity containing the updated discipline.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@RequestBody @Valid DisciplinaPutPostRequestDTO disciplina,
                                        @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(disciplinaService.alterar(disciplina,id));
    }
}
