package org.examle.aston_practice.spellbook.entity;

import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SchoolOfMagic;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import lombok.Data;

/**
 * Entity representing a Spell
 */
@Data
public class Spell {
    private Long id;
    private String name;
    private SchoolOfMagic school;
    private SpellCircle circle;
    private CasterClass casterClass;

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
                ", casterClass=" + casterClass +
                '}';
    }
}
