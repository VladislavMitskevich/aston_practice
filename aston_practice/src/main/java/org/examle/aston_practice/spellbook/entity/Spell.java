package org.examle.aston_practice.spellbook.entity;

import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SchoolOfMagic;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

/**
 * Entity representing a Spell.
 * This class is used to represent a spell in the database.
 */
@Getter
@Setter
public class Spell {
    /**
     * Unique identifier for the spell.
     */
    private Long id;

    /**
     * Name of the spell.
     */
    private String name;

    /**
     * School of magic to which the spell belongs.
     */
    private SchoolOfMagic school;

    /**
     * Circle of the spell.
     */
    private SpellCircle circle;

    /**
     * Set of caster classes that can cast the spell.
     */
    private Set<CasterClass> casterClasses;

    /**
     * Description of the spell.
     */
    private String description;

    /**
     * Returns a string representation of the spell.
     * @return spell details as a string
     */
    @Override
    public String toString() {
        return "Spell{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", school=" + school +
                ", circle=" + circle +
                ", casterClasses=" + casterClasses +
                ", description='" + description + '\'' +
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
        Spell spell = (Spell) o;
        return Objects.equals(id, spell.id) &&
                Objects.equals(name, spell.name) &&
                school == spell.school &&
                circle == spell.circle &&
                Objects.equals(casterClasses, spell.casterClasses) &&
                Objects.equals(description, spell.description);
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, school, circle, casterClasses, description);
    }
}
