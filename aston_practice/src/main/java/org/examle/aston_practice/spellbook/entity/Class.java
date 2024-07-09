package org.examle.aston_practice.spellbook.entity;

import lombok.Data;
import org.examle.aston_practice.spellbook.enums.Circle;

/**
 * Entity class representing a class that can cast spells.
 */
@Data
public class Class {
    private Long id;
    private String name; // Name of the class, e.g., "Cleric"
    private Circle circle; // Circle of spells this class can cast
}
