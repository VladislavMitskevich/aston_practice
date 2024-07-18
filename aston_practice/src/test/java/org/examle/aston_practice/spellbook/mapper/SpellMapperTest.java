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
        spellMapper = new SpellMapper();
    }

    @Test
    public void testToDto() {
        Spell spell = new Spell();
        spell.setId(1L);
        spell.setName("Fireball");
        spell.setSchool(SchoolOfMagic.EVOCATION);
        spell.setCircle(SpellCircle.THIRD);
        spell.setDescription("A fireball spell");
        spell.setCasterClasses(new HashSet<>(Arrays.asList(CasterClass.MAGE)));

        SpellDTO spellDTO = spellMapper.toDto(spell);

        assertEquals(spell.getId(), spellDTO.getId());
        assertEquals(spell.getName(), spellDTO.getName());
        assertEquals(spell.getSchool(), spellDTO.getSchool());
        assertEquals(spell.getCircle(), spellDTO.getCircle());
        assertEquals(spell.getDescription(), spellDTO.getDescription());
        assertEquals(spell.getCasterClasses(), spellDTO.getCasterClasses());
    }

    @Test
    public void testToEntity() {
        SpellDTO spellDTO = new SpellDTO();
        spellDTO.setId(1L);
        spellDTO.setName("Fireball");
        spellDTO.setSchool(SchoolOfMagic.EVOCATION);
        spellDTO.setCircle(SpellCircle.THIRD);
        spellDTO.setDescription("A fireball spell");
        spellDTO.setCasterClasses(new HashSet<>(Arrays.asList(CasterClass.MAGE)));

        Spell spell = spellMapper.toEntity(spellDTO);

        assertEquals(spellDTO.getId(), spell.getId());
        assertEquals(spellDTO.getName(), spell.getName());
        assertEquals(spellDTO.getSchool(), spell.getSchool());
        assertEquals(spellDTO.getCircle(), spell.getCircle());
        assertEquals(spellDTO.getDescription(), spell.getDescription());
        assertEquals(spellDTO.getCasterClasses(), spell.getCasterClasses());
    }

    @Test
    public void testCharacterToDto() {
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

        CharacterDTO characterDTO = spellMapper.characterToDto(character);

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

        Character character = spellMapper.dtoToCharacter(characterDTO);

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
        SpellDTO result = spellMapper.toDto(spell);
        assertEquals(expectedSpellDTO, result);
    }

    @ParameterizedTest
    @MethodSource("provideSpellsForConversion")
    public void testToEntityParameterized(Spell expectedSpell, SpellDTO spellDTO) {
        Spell result = spellMapper.toEntity(spellDTO);
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
