package org.examle.aston_practice.spellbook.entity;

import lombok.Data;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.enums.SpellSchool;

/**
 * Entity representing a Spell.
 */
@Data
public class Spell {
    private Long id;
    private String name;
    private String description;
    private SpellSchool school; // School of magic
    private SpellCircle circle; // Circle of the spell
    private String spellClass; // Class of the spell in format "Class(Circle)"
}
