package org.examle.aston_practice.spellbook.mapper;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.SpellCircle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mapper for converting Character entities to DTOs and vice versa.
 */
public class CharacterMapper {

    private final SpellMapper spellMapper;

    public CharacterMapper(SpellMapper spellMapper) {
        this.spellMapper = spellMapper;
    }

    public CharacterDTO toDto(Character character) {
        CharacterDTO dto = new CharacterDTO();
        dto.setId(character.getId());
        dto.setName(character.getName());
        dto.setCasterClass(character.getCasterClass());
        dto.setLevel(character.getLevel());
        dto.setSpellsByCircle(mapSpellsByCircleToDto(character.getSpellsByCircle()));
        return dto;
    }

    public Character toEntity(CharacterDTO dto) {
        Character character = new Character();
        character.setId(dto.getId());
        character.setName(dto.getName());
        character.setCasterClass(dto.getCasterClass());
        character.setLevel(dto.getLevel());
        character.setSpellsByCircle(mapSpellsByCircleToEntity(dto.getSpellsByCircle()));
        return character;
    }

    private Map<SpellCircle, List<SpellDTO>> mapSpellsByCircleToDto(Map<SpellCircle, List<Spell>> spellsByCircleEntity) {
        return spellsByCircleEntity.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(spellMapper::toDto)
                                .collect(Collectors.toList())
                ));
    }

    private Map<SpellCircle, List<Spell>> mapSpellsByCircleToEntity(Map<SpellCircle, List<SpellDTO>> spellsByCircleDto) {
        return spellsByCircleDto.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(spellMapper::toEntity)
                                .collect(Collectors.toList())
                ));
    }

    public SpellDTO spellToDto(Spell spell) {
        return spellMapper.toDto(spell);
    }

    public Spell dtoToSpell(SpellDTO spellDTO) {
        return spellMapper.toEntity(spellDTO);
    }
}
