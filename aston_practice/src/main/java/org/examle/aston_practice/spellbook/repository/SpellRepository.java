package org.examle.aston_practice.spellbook.repository;

import org.examle.aston_practice.spellbook.entity.Spell;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Spell operations.
 */
public interface SpellRepository {
    List<Spell> findAll(); // Retrieve all spells
    Optional<Spell> findById(Long id); // Retrieve a spell by ID
    void save(Spell spell); // Save a new spell
    void update(Spell spell); // Update an existing spell
    void delete(Long id); // Delete a spell by ID
    List<Spell> findByClass(Long classId); // Retrieve spells by class ID
    List<Spell> findByCircle(String circle); // Retrieve spells by circle
    List<Spell> findBySchool(String school); // Retrieve spells by school
}
