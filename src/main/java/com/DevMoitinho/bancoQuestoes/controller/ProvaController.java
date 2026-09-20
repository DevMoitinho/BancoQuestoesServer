package com.DevMoitinho.bancoQuestoes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DevMoitinho.bancoQuestoes.service.questao.QuestaoService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for generating exams.
 */
@RestController
@RequestMapping(value = "/questao")
@RequiredArgsConstructor
public class ProvaController{

    private final QuestaoService questaoService;

    /**
     * Generates a random set of questions for a specific discipline to create an exam.
     *
     * @param idDisciplina The ID of the discipline.
     * @param tamanho The number of questions to select for the exam.
     * @return A response entity containing the generated exam questions.
     */
    @GetMapping("/{idDisciplina}-{tamanho}")
    public ResponseEntity<?> gerarProva(@PathVariable Long idDisciplina, @PathVariable int tamanho){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(questaoService.gerarProva(idDisciplina, tamanho));
    }
}
