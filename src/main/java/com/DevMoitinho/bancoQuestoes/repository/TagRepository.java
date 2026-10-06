package com.DevMoitinho.bancoQuestoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DevMoitinho.bancoQuestoes.model.Tag;

/**
 * Repository interface for accessing Disciplina entities.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
}
