package org.examle.aston_practice.spellbook.entity;

import lombok.Data;
import org.examle.aston_practice.spellbook.enums.Circle;
import org.examle.aston_practice.spellbook.enums.School;

import java.util.Set;

/**
 * Entity class representing a spell.
 */
@Data
public class Spell {
    private Long id;
    private String name;
    private String description;
    private School school; // School of magic the spell belongs to
    private Circle circle; // Circle of the spell
    private Set<Class> classes; // Classes that can cast this spell
}
