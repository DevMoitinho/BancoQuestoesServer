package com.DevMoitinho.bancoQuestoes.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a subject or discipline in the question bank system.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Disciplina {

    /**
     * Unique identifier of the discipline.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * Name of the discipline.
     */
    @Column(nullable=false, unique=true)
    private String nome;

    /**
     * List of questions associated with this discipline.
     */
    @OneToMany(mappedBy = "disciplina")
    private List<Questao> questoes;

}
