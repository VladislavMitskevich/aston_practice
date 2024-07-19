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
 * This class provides methods to convert Character entities to CharacterDTOs
 * and vice versa, using a SpellMapper for nested Spell entities.
 */
public class CharacterMapper {

    private final SpellMapper spellMapper;

    /**
     * Constructs a CharacterMapper with a given SpellMapper.
     *
     * @param spellMapper the SpellMapper used for converting Spell entities and DTOs
     */
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
        // Create a new CharacterDTO instance
        CharacterDTO characterDTO = new CharacterDTO();

        // Set basic properties from the Character entity to the CharacterDTO
        characterDTO.setId(character.getId());
        characterDTO.setName(character.getName());
        characterDTO.setCasterClass(character.getCasterClass());
        characterDTO.setLevel(character.getLevel());

        // Check if the Character entity has spells by circle
        if (character.getSpellsByCircle() != null) {
            // Convert each Spell entity in the map to a SpellDTO
            Map<SpellCircle, List<SpellDTO>> spellsByCircleDTO = character.getSpellsByCircle().entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().stream()
                                    .map(spellMapper::toDto)
                                    .collect(Collectors.toList())
                    ));
            // Set the converted map to the CharacterDTO
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
        // Create a new Character entity instance
        Character character = new Character();

        // Set basic properties from the CharacterDTO to the Character entity
        character.setId(characterDTO.getId());
        character.setName(characterDTO.getName());
        character.setCasterClass(characterDTO.getCasterClass());
        character.setLevel(characterDTO.getLevel());

        // Check if the CharacterDTO has spells by circle
        if (characterDTO.getSpellsByCircle() != null) {
            // Convert each SpellDTO in the map to a Spell entity
            Map<SpellCircle, List<Spell>> spellsByCircle = characterDTO.getSpellsByCircle().entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().stream()
                                    .map(spellMapper::toEntity)
                                    .collect(Collectors.toList())
                    ));
            // Set the converted map to the Character entity
            character.setSpellsByCircle(spellsByCircle);
        }

        return character;
    }

    /**
     * Converts a Spell entity to a SpellDTO using the SpellMapper.
     *
     * @param spell the Spell entity to convert
     * @return the corresponding SpellDTO
     */
    public SpellDTO convertSpellToDto(Spell spell) {
        return spellMapper.toDto(spell);
    }

    /**
     * Converts a SpellDTO to a Spell entity using the SpellMapper.
     *
     * @param spellDTO the SpellDTO to convert
     * @return the corresponding Spell entity
     */
    public Spell convertDtoToSpell(SpellDTO spellDTO) {
        return spellMapper.toEntity(spellDTO);
    }
}
