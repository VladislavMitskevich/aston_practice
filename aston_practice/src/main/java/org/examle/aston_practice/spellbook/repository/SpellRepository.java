package org.examle.aston_practice.spellbook.repository;

import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.Circle;
import org.examle.aston_practice.spellbook.enums.School;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Spell.
 */
public interface SpellRepository {
    List<Spell> findAll(); // Find all spells
    Optional<Spell> findById(Long id); // Find spell by ID
    Spell save(Spell spell); // Save a new spell
    void update(Spell spell); // Update an existing spell
    void delete(Long id); // Delete a spell by ID
    List<Spell> findByCircle(Circle circle); // Find spells by circle
    List<Spell> findByClass(Long classId); // Find spells by class ID
    List<Spell> findBySchool(School school); // Find spells by school
}
