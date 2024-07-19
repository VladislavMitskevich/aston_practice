package org.examle.aston_practice.spellbook.service;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.exception.SpellNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing spells.
 */
public interface SpellService {
    /**
     * Retrieves all spells.
     * @return a list of all spells as DTOs
     */
    List<SpellDTO> getAllSpells();

    /**
     * Retrieves a spell by its ID.
     * @param id the ID of the spell to retrieve
     * @return an Optional containing the spell as a DTO if found, or an empty Optional if not found
     */
    Optional<SpellDTO> getSpellById(Long id);

    /**
     * Retrieves a spell by its name.
     * @param spellName the name of the spell to retrieve, cannot be null
     * @return an Optional containing the spell as a DTO if found, or an empty Optional if not found
     * @throws IllegalArgumentException if the spellName is null
     */
    Optional<SpellDTO> getSpellByName(String spellName);

    /**
     * Creates a new spell if a spell with the same name does not already exist.
     * @param spellDTO the spell DTO to create, cannot be null
     * @throws IllegalArgumentException if the spellDTO is null or if a spell with the same name already exists
     */
    void createSpell(SpellDTO spellDTO);

    /**
     * Updates an existing spell.
     * @param spellDTO the spell DTO to update, cannot be null
     * @throws IllegalArgumentException if the spellDTO is null
     */
    void updateSpell(SpellDTO spellDTO);

    /**
     * Deletes a spell by its ID.
     * @param id the ID of the spell to delete
     */
    void deleteSpell(Long id);

    /**
     * Retrieves spells by caster class and circle.
     * @param casterClass the caster class, cannot be null
     * @param circle the circle, cannot be null
     * @return a list of spells as DTOs that match the specified caster class and circle
     * @throws IllegalArgumentException if the casterClass or circle is null
     */
    List<SpellDTO> getSpellsByCasterClassAndCircle(CasterClass casterClass, SpellCircle circle);

    /**
     * Retrieves all characters who can cast a specific spell.
     * @param spellName the name of the spell, cannot be null
     * @return a list of characters as DTOs that can cast the specified spell
     * @throws IllegalArgumentException if the spellName is null
     */
    List<CharacterDTO> getCharactersBySpellName(String spellName);

    /**
     * Checks if a spell with the given name exists.
     * @param spellName the name of the spell to check, cannot be null
     * @return true if a spell with the given name exists, false otherwise
     * @throws IllegalArgumentException if the spellName is null
     */
    boolean existsBySpellName(String spellName);

    /**
     * Retrieves a spell by its name.
     * @param spellName the name of the spell to retrieve, cannot be null
     * @return the spell as a DTO
     * @throws SpellNotFoundException if the spell with the given name is not found
     * @throws IllegalArgumentException if the spellName is null
     */
    SpellDTO findSpellByName(String spellName) throws SpellNotFoundException;
}
