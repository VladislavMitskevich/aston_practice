package org.examle.aston_practice.spellbook.dto;

import org.examle.aston_practice.spellbook.enums.CasterClass;
import lombok.Data;
import org.examle.aston_practice.spellbook.enums.SpellCircle;

import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for Character.
 * This class is used to transfer character data between different layers of the application.
 */
@Data
public class CharacterDTO {
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
    private Map<SpellCircle, List<SpellDTO>> spellsByCircle;

    /**
     * Returns a string representation of the character.
     * @return character details as a string
     */
    @Override
    public String toString() {
        return "CharacterDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", casterClass=" + casterClass +
                ", level=" + level +
                ", spellsByCircle=" + spellsByCircle +
                '}';
    }
}
