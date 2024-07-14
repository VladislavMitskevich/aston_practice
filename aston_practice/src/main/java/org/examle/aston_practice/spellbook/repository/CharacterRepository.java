package org.examle.aston_practice.spellbook.repository;

import org.examle.aston_practice.spellbook.entity.Character;

import java.util.List;

/**
 * Interface for Character repository
 */
public interface CharacterRepository {
    List<Character> findAll();
    Character findById(Long id);
    void save(Character character);
    void update(Character character);
    void deleteById(Long id);
}
