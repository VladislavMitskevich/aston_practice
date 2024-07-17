package org.examle.aston_practice.spellbook.service;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;

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
     * @param name the name of the spell to retrieve, cannot be null
     * @return an Optional containing the spell as a DTO if found, or an empty Optional if not found
     */
    Optional<SpellDTO> getSpellByName(String name);

    /**
     * Creates a new spell.
     * @param spellDTO the spell DTO to create, cannot be null
     */
    void createSpell(SpellDTO spellDTO);

    /**
     * Updates an existing spell.
     * @param spellDTO the spell DTO to update, cannot be null
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
     */
    List<SpellDTO> getSpellsByCasterClassAndCircle(CasterClass casterClass, SpellCircle circle);

    /**
     * Retrieves all characters who can cast a specific spell.
     * @param spellName the name of the spell, cannot be null
     * @return a list of characters as DTOs that can cast the specified spell
     */
    List<CharacterDTO> getCharactersBySpellName(String spellName);
}
