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

import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.service.questao.QuestaoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing questions.
 */
@RestController
@RequestMapping(value = "/questao")
@RequiredArgsConstructor
public class QuestaoController{

    private final QuestaoService questaoService;

    /**
     * Retrieves a list of all questions.
     *
     * @return A response entity containing the list of questions.
     */
    @GetMapping("/list")
    public ResponseEntity<?> listarQuestoes(){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(questaoService.listar());
    }

    /**
     * Retrieves questions for a specific discipline.
     *
     * @param idDisciplina The ID of the discipline.
     * @return A response entity containing the filtered list of questions.
     */
    @GetMapping("/list/{idDisciplina}")
    public ResponseEntity<?> listarQuestoesPorDisciplina(@PathVariable Long idDisciplina){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(questaoService.listarPorDisciplina(idDisciplina));
    }

    /**
     * Creates a new question.
     *
     * @param questao The question data to create.
     * @return A response entity containing the created question.
     */
    @PostMapping()
    public ResponseEntity<?> criarQuestao(@RequestBody @Valid QuestaoPutPostRequestDTO questao){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(questaoService.criar(questao));
    }

    /**
     * Deletes a question by its ID.
     *
     * @param idQuestao The ID of the question to delete.
     * @return A response entity containing the deleted question.
     */
    @DeleteMapping("/{idQuestao}")
    public ResponseEntity<?> deletar(@PathVariable Long idQuestao){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(questaoService.remover(idQuestao));
    }
    /**
     * Updates an existing question.
     *
     * @param questao The updated question data.
     * @param idQuestao The ID of the question to update.
     * @return A response entity containing the updated question.
     */
    @PutMapping("/{idQuestao}")
    public ResponseEntity<?> alterar(@RequestBody @Valid QuestaoPutPostRequestDTO questao,
                                        @PathVariable Long idQuestao){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(questaoService.alterar(questao,idQuestao));
    }
}
