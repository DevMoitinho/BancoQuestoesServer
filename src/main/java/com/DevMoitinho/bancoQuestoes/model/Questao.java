package com.DevMoitinho.bancoQuestoes.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable=false)
    private String enunciado;

    @OneToMany(mappedBy="questao", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Alternativa> alternativas;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

}
