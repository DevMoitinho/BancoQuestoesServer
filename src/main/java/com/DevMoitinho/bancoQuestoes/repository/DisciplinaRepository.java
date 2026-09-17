package com.DevMoitinho.bancoQuestoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DevMoitinho.bancoQuestoes.model.Disciplina;

/**
 * Repository interface for accessing Disciplina entities.
 */
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
}
