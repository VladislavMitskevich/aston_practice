package org.examle.aston_practice.spellbook.service;

import org.examle.aston_practice.spellbook.dto.SpellDTO;

import java.util.List;

/**
 * Service interface for Spell operations.
 */
public interface SpellService {
    List<SpellDTO> getAllSpells(); // Retrieve all spells
    SpellDTO getSpellById(Long id); // Retrieve a spell by ID
    void createSpell(SpellDTO spellDTO); // Create a new spell
    void updateSpell(SpellDTO spellDTO); // Update an existing spell
    void deleteSpell(Long id); // Delete a spell by ID
    List<SpellDTO> getSpellsByClass(String spellClass); // Retrieve spells by class
    List<SpellDTO> getSpellsByCircle(String circle); // Retrieve spells by circle
    List<SpellDTO> getSpellsBySchool(String school); // Retrieve spells by school
}
