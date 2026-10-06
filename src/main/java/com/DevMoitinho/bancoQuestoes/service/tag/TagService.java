package  com.DevMoitinho.bancoQuestoes.service.tag;

import java.util.List;

import com.DevMoitinho.bancoQuestoes.dto.tag.TagPutPostRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.tag.TagResponseDTO;


/**
 * Service interface for managing tags.
 */
public interface TagService{

    /**
     * Creates a new tag.
     *
     * @param tag The tag data to create.
     * @return The created tag as a response DTO.
     */
    public TagResponseDTO criar(TagPutPostRequestDTO tag);

    /**
     * Lists all tags.
     *
     * @return A list of all tags.
     */
    public List<TagResponseDTO> listar();

    /**
     * Updates an existing tag.
     *
     * @param tag The updated tag data.
     * @param id The ID of the tag to update.
     * @return The updated tag as a response DTO.
     */
    public TagResponseDTO alterar(TagPutPostRequestDTO tag, Long id);

    /**
     * Removes a tag from the system.
     *
     * @param id The ID of the tag to remove.
     * @return The removed tag as a response DTO.
     */
    public TagResponseDTO remover(Long id);
}
