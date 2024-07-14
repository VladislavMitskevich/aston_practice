package org.examle.aston_practice.spellbook.entity;

import org.examle.aston_practice.spellbook.enums.CasterClass;
import lombok.Data;

import java.util.Map;

/**
 * Entity representing a Character.
 * This class is used to represent a character in the database.
 */
@Data
public class Character {
    /**
     * Unique identifier for the character.
     */
    private Long id;

    /**
     * Name of the character.
     */
    private String name;

    /**
     * Map containing the spell limits for each caster class.
     * The key is the caster class and the value is the maximum number of spells that can be cast.
     */
    private Map<CasterClass, Integer> spellLimits;

    /**
     * Returns a string representation of the character.
     * @return character details as a string
     */
    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", spellLimits=" + spellLimits +
                '}';
    }
}
