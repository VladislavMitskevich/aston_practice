package org.examle.aston_practice.spellbook.dto;

import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Data Transfer Object for Character.
 * This class is used to transfer character data between different layers of the application.
 */
@Getter
@Setter
public class CharacterDTO {
    /**
     * Unique identifier for the character.
     */
    private Long id;

    /**
     * Name of the character. Must be unique.
     */
    private String name;

    /**
     * Caster class of the character. Determines the type of magic the character can use.
     */
    private CasterClass casterClass;

    /**
     * Level of the character. Must be between 1 and 20.
     */
    private int level;

    /**
     * Map of spells available to the character by circle.
     * Each entry represents a spell circle and the list of spells available at that circle.
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

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterDTO that = (CharacterDTO) o;
        return level == that.level &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                casterClass == that.casterClass &&
                Objects.equals(spellsByCircle, that.spellsByCircle);
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, casterClass, level, spellsByCircle);
    }
}
