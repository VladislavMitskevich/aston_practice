package org.examle.aston_practice.spellbook.mapper;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.entity.Spell;

/**
 * Mapper for converting Spell entities to DTOs and vice versa.
 */
public class SpellMapper {

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

    public CharacterDTO characterToDto(Character character) {
        CharacterDTO dto = new CharacterDTO();
        dto.setId(character.getId());
        dto.setName(character.getName());
        dto.setCasterClass(character.getCasterClass());
        dto.setLevel(character.getLevel());
        return dto;
    }
}
