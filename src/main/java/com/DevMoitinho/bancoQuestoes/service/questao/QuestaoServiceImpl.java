package com.DevMoitinho.bancoQuestoes.service.questao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.DevMoitinho.bancoQuestoes.dto.PesquisaPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.questao.QuestaoResponseDTO;
import com.DevMoitinho.bancoQuestoes.exception.disciplina.DisciplinaNotFoundException;
import com.DevMoitinho.bancoQuestoes.exception.questao.QuestaoNotFoundException;
import com.DevMoitinho.bancoQuestoes.exception.questao.QuestoesInsuficientesException;
import com.DevMoitinho.bancoQuestoes.model.Alternativa;
import com.DevMoitinho.bancoQuestoes.model.Disciplina;
import com.DevMoitinho.bancoQuestoes.model.Questao;
import com.DevMoitinho.bancoQuestoes.model.Tag;
import com.DevMoitinho.bancoQuestoes.repository.DisciplinaRepository;
import com.DevMoitinho.bancoQuestoes.repository.QuestaoRepository;
import com.DevMoitinho.bancoQuestoes.repository.QuestaoSpecification;
import com.DevMoitinho.bancoQuestoes.repository.TagRepository;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of the QuestaoService providing business logic for managing
 * questions and generating exams.
 */
@Service
@RequiredArgsConstructor
public class QuestaoServiceImpl implements QuestaoService {

    private final ModelMapper modelMapper;
    private final QuestaoRepository repository;
    private final DisciplinaRepository disciplinaRepository;
    private final TagRepository tagRepository;

    // FUNCOES PARA PESQUISA

    @Override
    public List<QuestaoResponseDTO> pesquisar(PesquisaPutPostRequestDTO pesquisaDTO) {
        Specification<Questao> spec = (root, query, cb) -> cb.conjunction();

        if (StringUtils.hasText(pesquisaDTO.getPesquisa())) {
            spec = spec.and(QuestaoSpecification.searchText(pesquisaDTO.getPesquisa()));
        }
        if (pesquisaDTO.getDisciplinaId() != null) {
            spec = spec.and(QuestaoSpecification.hasDisciplina(pesquisaDTO.getDisciplinaId()));
        }
        if (pesquisaDTO.getTags() != null && !pesquisaDTO.getTags().isEmpty()) {
            spec = spec.and(QuestaoSpecification.hasTags(pesquisaDTO.getTags()));
        }

        List<Questao> questoes = repository.findAll(spec);
        return questoes.stream()
                .map(q -> modelMapper.map(q, QuestaoResponseDTO.class))
                .collect(Collectors.toList());
    }

    // FUNCOES PARA CRIAR/ALTERAR/REMOVER
    @Transactional
    @Override
    public QuestaoResponseDTO criar(QuestaoPutPostRequestDTO questao) {
        Questao newQ = modelMapper.map(questao, Questao.class);
        newQ.setId(null);

        newQ.getAlternativas().forEach(alt -> alt.setQuestao(newQ));

        Optional<Disciplina> disciplina = disciplinaRepository.findById(questao.getDisciplinaId());

        if (disciplina.isEmpty()) {
            throw new DisciplinaNotFoundException();
        }
        if (questao.getTagsIds() != null) {
            List<Tag> tags = tagRepository.findAllById(questao.getTagsIds());
            newQ.setTags(tags);
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

    @Transactional
    @Override
    public QuestaoResponseDTO alterar(QuestaoPutPostRequestDTO questao, Long idQuestao) {
        Optional<Questao> questaoOptional = repository.findById(idQuestao);
        if (questaoOptional.isEmpty()) {
            throw new QuestaoNotFoundException();
        }
        Questao questaoExistente = questaoOptional.get();
        Questao questaoNova = modelMapper.map(questao, Questao.class);
        questaoExistente.setEnunciado(questaoNova.getEnunciado());
        Optional<Disciplina> disciplina = disciplinaRepository.findById(questao.getDisciplinaId());

        if (disciplina.isEmpty()) {
            throw new DisciplinaNotFoundException();
        }
        questaoExistente.setDisciplina(disciplina.get());

        questaoExistente.getAlternativas().clear();
        questaoNova.getAlternativas().forEach(alt -> {
            alt.setQuestao(questaoExistente);
            questaoExistente.getAlternativas().add(alt);
        });

        questaoExistente.getTags().clear();

        if (questao.getTagsIds() != null) {
            List<Tag> novasTags = tagRepository.findAllById(questao.getTagsIds());
            questaoExistente.getTags().addAll(novasTags);
        }

        repository.save(questaoExistente);

        return modelMapper.map(questaoExistente, QuestaoResponseDTO.class);

    }

    @Override
    public List<QuestaoResponseDTO> gerarProva(Long idDisciplina, int tamanho) {
        // carrega as questões da disciplina, embaralhadas
        List<Questao> q = repository.findByDisciplinaId(idDisciplina);

        if (q.size() < tamanho) {
            throw new QuestoesInsuficientesException();
        }

        Collections.shuffle(q);
        List<Questao> questoesFinal = new ArrayList<Questao>();

        for (int i = 0; i < tamanho; i++) {
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
