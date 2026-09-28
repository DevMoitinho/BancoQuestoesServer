package com.DevMoitinho.bancoQuestoes.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a question in the question bank system.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Questao {

    /**
     * Unique identifier of the question.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * The text of the question.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String enunciado;

    /**
     * List of possible answer alternatives.
     */
    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alternativa> alternativas;

    /**
     * The discipline this question is categorized under.
     */
    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    /**
     * The tags this question is assigned.
     */
    @ManyToMany
    @JoinTable(name = "questao_tag", joinColumns = @JoinColumn(name = "questao_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

}
