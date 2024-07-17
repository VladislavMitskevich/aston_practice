package org.examle.aston_practice.spellbook.repository;

import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;

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
     * Retrieves a character by its name.
     * @param name the name of the character to retrieve
     * @return an Optional containing the character if found, or an empty Optional if not found
     */
    Optional<Character> findByName(String name);

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

    /**
     * Finds characters by caster class.
     * @param casterClass the caster class
     * @return list of characters that match the specified caster class
     */
    List<Character> findByCasterClass(CasterClass casterClass);

    /**
     * Adds a spell to a character's spell list.
     * @param characterId the ID of the character
     * @param spellId the ID of the spell
     */
    void addSpellToCharacter(Long characterId, Long spellId);

    /**
     * Finds all characters who have a specific spell.
     * @param spellName the name of the spell
     * @return list of characters that have the specified spell
     */
    List<Character> findCharactersBySpellName(String spellName);

    /**
     * Finds all spells of a character by the character's name.
     * @param name the name of the character
     * @return list of spells that the character has
     */
    List<Spell> findSpellsByCharacterName(String name);

    /**
     * Finds all spells available for a specific caster class and spell circle.
     * @param casterClass the caster class
     * @param spellCircle the spell circle
     * @return list of spells that match the specified caster class and spell circle
     */
    List<Spell> findSpellsByCasterClassAndSpellCircle(CasterClass casterClass, SpellCircle spellCircle);

    /**
     * Adds a new spell to a character's spell list.
     * @param characterId the ID of the character
     * @param spell the spell to add
     */
    void addNewSpellToCharacter(Long characterId, Spell spell);
}
