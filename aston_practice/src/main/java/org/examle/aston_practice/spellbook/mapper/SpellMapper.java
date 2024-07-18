package org.examle.aston_practice.spellbook.mapper;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.SpellCircle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpellMapper {

    public SpellDTO toDto(Spell spell) {
        SpellDTO spellDTO = new SpellDTO();
        spellDTO.setId(spell.getId());
        spellDTO.setName(spell.getName());
        spellDTO.setSchool(spell.getSchool());
        spellDTO.setCircle(spell.getCircle());
        spellDTO.setDescription(spell.getDescription());
        spellDTO.setCasterClasses(spell.getCasterClasses());
        return spellDTO;
    }

    public Spell toEntity(SpellDTO spellDTO) {
        Spell spell = new Spell();
        spell.setId(spellDTO.getId());
        spell.setName(spellDTO.getName());
        spell.setSchool(spellDTO.getSchool());
        spell.setCircle(spellDTO.getCircle());
        spell.setDescription(spellDTO.getDescription());
        spell.setCasterClasses(spellDTO.getCasterClasses());
        return spell;
    }

    public CharacterDTO characterToDto(Character character) {
        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.setId(character.getId());
        characterDTO.setName(character.getName());
        characterDTO.setCasterClass(character.getCasterClass());
        characterDTO.setLevel(character.getLevel());
        Map<SpellCircle, List<SpellDTO>> spellsByCircleDTO = character.getSpellsByCircle().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(this::toDto)
                                .collect(Collectors.toList())
                ));
        characterDTO.setSpellsByCircle(spellsByCircleDTO);
        return characterDTO;
    }

    public Character dtoToCharacter(CharacterDTO characterDTO) {
        Character character = new Character();
        character.setId(characterDTO.getId());
        character.setName(characterDTO.getName());
        character.setCasterClass(characterDTO.getCasterClass());
        character.setLevel(characterDTO.getLevel());
        Map<SpellCircle, List<Spell>> spellsByCircle = characterDTO.getSpellsByCircle().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(this::toEntity)
                                .collect(Collectors.toList())
                ));
        character.setSpellsByCircle(spellsByCircle);
        return character;
    }
}
