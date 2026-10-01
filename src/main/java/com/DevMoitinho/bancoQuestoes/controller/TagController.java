package com.DevMoitinho.bancoQuestoes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DevMoitinho.bancoQuestoes.dto.tag.TagPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.service.tag.TagService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing tags.
 */
@RestController
@RequestMapping(value = "/tag")
@RequiredArgsConstructor
public class TagController{

    private final TagService tagService;

    /**
     * Retrieves a list of all tags.
     *
     * @return A response entity containing the list of tags.
     */
    @GetMapping("/list")
    public ResponseEntity<?> listarTags(){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(tagService.listar());
    }

    /**
     * Creates a new tag.
     *
     * @param tag The tag data to create.
     * @return A response entity containing the created tag.
     */
    @PostMapping()
    public ResponseEntity<?> criarTag(@RequestBody @Valid TagPutPostRequestDTO tag){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(tagService.criar(tag));
    }

    /**
     * Deletes a tag by its ID.
     *
     * @param id The ID of the tag to delete.
     * @return A response entity containing the deleted tag.
     */
    @DeleteMapping("/{idQuestao}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(tagService.remover(id));
    }
    /**
     * Updates an existing tag.
     *
     * @param tag The updated tag data.
     * @param id The ID of the tag to update.
     * @return A response entity containing the updated tag.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@RequestBody @Valid TagPutPostRequestDTO tag,
                                        @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(tagService.alterar(tag,id));
    }
}
