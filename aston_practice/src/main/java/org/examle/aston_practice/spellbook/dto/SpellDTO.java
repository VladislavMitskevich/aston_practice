package org.examle.aston_practice.spellbook.dto;

import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SchoolOfMagic;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Data Transfer Object for Spell.
 * This class is used to transfer spell data between different layers of the application.
 */
@Getter
@Setter
public class SpellDTO {
    /**
     * Unique identifier for the spell.
     */
    private Long id;

    /**
     * Name of the spell. Must be unique.
     */
    private String name;

    /**
     * School of magic to which the spell belongs. Determines the type of magic the spell uses.
     */
    private SchoolOfMagic school;

    /**
     * Circle of the spell. Determines the level of complexity and power of the spell.
     */
    private SpellCircle circle;

    /**
     * Set of caster classes that can cast the spell.
     */
    private Set<CasterClass> casterClasses;

    /**
     * Description of the spell. Provides details about what the spell does.
     */
    private String description;

    /**
     * List of characters that can cast this spell.
     */
    private List<CharacterDTO> characters;

    /**
     * Returns a string representation of the spell.
     * @return spell details as a string
     */
    @Override
    public String toString() {
        return "SpellDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", school=" + school +
                ", circle=" + circle +
                ", casterClasses=" + casterClasses +
                ", description='" + description + '\'' +
                ", characters=" + characters +
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
        SpellDTO spellDTO = (SpellDTO) o;
        return Objects.equals(id, spellDTO.id) &&
                Objects.equals(name, spellDTO.name) &&
                school == spellDTO.school &&
                circle == spellDTO.circle &&
                Objects.equals(casterClasses, spellDTO.casterClasses) &&
                Objects.equals(description, spellDTO.description) &&
                Objects.equals(characters, spellDTO.characters);
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, school, circle, casterClasses, description, characters);
    }
}
