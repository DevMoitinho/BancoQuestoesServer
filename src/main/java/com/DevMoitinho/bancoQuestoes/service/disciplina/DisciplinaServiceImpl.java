package com.DevMoitinho.bancoQuestoes.service.disciplina;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.DevMoitinho.bancoQuestoes.dto.disciplina.DisciplinaPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.disciplina.DisciplinaResponseDTO;
import com.DevMoitinho.bancoQuestoes.exception.disciplina.DisciplinaNotFoundException;
import com.DevMoitinho.bancoQuestoes.model.Disciplina;
import com.DevMoitinho.bancoQuestoes.repository.DisciplinaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of the DisciplinaService providing business logic for managing disciplines.
 */
@Service
@RequiredArgsConstructor
public class DisciplinaServiceImpl implements DisciplinaService {

    private final ModelMapper modelMapper;
    private final DisciplinaRepository disciplinaRepository;

    @Override
    public List<DisciplinaResponseDTO> listar() {
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        return disciplinas.stream()
                .map(disciplina -> modelMapper.map(disciplina, DisciplinaResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DisciplinaResponseDTO criar(DisciplinaPutPostRequestDTO disciplina) {
        Disciplina newD = modelMapper.map(disciplina, Disciplina.class);

        disciplinaRepository.save(newD);
        return modelMapper.map(newD, DisciplinaResponseDTO.class);
    }

    @Override
    public DisciplinaResponseDTO remover(Long id) {
        Optional<Disciplina> disciplinaOptional = disciplinaRepository.findById(id);
        if (disciplinaOptional.isEmpty()) {
            throw new DisciplinaNotFoundException();
        }
        Disciplina disciplina = disciplinaOptional.get();
        disciplinaRepository.delete(disciplina);
        return modelMapper.map(disciplina, DisciplinaResponseDTO.class);
    }

    @Override
    public DisciplinaResponseDTO alterar(DisciplinaPutPostRequestDTO disciplina, Long id) {
        Optional<Disciplina> disciplinaOptional = disciplinaRepository.findById(id);
        if (disciplinaOptional.isEmpty()) {
            throw new DisciplinaNotFoundException();
        }
        Disciplina disciplinaExistente = disciplinaOptional.get();
        Disciplina disciplinaNova = modelMapper.map(disciplina, Disciplina.class); 
        disciplinaExistente.setNome(disciplinaNova.getNome());

        disciplinaRepository.save(disciplinaExistente); 

        return modelMapper.map(disciplinaExistente, DisciplinaResponseDTO.class);

    }

}
