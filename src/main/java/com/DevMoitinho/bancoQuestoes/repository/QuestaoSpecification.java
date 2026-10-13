package com.DevMoitinho.bancoQuestoes.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.DevMoitinho.bancoQuestoes.model.Questao;
import com.DevMoitinho.bancoQuestoes.model.Tag;

import jakarta.persistence.criteria.Join;

public class QuestaoSpecification {

    public static Specification<Questao> hasDisciplina(Long disciplinaId) {
        return (root, query, cb) -> cb.equal(root.get("disciplina").get("id"), disciplinaId);
    }

    public static Specification<Questao> hasTags(List<Long> ids) {
        return (root, query, cb) -> {
            if(ids == null || ids.isEmpty()){
                return cb.conjunction();
            }
            query.distinct(true);

            Join<Questao,Tag> tagsJoin = root.join("tags");

            return tagsJoin.get("id").in(ids);
        };
    }

    public static Specification<Questao> searchText(String text) {
        return (root, query, cb) -> {
            return cb.isTrue(
                    cb.function("fts_match", Boolean.class, root.get("searchTs"), cb.literal(text)));
        };
    }
}
