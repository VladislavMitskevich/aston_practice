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
import org.mockito.Mockito;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CharacterMapperTest {

    private CharacterMapper characterMapper;
    private SpellMapper spellMapper;

    @BeforeEach
    public void setUp() {
        // Mock the SpellMapper to isolate CharacterMapper tests
        spellMapper = Mockito.mock(SpellMapper.class);
        characterMapper = new CharacterMapper(spellMapper);
    }

    @Test
    public void testToDto() {
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

        // Create a SpellDTO for comparison
        SpellDTO spellDTO = new SpellDTO();
        spellDTO.setId(1L);
        spellDTO.setName("Fireball");
        spellDTO.setSchool(SchoolOfMagic.EVOCATION);
        spellDTO.setCircle(SpellCircle.THIRD);
        spellDTO.setDescription("A fireball spell");
        spellDTO.setCasterClasses(new HashSet<>(Arrays.asList(CasterClass.MAGE)));

        // Mock the SpellMapper's toDto method to return the SpellDTO
        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        // Convert Character entity to CharacterDTO
        CharacterDTO characterDTO = characterMapper.toDto(character);

        // Assert that the conversion is correct
        assertEquals(character.getId(), characterDTO.getId());
        assertEquals(character.getName(), characterDTO.getName());
        assertEquals(character.getCasterClass(), characterDTO.getCasterClass());
        assertEquals(character.getLevel(), characterDTO.getLevel());
        assertEquals(1, characterDTO.getSpellsByCircle().size());
        assertEquals(1, characterDTO.getSpellsByCircle().get(SpellCircle.THIRD).size());
        assertEquals(spellDTO, characterDTO.getSpellsByCircle().get(SpellCircle.THIRD).get(0));
    }

    @Test
    public void testToEntity() {
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

        // Create a Spell entity for comparison
        Spell spell = new Spell();
        spell.setId(1L);
        spell.setName("Fireball");
        spell.setSchool(SchoolOfMagic.EVOCATION);
        spell.setCircle(SpellCircle.THIRD);
        spell.setDescription("A fireball spell");
        spell.setCasterClasses(new HashSet<>(Arrays.asList(CasterClass.MAGE)));

        // Mock the SpellMapper's toEntity method to return the Spell entity
        when(spellMapper.toEntity(spellDTO)).thenReturn(spell);

        // Convert CharacterDTO to Character entity
        Character character = characterMapper.toEntity(characterDTO);

        // Assert that the conversion is correct
        assertEquals(characterDTO.getId(), character.getId());
        assertEquals(characterDTO.getName(), character.getName());
        assertEquals(characterDTO.getCasterClass(), character.getCasterClass());
        assertEquals(characterDTO.getLevel(), character.getLevel());
        assertEquals(1, character.getSpellsByCircle().size());
        assertEquals(1, character.getSpellsByCircle().get(SpellCircle.THIRD).size());
        assertEquals(spell, character.getSpellsByCircle().get(SpellCircle.THIRD).get(0));
    }

    @ParameterizedTest
    @MethodSource("provideSpellsForConversion")
    public void testConvertSpellToDto(Spell spell, SpellDTO expectedSpellDTO) {
        // Mock the SpellMapper's toDto method to return the expected SpellDTO
        when(spellMapper.toDto(spell)).thenReturn(expectedSpellDTO);

        // Convert Spell entity to SpellDTO
        SpellDTO result = characterMapper.convertSpellToDto(spell);

        // Assert that the conversion is correct
        assertEquals(expectedSpellDTO, result);
    }

    @ParameterizedTest
    @MethodSource("provideSpellsForConversion")
    public void testConvertDtoToSpell(Spell expectedSpell, SpellDTO spellDTO) {
        // Mock the SpellMapper's toEntity method to return the expected Spell entity
        when(spellMapper.toEntity(spellDTO)).thenReturn(expectedSpell);

        // Convert SpellDTO to Spell entity
        Spell result = characterMapper.convertDtoToSpell(spellDTO);

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
