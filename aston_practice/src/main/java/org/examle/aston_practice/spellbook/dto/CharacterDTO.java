package org.examle.aston_practice.spellbook.dto;

import org.examle.aston_practice.spellbook.enums.CasterClass;
import lombok.Data;

import java.util.Map;

/**
 * Data Transfer Object for Character
 */
@Data
public class CharacterDTO {
    private Long id;
    private String name;
    private Map<CasterClass, Integer> spellLimits;

    /**
     * Returns a string representation of the character.
     * @return character details as a string
     */
    @Override
    public String toString() {
        return "CharacterDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", spellLimits=" + spellLimits +
                '}';
    }
}
