package org.examle.aston_practice.spellbook.mapper;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.SpellCircle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CharacterMapper {

    private final SpellMapper spellMapper;

    public CharacterMapper(SpellMapper spellMapper) {
        this.spellMapper = spellMapper;
    }

    public CharacterDTO toDto(Character character) {
        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.setId(character.getId());
        characterDTO.setName(character.getName());
        characterDTO.setCasterClass(character.getCasterClass());
        characterDTO.setLevel(character.getLevel());
        Map<SpellCircle, List<SpellDTO>> spellsByCircleDTO = character.getSpellsByCircle().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(spellMapper::toDto)
                                .collect(Collectors.toList())
                ));
        characterDTO.setSpells(spellsByCircleDTO);
        return characterDTO;
    }

    public Character toEntity(CharacterDTO characterDTO) {
        Character character = new Character();
        character.setId(characterDTO.getId());
        character.setName(characterDTO.getName());
        character.setCasterClass(characterDTO.getCasterClass());
        character.setLevel(characterDTO.getLevel());

        // Добавляем проверку на null
        if (characterDTO.getSpellsByCircle() != null) {
            Map<SpellCircle, List<Spell>> spellsByCircle = characterDTO.getSpellsByCircle().entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().stream()
                                    .map(spellMapper::toEntity)
                                    .collect(Collectors.toList())
                    ));
            character.setSpells(spellsByCircle);
        }

        return character;
    }

    public SpellDTO spellToDto(Spell spell) {
        return spellMapper.toDto(spell);
    }

    public Spell dtoToSpell(SpellDTO spellDTO) {
        return spellMapper.toEntity(spellDTO);
    }
}
