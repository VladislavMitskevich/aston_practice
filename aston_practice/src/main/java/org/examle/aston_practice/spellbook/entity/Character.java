package org.examle.aston_practice.spellbook.entity;

import org.examle.aston_practice.spellbook.enums.CasterClass;
import lombok.Data;

import java.util.Map;

/**
 * Entity representing a Character
 */
@Data
public class Character {
    private Long id;
    private String name;
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
