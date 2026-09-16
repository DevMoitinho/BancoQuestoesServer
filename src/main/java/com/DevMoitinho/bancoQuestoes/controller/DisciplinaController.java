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

@RestController
@RequestMapping(value = "/disciplina")
@RequiredArgsConstructor
public class DisciplinaController{

    private final DisciplinaService disciplinaService;

    @GetMapping("/list")
    public ResponseEntity<?> listarDisciplinas(){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(disciplinaService.listar());
    }

    @PostMapping()
    public ResponseEntity<?> criarDisciplina(@RequestBody @Valid DisciplinaPutPostRequestDTO disciplina){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(disciplinaService.criar(disciplina));
    }

    @DeleteMapping("/{idQuestao}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(disciplinaService.remover(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@RequestBody @Valid DisciplinaPutPostRequestDTO disciplina,
                                        @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(disciplinaService.alterar(disciplina,id));
    }
}
