package org.examle.aston_practice.spellbook.service;

import org.examle.aston_practice.spellbook.dto.SpellDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for spell operations
 */
public interface SpellService {

    /**
     * Finds all spells
     * @return list of spells
     */
    List<SpellDTO> findAll();

    /**
     * Finds a spell by id
     * @param id the spell id
     * @return optional spell
     */
    Optional<SpellDTO> findById(Long id);

    /**
     * Saves a spell
     * @param spellDTO the spell to save
     */
    void save(SpellDTO spellDTO);

    /**
     * Updates a spell
     * @param spellDTO the spell to update
     */
    void update(SpellDTO spellDTO);

    /**
     * Deletes a spell by id
     * @param id the spell id
     */
    void delete(Long id);

    /**
     * Finds spells by class and circle
     * @param spellClass the spell class
     * @param circle the circle
     * @return list of spells
     */
    List<SpellDTO> findByClassAndCircle(String spellClass, String circle);
}
