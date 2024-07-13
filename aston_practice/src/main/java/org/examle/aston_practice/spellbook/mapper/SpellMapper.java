package org.examle.aston_practice.spellbook.mapper;

import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.enums.SpellSchool;

/**
 * Mapper class for converting between Spell and SpellDTO.
 */
public class SpellMapper {

    /**
     * Converts a Spell entity to a SpellDTO.
     * @param spell the Spell entity
     * @return the corresponding SpellDTO
     */
    public static SpellDTO toDTO(Spell spell) {
        SpellDTO dto = new SpellDTO();
        dto.setId(spell.getId());
        dto.setName(spell.getName());
        dto.setDescription(spell.getDescription());
        dto.setSchool(spell.getSchool().name());
        dto.setCircle(spell.getCircle().name());
        dto.setSpellClass(spell.getSpellClass());
        return dto;
    }

    /**
     * Converts a SpellDTO to a Spell entity.
     * @param dto the SpellDTO
     * @return the corresponding Spell entity
     */
    public static Spell toEntity(SpellDTO dto) {
        Spell spell = new Spell();
        spell.setId(dto.getId());
        spell.setName(dto.getName());
        spell.setDescription(dto.getDescription());
        spell.setSchool(SpellSchool.valueOf(dto.getSchool()));
        spell.setCircle(SpellCircle.valueOf(dto.getCircle()));
        spell.setSpellClass(dto.getSpellClass());
        return spell;
    }
}
