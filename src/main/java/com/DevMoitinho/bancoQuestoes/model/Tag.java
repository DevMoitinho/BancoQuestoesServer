package com.DevMoitinho.bancoQuestoes.model;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a tag of the system.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag{

    /**
     * Unique identifier of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique name.
     */
    @Column(nullable=false, unique=true)
    private String name;

    /**
     * Reference to questions.
     */
    @ManyToMany(mappedBy = "tags")
    private List<Questao> questoes;

}
