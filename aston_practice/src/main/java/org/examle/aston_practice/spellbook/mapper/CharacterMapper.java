package org.examle.aston_practice.spellbook.mapper;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.entity.Character;

/**
 * Mapper for converting Character entities to DTOs and vice versa.
 * This class provides methods to convert between Character entities and CharacterDTO objects.
 */
public class CharacterMapper {
    /**
     * Converts a Character entity to a CharacterDTO.
     * @param character the Character entity to convert
     * @return the corresponding CharacterDTO
     */
    public CharacterDTO toDto(Character character) {
        CharacterDTO dto = new CharacterDTO();
        dto.setId(character.getId());
        dto.setName(character.getName());
        dto.setSpellLimits(character.getSpellLimits());
        return dto;
    }

    /**
     * Converts a CharacterDTO to a Character entity.
     * @param dto the CharacterDTO to convert
     * @return the corresponding Character entity
     */
    public Character toEntity(CharacterDTO dto) {
        Character character = new Character();
        character.setId(dto.getId());
        character.setName(dto.getName());
        character.setSpellLimits(dto.getSpellLimits());
        return character;
    }
}
