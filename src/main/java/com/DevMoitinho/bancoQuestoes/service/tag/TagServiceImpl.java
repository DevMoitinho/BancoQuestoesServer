package com.DevMoitinho.bancoQuestoes.service.tag;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.DevMoitinho.bancoQuestoes.dto.tag.TagPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.tag.TagResponseDTO;
import com.DevMoitinho.bancoQuestoes.exception.tag.TagNotFoundException;
import com.DevMoitinho.bancoQuestoes.model.Tag;
import com.DevMoitinho.bancoQuestoes.repository.TagRepository;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of the TagService providing business logic for managing disciplines.
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;

    @Override
    public List<TagResponseDTO> listar() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(tag -> modelMapper.map(tag, TagResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagResponseDTO criar(TagPutPostRequestDTO tag) {
        Tag newD = modelMapper.map(tag, Tag.class);

        tagRepository.save(newD);
        return modelMapper.map(newD, TagResponseDTO.class);
    }

    @Override
    public TagResponseDTO remover(Long id) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isEmpty()) {
            throw new TagNotFoundException();
        }
        Tag tag = tagOptional.get();
        tagRepository.delete(tag);
        return modelMapper.map(tag, TagResponseDTO.class);
    }

    @Override
    public TagResponseDTO alterar(TagPutPostRequestDTO tag, Long id) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isEmpty()) {
            throw new TagNotFoundException();
        }
        Tag tagExistente = tagOptional.get();
        Tag tagNova = modelMapper.map(tag, Tag.class); 
        tagExistente.setName(tagNova.getName());

        tagRepository.save(tagExistente); 

        return modelMapper.map(tagExistente, TagResponseDTO.class);

    }

}
