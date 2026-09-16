package com.DevMoitinho.bancoQuestoes.service.questao;

import java.util.List;

import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoResponseDTO;


public interface QuestaoService {

    public List<QuestaoResponseDTO> listar();

    public List<QuestaoResponseDTO> listarPorDisciplina(Long idDisciplina);

    public QuestaoResponseDTO criar(QuestaoPutPostRequestDTO questao);

    public QuestaoResponseDTO remover(Long idQuestao);

    public QuestaoResponseDTO alterar(QuestaoPutPostRequestDTO questao, Long idQuestao);

    public List<QuestaoResponseDTO> gerarProva(Long idDisciplina, int tamanho);
}
