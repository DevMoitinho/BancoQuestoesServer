package com.DevMoitinho.bancoQuestoes.service.questao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoResponseDTO;
import com.DevMoitinho.bancoQuestoes.exception.disciplina.DisciplinaNotFoundException;
import com.DevMoitinho.bancoQuestoes.exception.questao.QuestaoNotFoundException;
import com.DevMoitinho.bancoQuestoes.exception.questao.QuestoesInsuficientesException;
import com.DevMoitinho.bancoQuestoes.model.Alternativa;
import com.DevMoitinho.bancoQuestoes.model.Disciplina;
import com.DevMoitinho.bancoQuestoes.model.Questao;
import com.DevMoitinho.bancoQuestoes.repository.DisciplinaRepository;
import com.DevMoitinho.bancoQuestoes.repository.QuestaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestaoServiceImpl implements QuestaoService {

    private final ModelMapper modelMapper;
    private final QuestaoRepository repository;
    private final DisciplinaRepository disciplinaRepository;

    @Override
    public List<QuestaoResponseDTO> listar() {
        List<Questao> questoes = repository.findAll();
        return questoes.stream()
                .map(questao -> modelMapper.map(questao, QuestaoResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public QuestaoResponseDTO criar(QuestaoPutPostRequestDTO questao) {
        Questao newQ = modelMapper.map(questao, Questao.class);
        newQ.setId(null);

        newQ.getAlternativas().forEach(alt -> alt.setQuestao(newQ));

        Optional<Disciplina> disciplina = disciplinaRepository.findById(questao.getDisciplinaId());

        if(disciplina.isEmpty()){
            throw new DisciplinaNotFoundException();
        }

        newQ.setDisciplina(disciplina.get());

        repository.save(newQ);
        return modelMapper.map(newQ, QuestaoResponseDTO.class);
    }

    @Override
    public QuestaoResponseDTO remover(Long idQuestao) {
        Optional<Questao> questaoOptional = repository.findById(idQuestao);
        if (questaoOptional.isEmpty()) {
            throw new QuestaoNotFoundException();
        }
        Questao questao = questaoOptional.get();
        repository.delete(questao);
        return modelMapper.map(questao, QuestaoResponseDTO.class);
    }

    @Override
    public QuestaoResponseDTO alterar(QuestaoPutPostRequestDTO questao, Long idQuestao) {
        Optional<Questao> questaoOptional = repository.findById(idQuestao);
        if (questaoOptional.isEmpty()) {
            throw new QuestaoNotFoundException();
        }
        Questao questaoExistente = questaoOptional.get();
        Questao questaoNova = modelMapper.map(questao, Questao.class); 
        questaoExistente.setEnunciado(questaoNova.getEnunciado());

        questaoExistente.getAlternativas().clear(); 
        questaoNova.getAlternativas().forEach(alt -> {
            alt.setQuestao(questaoExistente);
            questaoExistente.getAlternativas().add(alt);
        });

        repository.save(questaoExistente); 

        return modelMapper.map(questaoExistente, QuestaoResponseDTO.class);

    }

	@Override
	public List<QuestaoResponseDTO> listarPorDisciplina(Long idDisciplina) {
        List<Questao> questoes = repository.findByDisciplinaId(idDisciplina);
        return questoes.stream()
                .map(questao -> modelMapper.map(questao, QuestaoResponseDTO.class))
                .collect(Collectors.toList());

	}

	@Override
	public List<QuestaoResponseDTO> gerarProva(Long idDisciplina, int tamanho) {
        //carrega as questões da disciplina, embaralhadas
        List<Questao> q = repository.findByDisciplinaId(idDisciplina);

        if(q.size() < tamanho){
            throw new QuestoesInsuficientesException();
        }

        Collections.shuffle(q);
        List<Questao> questoesFinal = new ArrayList<Questao>();

        for(int i=0;i<tamanho;i++){
            Questao finale = new Questao();
            Questao base = q.get(i);

            finale.setEnunciado(base.getEnunciado());

            List<Alternativa> alt = new ArrayList<>(base.getAlternativas());
            Collections.shuffle(alt);
            finale.setAlternativas(alt);
            questoesFinal.add(finale);
        }

        return questoesFinal.stream()
                .map(questao -> modelMapper.map(questao, QuestaoResponseDTO.class))
                .collect(Collectors.toList());

	}

}
