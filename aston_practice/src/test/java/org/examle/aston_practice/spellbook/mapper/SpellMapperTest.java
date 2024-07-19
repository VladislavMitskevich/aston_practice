package org.examle.aston_practice.spellbook.mapper;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.enums.SchoolOfMagic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpellMapperTest {

    private SpellMapper spellMapper;

    @BeforeEach
    public void setUp() {
        // Initialize the SpellMapper instance before each test
        spellMapper = new SpellMapper();
    }

    @Test
    public void testToDto() {
        // Create a Spell entity for testing
        Spell spell = new Spell();
        spell.setId(1L);
        spell.setName("Fireball");
        spell.setSchool(SchoolOfMagic.EVOCATION);
        spell.setCircle(SpellCircle.THIRD);
        spell.setDescription("A fireball spell");
        spell.setCasterClasses(new HashSet<>(Arrays.asList(CasterClass.MAGE)));

        // Convert the Spell entity to a SpellDTO using the mapper
        SpellDTO spellDTO = spellMapper.toDto(spell);

        // Assert that the conversion is correct
        assertEquals(spell.getId(), spellDTO.getId());
        assertEquals(spell.getName(), spellDTO.getName());
        assertEquals(spell.getSchool(), spellDTO.getSchool());
        assertEquals(spell.getCircle(), spellDTO.getCircle());
        assertEquals(spell.getDescription(), spellDTO.getDescription());
        assertEquals(spell.getCasterClasses(), spellDTO.getCasterClasses());
    }

    @Test
    public void testToEntity() {
        // Create a SpellDTO for testing
        SpellDTO spellDTO = new SpellDTO();
        spellDTO.setId(1L);
        spellDTO.setName("Fireball");
        spellDTO.setSchool(SchoolOfMagic.EVOCATION);
        spellDTO.setCircle(SpellCircle.THIRD);
        spellDTO.setDescription("A fireball spell");
        spellDTO.setCasterClasses(new HashSet<>(Arrays.asList(CasterClass.MAGE)));

        // Convert the SpellDTO to a Spell entity using the mapper
        Spell spell = spellMapper.toEntity(spellDTO);

        // Assert that the conversion is correct
        assertEquals(spellDTO.getId(), spell.getId());
        assertEquals(spellDTO.getName(), spell.getName());
        assertEquals(spellDTO.getSchool(), spell.getSchool());
        assertEquals(spellDTO.getCircle(), spell.getCircle());
        assertEquals(spellDTO.getDescription(), spell.getDescription());
        assertEquals(spellDTO.getCasterClasses(), spell.getCasterClasses());
    }

    @Test
    public void testCharacterToDto() {
        // Create a Character entity for testing
        Character character = new Character();
        character.setId(1L);
        character.setName("Test Character");
        character.setCasterClass(CasterClass.MAGE);
        character.setLevel(10);

        // Create a Spell entity associated with the Character
        Spell spell = new Spell();
        spell.setId(1L);
        spell.setName("Fireball");
        spell.setSchool(SchoolOfMagic.EVOCATION);
        spell.setCircle(SpellCircle.THIRD);
        spell.setDescription("A fireball spell");
        spell.setCasterClasses(new HashSet<>(Arrays.asList(CasterClass.MAGE)));

        // Map spells to their respective circles
        Map<SpellCircle, List<Spell>> spellsByCircle = new HashMap<>();
        spellsByCircle.put(SpellCircle.THIRD, Arrays.asList(spell));
        character.setSpellsByCircle(spellsByCircle);

        // Convert the Character entity to a CharacterDTO using the mapper
        CharacterDTO characterDTO = spellMapper.characterToDto(character);

        // Assert that the conversion is correct
        assertEquals(character.getId(), characterDTO.getId());
        assertEquals(character.getName(), characterDTO.getName());
        assertEquals(character.getCasterClass(), characterDTO.getCasterClass());
        assertEquals(character.getLevel(), characterDTO.getLevel());
        assertEquals(1, characterDTO.getSpellsByCircle().size());
        assertEquals(1, characterDTO.getSpellsByCircle().get(SpellCircle.THIRD).size());
        assertEquals(spellMapper.toDto(spell), characterDTO.getSpellsByCircle().get(SpellCircle.THIRD).get(0));
    }

    @Test
    public void testDtoToCharacter() {
        // Create a CharacterDTO for testing
        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.setId(1L);
        characterDTO.setName("Test Character");
        characterDTO.setCasterClass(CasterClass.MAGE);
        characterDTO.setLevel(10);

        // Create a SpellDTO associated with the CharacterDTO
        SpellDTO spellDTO = new SpellDTO();
        spellDTO.setId(1L);
        spellDTO.setName("Fireball");
        spellDTO.setSchool(SchoolOfMagic.EVOCATION);
        spellDTO.setCircle(SpellCircle.THIRD);
        spellDTO.setDescription("A fireball spell");
        spellDTO.setCasterClasses(new HashSet<>(Arrays.asList(CasterClass.MAGE)));

        // Map spells to their respective circles
        Map<SpellCircle, List<SpellDTO>> spellsByCircleDTO = new HashMap<>();
        spellsByCircleDTO.put(SpellCircle.THIRD, Arrays.asList(spellDTO));
        characterDTO.setSpellsByCircle(spellsByCircleDTO);

        // Convert the CharacterDTO to a Character entity using the mapper
        Character character = spellMapper.dtoToCharacter(characterDTO);

        // Assert that the conversion is correct
        assertEquals(characterDTO.getId(), character.getId());
        assertEquals(characterDTO.getName(), character.getName());
        assertEquals(characterDTO.getCasterClass(), character.getCasterClass());
        assertEquals(characterDTO.getLevel(), character.getLevel());
        assertEquals(1, character.getSpellsByCircle().size());
        assertEquals(1, character.getSpellsByCircle().get(SpellCircle.THIRD).size());
        assertEquals(spellMapper.toEntity(spellDTO), character.getSpellsByCircle().get(SpellCircle.THIRD).get(0));
    }

    @ParameterizedTest
    @MethodSource("provideSpellsForConversion")
    public void testToDtoParameterized(Spell spell, SpellDTO expectedSpellDTO) {
        // Convert the Spell entity to a SpellDTO using the mapper
        SpellDTO result = spellMapper.toDto(spell);
        // Assert that the conversion is correct
        assertEquals(expectedSpellDTO, result);
    }

    @ParameterizedTest
    @MethodSource("provideSpellsForConversion")
    public void testToEntityParameterized(Spell expectedSpell, SpellDTO spellDTO) {
        // Convert the SpellDTO to a Spell entity using the mapper
        Spell result = spellMapper.toEntity(spellDTO);
        // Assert that the conversion is correct
        assertEquals(expectedSpell, result);
    }

    // Provide test data for parameterized tests
    private static Stream<Object[]> provideSpellsForConversion() {
        Spell spell = new Spell();
        spell.setId(1L);
        spell.setName("Fireball");
        spell.setSchool(SchoolOfMagic.EVOCATION);
        spell.setCircle(SpellCircle.THIRD);
        spell.setDescription("A fireball spell");
        spell.setCasterClasses(new HashSet<>(Arrays.asList(CasterClass.MAGE)));

        SpellDTO spellDTO = new SpellDTO();
        spellDTO.setId(1L);
        spellDTO.setName("Fireball");
        spellDTO.setSchool(SchoolOfMagic.EVOCATION);
        spellDTO.setCircle(SpellCircle.THIRD);
        spellDTO.setDescription("A fireball spell");
        spellDTO.setCasterClasses(new HashSet<>(Arrays.asList(CasterClass.MAGE)));

        return Stream.of(new Object[][]{
                {spell, spellDTO}
        });
    }
}
