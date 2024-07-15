package org.examle.aston_practice.spellbook.entity;

import org.examle.aston_practice.spellbook.enums.CasterClass;
import lombok.Data;
import org.examle.aston_practice.spellbook.enums.SpellCircle;

import java.util.List;
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
     * Caster class of the character.
     */
    private CasterClass casterClass;

    /**
     * Level of the character.
     */
    private int level;

    /**
     * Map of spells available to the character by circle.
     */
    private Map<SpellCircle, List<Spell>> spellsByCircle;

    /**
     * Returns a string representation of the character.
     * @return character details as a string
     */
    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", casterClass=" + casterClass +
                ", level=" + level +
                ", spellsByCircle=" + spellsByCircle +
                '}';
    }
}
