package org.examle.aston_practice.spellbook.service;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;

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
     * Retrieves a character by its name.
     * @param name the name of the character to retrieve, cannot be null
     * @return an Optional containing the character as a DTO if found, or an empty Optional if not found
     */
    Optional<CharacterDTO> getCharacterByName(String name);

    /**
     * Creates a new character.
     * @param characterDTO the character DTO to create, cannot be null
     */
    void createCharacter(CharacterDTO characterDTO);

    /**
     * Updates an existing character.
     * @param characterDTO the character DTO to update, cannot be null
     */
    void updateCharacter(CharacterDTO characterDTO);

    /**
     * Deletes a character by its ID.
     * @param id the ID of the character to delete
     */
    void deleteCharacter(Long id);

    /**
     * Retrieves characters by caster class.
     * @param casterClass the caster class, cannot be null
     * @return a list of characters as DTOs that match the specified caster class
     */
    List<CharacterDTO> getCharactersByCasterClass(CasterClass casterClass);

    /**
     * Adds a spell to a character's spell list.
     * @param characterId the ID of the character
     * @param spellId the ID of the spell
     */
    void addSpellToCharacter(Long characterId, Long spellId);

    /**
     * Retrieves all characters who have a specific spell.
     * @param spellName the name of the spell, cannot be null
     * @return a list of characters as DTOs that have the specified spell
     */
    List<CharacterDTO> getCharactersBySpellName(String spellName);

    /**
     * Retrieves all spells of a character by the character's name.
     * @param name the name of the character, cannot be null
     * @return a list of spells as DTOs that the character has
     */
    List<SpellDTO> getSpellsByCharacterName(String name);

    /**
     * Retrieves all spells available for a specific caster class and spell circle.
     * @param casterClass the caster class, cannot be null
     * @param spellCircle the spell circle, cannot be null
     * @return a list of spells as DTOs that match the specified caster class and spell circle
     */
    List<SpellDTO> getSpellsByCasterClassAndSpellCircle(CasterClass casterClass, SpellCircle spellCircle);

    /**
     * Adds a new spell to a character's spell list.
     * @param characterId the ID of the character
     * @param spellDTO the spell DTO to add, cannot be null
     */
    void addNewSpellToCharacter(Long characterId, SpellDTO spellDTO);
}
