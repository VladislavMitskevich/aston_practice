package org.examle.aston_practice.spellbook.dto;


import lombok.Data;

/**
 * Data Transfer Object for Spell.
 */
@Data
public class SpellDTO {
    private Long id;
    private String name;
    private String description;
    private String school; // School of magic
    private String circle; // Circle of the spell
    private String spellClass; // Class of the spell in format "Class(Circle)"
}
