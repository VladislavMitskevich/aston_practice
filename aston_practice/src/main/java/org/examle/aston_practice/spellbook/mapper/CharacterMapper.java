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
 * Mapper class for converting between Character entity and CharacterDTO.
 */
public class CharacterMapper {

    private final SpellMapper spellMapper;

    public CharacterMapper(SpellMapper spellMapper) {
        this.spellMapper = spellMapper;
    }

    /**
     * Converts a Character entity to a CharacterDTO.
     *
     * @param character the Character entity to convert
     * @return the corresponding CharacterDTO
     */
    public CharacterDTO toDto(Character character) {
        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.setId(character.getId());
        characterDTO.setName(character.getName());
        characterDTO.setCasterClass(character.getCasterClass());
        characterDTO.setLevel(character.getLevel());

        // Проверка на null для spellsByCircle
        if (character.getSpellsByCircle() != null) {
            Map<SpellCircle, List<SpellDTO>> spellsByCircleDTO = character.getSpellsByCircle().entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().stream()
                                    .map(spellMapper::toDto)
                                    .collect(Collectors.toList())
                    ));
            characterDTO.setSpellsByCircle(spellsByCircleDTO);
        }

        return characterDTO;
    }

    /**
     * Converts a CharacterDTO to a Character entity.
     *
     * @param characterDTO the CharacterDTO to convert
     * @return the corresponding Character entity
     */
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
            character.setSpellsByCircle(spellsByCircle);
        }

        return character;
    }

    /**
     * Converts a Spell entity to a SpellDTO.
     *
     * @param spell the Spell entity to convert
     * @return the corresponding SpellDTO
     */
    public SpellDTO convertSpellToDto(Spell spell) {
        return spellMapper.toDto(spell);
    }

    /**
     * Converts a SpellDTO to a Spell entity.
     *
     * @param spellDTO the SpellDTO to convert
     * @return the corresponding Spell entity
     */
    public Spell convertDtoToSpell(SpellDTO spellDTO) {
        return spellMapper.toEntity(spellDTO);
    }
}
