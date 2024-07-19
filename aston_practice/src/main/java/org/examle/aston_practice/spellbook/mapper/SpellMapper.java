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
 * Mapper class for converting between Spell entity and SpellDTO.
 * This class also provides methods to convert Character entities and DTOs,
 * leveraging the conversion of Spell entities and DTOs.
 */
public class SpellMapper {

    /**
     * Converts a Spell entity to a SpellDTO.
     *
     * @param spell the Spell entity to convert
     * @return the corresponding SpellDTO
     */
    public SpellDTO toDto(Spell spell) {
        // Create a new SpellDTO instance
        SpellDTO spellDTO = new SpellDTO();

        // Set properties from the Spell entity to the SpellDTO
        spellDTO.setId(spell.getId());
        spellDTO.setName(spell.getName());
        spellDTO.setSchool(spell.getSchool());
        spellDTO.setCircle(spell.getCircle());
        spellDTO.setDescription(spell.getDescription());
        spellDTO.setCasterClasses(spell.getCasterClasses());

        return spellDTO;
    }

    /**
     * Converts a SpellDTO to a Spell entity.
     *
     * @param spellDTO the SpellDTO to convert
     * @return the corresponding Spell entity
     */
    public Spell toEntity(SpellDTO spellDTO) {
        // Create a new Spell entity instance
        Spell spell = new Spell();

        // Set properties from the SpellDTO to the Spell entity
        spell.setId(spellDTO.getId());
        spell.setName(spellDTO.getName());
        spell.setSchool(spellDTO.getSchool());
        spell.setCircle(spellDTO.getCircle());
        spell.setDescription(spellDTO.getDescription());
        spell.setCasterClasses(spellDTO.getCasterClasses());

        return spell;
    }

    /**
     * Converts a Character entity to a CharacterDTO.
     *
     * @param character the Character entity to convert
     * @return the corresponding CharacterDTO
     */
    public CharacterDTO characterToDto(Character character) {
        // Create a new CharacterDTO instance
        CharacterDTO characterDTO = new CharacterDTO();

        // Set basic properties from the Character entity to the CharacterDTO
        characterDTO.setId(character.getId());
        characterDTO.setName(character.getName());
        characterDTO.setCasterClass(character.getCasterClass());
        characterDTO.setLevel(character.getLevel());

        // Convert the map of spells by circle from Spell entities to SpellDTOs
        Map<SpellCircle, List<SpellDTO>> spellsByCircleDTO = character.getSpellsByCircle().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(this::toDto)
                                .collect(Collectors.toList())
                ));

        // Set the converted map to the CharacterDTO
        characterDTO.setSpellsByCircle(spellsByCircleDTO);

        return characterDTO;
    }

    /**
     * Converts a CharacterDTO to a Character entity.
     *
     * @param characterDTO the CharacterDTO to convert
     * @return the corresponding Character entity
     */
    public Character dtoToCharacter(CharacterDTO characterDTO) {
        // Create a new Character entity instance
        Character character = new Character();

        // Set basic properties from the CharacterDTO to the Character entity
        character.setId(characterDTO.getId());
        character.setName(characterDTO.getName());
        character.setCasterClass(characterDTO.getCasterClass());
        character.setLevel(characterDTO.getLevel());

        // Convert the map of spells by circle from SpellDTOs to Spell entities
        Map<SpellCircle, List<Spell>> spellsByCircle = characterDTO.getSpellsByCircle().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(this::toEntity)
                                .collect(Collectors.toList())
                ));

        // Set the converted map to the Character entity
        character.setSpellsByCircle(spellsByCircle);

        return character;
    }
}
