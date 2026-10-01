package com.DevMoitinho.bancoQuestoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DevMoitinho.bancoQuestoes.model.Questao;

/**
 * Repository interface for accessing Questao entities.
 */
public interface QuestaoRepository extends JpaRepository<Questao, Long>{
    
    public List<Questao> findByDisciplinaId(Long idDisciplina);

    @Query(value="select * from questao q where q.search_ts @@ to_tsquery('portuguese',?1) and q.disciplina_id = ?2 order by ts_rank(q.search_ts, to_tsquery('portuguese',?1)) desc", nativeQuery=true)
    public List<Questao> pesquisar(String pesquisa, Long disciplinaId);

    List<Questao> findByTagsId(Long tagId);

}
