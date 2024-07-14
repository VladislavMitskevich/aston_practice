package org.examle.aston_practice.spellbook.repository;

import org.examle.aston_practice.spellbook.entity.Character;

import java.util.List;
import java.util.Optional;

/**
 * Interface for Character repository.
 * This interface defines the methods for CRUD operations on Character entities.
 */
public interface CharacterRepository {
    /**
     * Retrieves all characters from the database.
     * @return a list of all characters
     */
    List<Character> findAll();

    /**
     * Retrieves a character by its ID.
     * @param id the ID of the character to retrieve
     * @return an Optional containing the character if found, or an empty Optional if not found
     */
    Optional<Character> findById(Long id);

    /**
     * Saves a new character to the database.
     * @param character the character to save
     */
    void save(Character character);

    /**
     * Updates an existing character in the database.
     * @param character the character to update
     */
    void update(Character character);

    /**
     * Deletes a character by its ID.
     * @param id the ID of the character to delete
     */
    void deleteById(Long id);
}
