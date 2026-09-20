package com.DevMoitinho.bancoQuestoes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a possible answer alternative for a question.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alternativa {

    /**
     * Unique identifier of the alternative.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * The text of the answer alternative.
     */
    @Column(nullable = false)
    private String enunciado;

    /**
     * Indicates if this alternative is the correct answer.
     */
    @Column(nullable = false)
    private boolean correta;

    /**
     * The question to which this alternative belongs.
     */
    @ManyToOne
    @JoinColumn(name="questao_id")
    private Questao questao;
}
