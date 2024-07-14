package org.examle.aston_practice.spellbook.mapper;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.entity.Character;

/**
 * Mapper for converting Character entities to DTOs and vice versa
 */
public class CharacterMapper {
    public CharacterDTO toDto(Character character) {
        CharacterDTO dto = new CharacterDTO();
        dto.setId(character.getId());
        dto.setName(character.getName());
        dto.setSpellLimits(character.getSpellLimits());
        return dto;
    }

    public Character toEntity(CharacterDTO dto) {
        Character character = new Character();
        character.setId(dto.getId());
        character.setName(dto.getName());
        character.setSpellLimits(dto.getSpellLimits());
        return character;
    }
}
