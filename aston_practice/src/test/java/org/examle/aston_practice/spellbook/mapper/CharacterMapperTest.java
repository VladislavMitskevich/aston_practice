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
        spellMapper = Mockito.mock(SpellMapper.class);
        characterMapper = new CharacterMapper(spellMapper);
    }

    @Test
    public void testToDto() {
        Character character = new Character();
        character.setId(1L);
        character.setName("Test Character");
        character.setCasterClass(CasterClass.MAGE);
        character.setLevel(10);

        Spell spell = new Spell();
        spell.setId(1L);
        spell.setName("Fireball");
        spell.setSchool(SchoolOfMagic.EVOCATION);
        spell.setCircle(SpellCircle.THIRD);
        spell.setDescription("A fireball spell");
        spell.setCasterClasses(new HashSet<>(Arrays.asList(CasterClass.MAGE)));

        Map<SpellCircle, List<Spell>> spellsByCircle = new HashMap<>();
        spellsByCircle.put(SpellCircle.THIRD, Arrays.asList(spell));
        character.setSpellsByCircle(spellsByCircle);

        SpellDTO spellDTO = new SpellDTO();
        spellDTO.setId(1L);
        spellDTO.setName("Fireball");
        spellDTO.setSchool(SchoolOfMagic.EVOCATION);
        spellDTO.setCircle(SpellCircle.THIRD);
        spellDTO.setDescription("A fireball spell");
        spellDTO.setCasterClasses(new HashSet<>(Arrays.asList(CasterClass.MAGE)));

        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        CharacterDTO characterDTO = characterMapper.toDto(character);

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
        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.setId(1L);
        characterDTO.setName("Test Character");
        characterDTO.setCasterClass(CasterClass.MAGE);
        characterDTO.setLevel(10);

        SpellDTO spellDTO = new SpellDTO();
        spellDTO.setId(1L);
        spellDTO.setName("Fireball");
        spellDTO.setSchool(SchoolOfMagic.EVOCATION);
        spellDTO.setCircle(SpellCircle.THIRD);
        spellDTO.setDescription("A fireball spell");
        spellDTO.setCasterClasses(new HashSet<>(Arrays.asList(CasterClass.MAGE)));

        Map<SpellCircle, List<SpellDTO>> spellsByCircleDTO = new HashMap<>();
        spellsByCircleDTO.put(SpellCircle.THIRD, Arrays.asList(spellDTO));
        characterDTO.setSpellsByCircle(spellsByCircleDTO);

        Spell spell = new Spell();
        spell.setId(1L);
        spell.setName("Fireball");
        spell.setSchool(SchoolOfMagic.EVOCATION);
        spell.setCircle(SpellCircle.THIRD);
        spell.setDescription("A fireball spell");
        spell.setCasterClasses(new HashSet<>(Arrays.asList(CasterClass.MAGE)));

        when(spellMapper.toEntity(spellDTO)).thenReturn(spell);

        Character character = characterMapper.toEntity(characterDTO);

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
        when(spellMapper.toDto(spell)).thenReturn(expectedSpellDTO);

        SpellDTO result = characterMapper.convertSpellToDto(spell);

        assertEquals(expectedSpellDTO, result);
    }

    @ParameterizedTest
    @MethodSource("provideSpellsForConversion")
    public void testConvertDtoToSpell(Spell expectedSpell, SpellDTO spellDTO) {
        when(spellMapper.toEntity(spellDTO)).thenReturn(expectedSpell);

        Spell result = characterMapper.convertDtoToSpell(spellDTO);

        assertEquals(expectedSpell, result);
    }

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
