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

@RestController
@RequestMapping(value = "/questao")
@RequiredArgsConstructor
public class QuestaoController{

    private final QuestaoService questaoService;

    @GetMapping("/list")
    public ResponseEntity<?> listarQuestoes(){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(questaoService.listar());
    }

    @GetMapping("/list/{idDisciplina}")
    public ResponseEntity<?> listarQuestoesPorDisciplina(@PathVariable Long idDisciplina){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(questaoService.listarPorDisciplina(idDisciplina));
    }

    @PostMapping()
    public ResponseEntity<?> criarQuestao(@RequestBody @Valid QuestaoPutPostRequestDTO questao){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(questaoService.criar(questao));
    }

    @DeleteMapping("/{idQuestao}")
    public ResponseEntity<?> deletar(@PathVariable Long idQuestao){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(questaoService.remover(idQuestao));
    }
    @PutMapping("/{idQuestao}")
    public ResponseEntity<?> alterar(@RequestBody @Valid QuestaoPutPostRequestDTO questao,
                                        @PathVariable Long idQuestao){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(questaoService.alterar(questao,idQuestao));
    }
}
