package  com.DevMoitinho.bancoQuestoes.service.disciplina;

import java.util.List;

import com.DevMoitinho.bancoQuestoes.dto.disciplina.DisciplinaPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.disciplina.DisciplinaResponseDTO;

public interface DisciplinaService{

    public DisciplinaResponseDTO criar(DisciplinaPutPostRequestDTO disciplina);
    public List<DisciplinaResponseDTO> listar();
    public DisciplinaResponseDTO alterar(DisciplinaPutPostRequestDTO disciplina, Long id);
    public DisciplinaResponseDTO remover(Long id);
}
