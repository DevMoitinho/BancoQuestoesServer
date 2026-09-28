package com.DevMoitinho.bancoQuestoes.service.questao;

import java.util.List;

import com.DevMoitinho.bancoQuestoes.dto.PesquisaPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoResponseDTO;


/**
 * Service interface for managing questions and generating exams.
 */
public interface QuestaoService {

    /**
     * Lists all questions in the system.
     *
     * @return A list of all questions.
     */
    public List<QuestaoResponseDTO> listar();

    /**
     * Lists questions associated with a specific discipline.
     *
     * @param idDisciplina The ID of the discipline to filter by.
     * @return A list of questions belonging to the specified discipline.
     */
    public List<QuestaoResponseDTO> listarPorDisciplina(Long idDisciplina);

    /**
     * Lists all questions that contains a certain string.
     *
     * @return A list of all questions.
     */
    public List<QuestaoResponseDTO> pesquisar(PesquisaPutPostRequestDTO pesquisa);

    /**
     * Creates a new question.
     *
     * @param questao The question data to create.
     * @return The created question as a response DTO.
     */
    public QuestaoResponseDTO criar(QuestaoPutPostRequestDTO questao);

    /**
     * Removes a question from the system.
     *
     * @param idQuestao The ID of the question to remove.
     * @return The removed question as a response DTO.
     */
    public QuestaoResponseDTO remover(Long idQuestao);

    /**
     * Updates an existing question.
     *
     * @param questao The updated question data.
     * @param idQuestao The ID of the question to update.
     * @return The updated question as a response DTO.
     */
    public QuestaoResponseDTO alterar(QuestaoPutPostRequestDTO questao, Long idQuestao);

    /**
     * Generates a random set of questions for a given discipline to create an exam.
     *
     * @param idDisciplina The ID of the discipline.
     * @param tamanho The number of questions to select for the exam.
     * @return A list of randomly selected questions for the exam.
     */
    public List<QuestaoResponseDTO> gerarProva(Long idDisciplina, int tamanho);
}
