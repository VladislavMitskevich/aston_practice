package org.examle.aston_practice.spellbook.dto;

import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SchoolOfMagic;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import lombok.Data;

/**
 * Data Transfer Object for Spell.
 * This class is used to transfer spell data between different layers of the application.
 */
@Data
public class SpellDTO {
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
     * Caster class that can cast the spell.
     */
    private CasterClass casterClass;

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
                ", casterClass=" + casterClass +
                '}';
    }
}
