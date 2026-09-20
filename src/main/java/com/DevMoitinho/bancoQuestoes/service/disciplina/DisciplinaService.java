package  com.DevMoitinho.bancoQuestoes.service.disciplina;

import java.util.List;

import com.DevMoitinho.bancoQuestoes.dto.disciplina.DisciplinaPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.disciplina.DisciplinaResponseDTO;

/**
 * Service interface for managing disciplines.
 */
public interface DisciplinaService{

    /**
     * Creates a new discipline.
     *
     * @param disciplina The discipline data to create.
     * @return The created discipline as a response DTO.
     */
    public DisciplinaResponseDTO criar(DisciplinaPutPostRequestDTO disciplina);

    /**
     * Lists all disciplines.
     *
     * @return A list of all disciplines.
     */
    public List<DisciplinaResponseDTO> listar();

    /**
     * Updates an existing discipline.
     *
     * @param disciplina The updated discipline data.
     * @param id The ID of the discipline to update.
     * @return The updated discipline as a response DTO.
     */
    public DisciplinaResponseDTO alterar(DisciplinaPutPostRequestDTO disciplina, Long id);

    /**
     * Removes a discipline from the system.
     *
     * @param id The ID of the discipline to remove.
     * @return The removed discipline as a response DTO.
     */
    public DisciplinaResponseDTO remover(Long id);
}
