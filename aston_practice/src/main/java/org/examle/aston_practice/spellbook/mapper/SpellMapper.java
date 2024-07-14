package org.examle.aston_practice.spellbook.mapper;

import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Spell;

/**
 * Mapper for converting Spell entities to DTOs and vice versa
 */
public class SpellMapper {
    public SpellDTO toDto(Spell spell) {
        SpellDTO dto = new SpellDTO();
        dto.setId(spell.getId());
        dto.setName(spell.getName());
        dto.setSchool(spell.getSchool());
        dto.setCircle(spell.getCircle());
        dto.setCasterClass(spell.getCasterClass());
        return dto;
    }

    public Spell toEntity(SpellDTO dto) {
        Spell spell = new Spell();
        spell.setId(dto.getId());
        spell.setName(dto.getName());
        spell.setSchool(dto.getSchool());
        spell.setCircle(dto.getCircle());
        spell.setCasterClass(dto.getCasterClass());
        return spell;
    }
}
