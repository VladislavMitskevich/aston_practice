package org.examle.aston_practice.spellbook.dto;


import lombok.Data;
import org.examle.aston_practice.spellbook.enums.Circle;
import org.examle.aston_practice.spellbook.enums.School;

import java.util.Set;

/**
 * Data Transfer Object for Spell.
 */
@Data
public class SpellDTO {
    private Long id;
    private String name;
    private String description;
    private School school; // School of magic the spell belongs to
    private Circle circle; // Circle of the spell
    private Set<String> classCircles; // e.g., "Cleric(2)"
}

