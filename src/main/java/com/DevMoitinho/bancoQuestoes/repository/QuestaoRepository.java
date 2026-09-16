package com.DevMoitinho.bancoQuestoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DevMoitinho.bancoQuestoes.model.Questao;

public interface QuestaoRepository extends JpaRepository<Questao, Long>{
    
    public List<Questao> findByDisciplinaId(Long idDisciplina);

}
