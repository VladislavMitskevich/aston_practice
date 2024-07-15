package org.examle.aston_practice.spellbook.mapper;

import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Spell;

/**
 * Mapper for converting Spell entities to DTOs and vice versa.
 * This class provides methods to convert between Spell entities and SpellDTO objects.
 */
public class SpellMapper {
    /**
     * Converts a Spell entity to a SpellDTO.
     * @param spell the Spell entity to convert
     * @return the corresponding SpellDTO
     */
    public SpellDTO toDto(Spell spell) {
        SpellDTO dto = new SpellDTO();
        dto.setId(spell.getId());
        dto.setName(spell.getName());
        dto.setSchool(spell.getSchool());
        dto.setCircle(spell.getCircle());
        dto.setCasterClasses(spell.getCasterClasses());
        dto.setDescription(spell.getDescription());
        return dto;
    }

    /**
     * Converts a SpellDTO to a Spell entity.
     * @param dto the SpellDTO to convert
     * @return the corresponding Spell entity
     */
    public Spell toEntity(SpellDTO dto) {
        Spell spell = new Spell();
        spell.setId(dto.getId());
        spell.setName(dto.getName());
        spell.setSchool(dto.getSchool());
        spell.setCircle(dto.getCircle());
        spell.setCasterClasses(dto.getCasterClasses());
        spell.setDescription(dto.getDescription());
        return spell;
    }
}