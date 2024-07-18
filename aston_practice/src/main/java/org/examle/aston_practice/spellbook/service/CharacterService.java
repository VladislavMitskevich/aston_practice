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
     * @param characterName the name of the character to retrieve, cannot be null
     * @return an Optional containing the character as a DTO if found, or an empty Optional if not found
     * @throws IllegalArgumentException if the characterName is null
     */
    Optional<CharacterDTO> getCharacterByName(String characterName);

    /**
     * Creates a new character if a character with the same name does not already exist.
     * @param characterDTO the character DTO to create, cannot be null
     * @throws IllegalArgumentException if the characterDTO is null or if a character with the same name already exists
     */
    void createCharacter(CharacterDTO characterDTO);

    /**
     * Updates an existing character.
     * @param characterDTO the character DTO to update, cannot be null
     * @throws IllegalArgumentException if the characterDTO is null
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
     * @throws IllegalArgumentException if the casterClass is null
     */
    List<CharacterDTO> getCharactersByCasterClass(CasterClass casterClass);

    /**
     * Adds a spell to a character's spell list by their names.
     * @param characterName the name of the character
     * @param spellName the name of the spell
     * @throws IllegalArgumentException if the characterName or spellName is null, or if the character or spell does not exist
     */
    void addSpellToCharacterByName(String characterName, String spellName);

    /**
     * Retrieves all characters who have a specific spell.
     * @param spellName the name of the spell, cannot be null
     * @return a list of characters as DTOs that have the specified spell
     * @throws IllegalArgumentException if the spellName is null
     */
    List<CharacterDTO> getCharactersBySpellName(String spellName);

    /**
     * Retrieves all spells of a character by the character's name.
     * @param characterName the name of the character, cannot be null
     * @return a list of spells as DTOs that the character has
     * @throws IllegalArgumentException if the characterName is null
     */
    List<SpellDTO> getSpellsByCharacterName(String characterName);

    /**
     * Retrieves all spells available for a specific caster class and spell circle.
     * @param casterClass the caster class, cannot be null
     * @param spellCircle the spell circle, cannot be null
     * @return a list of spells as DTOs that match the specified caster class and spell circle
     * @throws IllegalArgumentException if the casterClass or spellCircle is null
     */
    List<SpellDTO> getSpellsByCasterClassAndSpellCircle(CasterClass casterClass, SpellCircle spellCircle);

    /**
     * Adds a new spell to a character's spell list.
     * @param characterId the ID of the character
     * @param spellDTO the spell DTO to add, cannot be null
     * @throws IllegalArgumentException if the spellDTO is null
     */
    void addNewSpellToCharacter(Long characterId, SpellDTO spellDTO);

    /**
     * Checks if a character with the given name exists.
     * @param characterName the name of the character to check, cannot be null
     * @return true if a character with the given name exists, false otherwise
     * @throws IllegalArgumentException if the characterName is null
     */
    boolean existsByCharacterName(String characterName);
}
