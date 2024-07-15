package org.examle.aston_practice.spellbook.service;

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
     * Creates a new spell.
     * @param spellDTO the spell DTO to create
     */
    void createSpell(SpellDTO spellDTO);

    /**
     * Updates an existing spell.
     * @param spellDTO the spell DTO to update
     */
    void updateSpell(SpellDTO spellDTO);

    /**
     * Deletes a spell by its ID.
     * @param id the ID of the spell to delete
     */
    void deleteSpell(Long id);

    /**
     * Retrieves spells by caster class and circle.
     * @param casterClass the caster class
     * @param circle the circle
     * @return a list of spells as DTOs that match the specified caster class and circle
     */
    List<SpellDTO> getSpellsByCasterClassAndCircle(CasterClass casterClass, SpellCircle circle);
}
