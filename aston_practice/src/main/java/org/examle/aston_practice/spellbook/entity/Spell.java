package org.examle.aston_practice.spellbook.entity;

import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SchoolOfMagic;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import lombok.Data;

import java.util.Set;

/**
 * Entity representing a Spell.
 * This class is used to represent a spell in the database.
 */
@Data
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
}
