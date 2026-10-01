package com.DevMoitinho.bancoQuestoes.repository;

import org.springframework.data.jpa.domain.Specification;

import com.DevMoitinho.bancoQuestoes.model.Questao;

public class QuestaoSpecification {
    
    public static Specification<Questao> hasDisciplina(Long disciplinaId){
            return (root, query, cb) -> cb.equal(root.get("disciplina").get("id"), disciplinaId);
    }

}
