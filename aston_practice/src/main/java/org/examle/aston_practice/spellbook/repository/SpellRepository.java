package org.examle.aston_practice.spellbook.repository;

import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;

import java.util.List;
import java.util.Optional;

/**
 * Interface for spell repository.
 * This interface defines the methods for CRUD operations on Spell entities.
 */
public interface SpellRepository {

    /**
     * Finds all spells from the database.
     * @return list of all spells
     */
    List<Spell> findAll();

    /**
     * Finds a spell by its ID.
     * @param id the ID of the spell to retrieve
     * @return an Optional containing the spell if found, or an empty Optional if not found
     */
    Optional<Spell> findById(Long id);

    /**
     * Saves a new spell to the database.
     * @param spell the spell to save
     */
    void save(Spell spell);

    /**
     * Updates an existing spell in the database.
     * @param spell the spell to update
     */
    void update(Spell spell);

    /**
     * Deletes a spell by its ID.
     * @param id the ID of the spell to delete
     */
    void deleteById(Long id);

    /**
     * Finds spells by caster class and circle.
     * @param casterClass the caster class
     * @param circle the circle
     * @return list of spells that match the specified caster class and circle
     */
    List<Spell> findByCasterClassAndCircle(CasterClass casterClass, SpellCircle circle);
}
