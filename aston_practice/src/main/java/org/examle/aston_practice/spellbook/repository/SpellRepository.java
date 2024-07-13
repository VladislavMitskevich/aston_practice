package org.examle.aston_practice.spellbook.repository;

import org.examle.aston_practice.spellbook.entity.Spell;

import java.util.List;
import java.util.Optional;

/**
 * Interface for spell repository
 */
public interface SpellRepository {

    /**
     * Finds all spells
     * @return list of spells
     */
    List<Spell> findAll();

    /**
     * Finds a spell by id
     * @param id the spell id
     * @return optional spell
     */
    Optional<Spell> findById(Long id);

    /**
     * Saves a spell
     * @param spell the spell to save
     */
    void save(Spell spell);

    /**
     * Updates a spell
     * @param spell the spell to update
     */
    void update(Spell spell);

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
    List<Spell> findByClassAndCircle(String spellClass, String circle);
}
