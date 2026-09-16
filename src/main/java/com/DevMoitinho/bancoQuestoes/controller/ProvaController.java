package com.DevMoitinho.bancoQuestoes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DevMoitinho.bancoQuestoes.service.questao.QuestaoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/questao")
@RequiredArgsConstructor
public class ProvaController{

    private final QuestaoService questaoService;

    @GetMapping("/{idDisciplina}-{tamanho}")
    public ResponseEntity<?> gerarProva(@PathVariable Long idDisciplina, @PathVariable int tamanho){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(questaoService.gerarProva(idDisciplina, tamanho));
    }
}
