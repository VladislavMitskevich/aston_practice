package org.examle.aston_practice.spellbook.service;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.enums.CasterClass;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing characters.
 */
public interface CharacterService {
    /**
     * Retrieves all characters.
     * @return a list of all characters as DTOs
     */
    List<CharacterDTO> getAllCharacters();

    /**
     * Retrieves a character by its ID.
     * @param id the ID of the character to retrieve
     * @return an Optional containing the character as a DTO if found, or an empty Optional if not found
     */
    Optional<CharacterDTO> getCharacterById(Long id);

    /**
     * Creates a new character.
     * @param characterDTO the character DTO to create
     */
    void createCharacter(CharacterDTO characterDTO);

    /**
     * Updates an existing character.
     * @param characterDTO the character DTO to update
     */
    void updateCharacter(CharacterDTO characterDTO);

    /**
     * Deletes a character by its ID.
     * @param id the ID of the character to delete
     */
    void deleteCharacter(Long id);

    /**
     * Retrieves characters by caster class.
     * @param casterClass the caster class
     * @return a list of characters as DTOs that match the specified caster class
     */
    List<CharacterDTO> getCharactersByCasterClass(CasterClass casterClass);

    /**
     * Adds a spell to a character's spell list.
     * @param characterId the ID of the character
     * @param spellId the ID of the spell
     */
    void addSpellToCharacter(Long characterId, Long spellId);
}
