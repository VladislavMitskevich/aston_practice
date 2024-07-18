package org.examle.aston_practice.spellbook.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.mapper.CharacterMapper;
import org.examle.aston_practice.spellbook.repository.CharacterRepository;
import org.examle.aston_practice.spellbook.service.impl.CharacterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private CharacterMapper characterMapper;

    @InjectMocks
    private CharacterServiceImpl characterService;

    private CharacterDTO characterDTO;
    private SpellDTO spellDTO;
    private Spell spell;
    private Character character;

    @BeforeEach
    public void setUp() {
        characterDTO = new CharacterDTO();
        characterDTO.setId(1L);
        characterDTO.setName("Gandalf");
        characterDTO.setCasterClass(CasterClass.MAGE);
        characterDTO.setLevel(10);

        spellDTO = new SpellDTO();
        spellDTO.setId(1L);
        spellDTO.setName("Fireball");
        spellDTO.setCircle(SpellCircle.THIRD);

        spell = new Spell();
        spell.setId(1L);
        spell.setName("Fireball");
        spell.setCircle(SpellCircle.THIRD);

        character = new Character();
        character.setId(1L);
        character.setName("Gandalf");
        character.setCasterClass(CasterClass.MAGE);
        character.setLevel(10);
    }

    @Test
    public void testCreateCharacter() {
        when(characterMapper.toEntity(characterDTO)).thenReturn(character);
        doNothing().when(characterRepository).save(character);

        characterService.createCharacter(characterDTO);

        verify(characterRepository, times(1)).save(character);
    }

    @Test
    public void testGetCharacterById() {
        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));
        when(characterMapper.toDto(character)).thenReturn(characterDTO);

        Optional<CharacterDTO> result = characterService.getCharacterById(1L);

        assertTrue(result.isPresent());
        assertEquals(characterDTO, result.get());
    }

    @ParameterizedTest
    @EnumSource(CasterClass.class)
    public void testGetCharactersByCasterClass(CasterClass casterClass) {
        List<Character> characters = Arrays.asList(character);
        when(characterRepository.findByCasterClass(casterClass)).thenReturn(characters);
        when(characterMapper.toDto(character)).thenReturn(characterDTO);

        List<CharacterDTO> result = characterService.getCharactersByCasterClass(casterClass);

        assertEquals(1, result.size());
        assertEquals(characterDTO, result.get(0));
    }

    @Test
    public void testUpdateCharacter() {
        when(characterMapper.toEntity(characterDTO)).thenReturn(character);
        doNothing().when(characterRepository).update(character);

        characterService.updateCharacter(characterDTO);

        verify(characterRepository, times(1)).update(character);
    }

    @Test
    public void testDeleteCharacter() {
        doNothing().when(characterRepository).deleteById(1L);

        characterService.deleteCharacter(1L);

        verify(characterRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetAllCharacters() {
        List<Character> characters = Arrays.asList(character);
        when(characterRepository.findAll()).thenReturn(characters);
        when(characterMapper.toDto(character)).thenReturn(characterDTO);

        List<CharacterDTO> result = characterService.getAllCharacters();

        assertEquals(1, result.size());
        assertEquals(characterDTO, result.get(0));
    }

    @Test
    public void testGetCharacterByName() {
        when(characterRepository.findByName("Gandalf")).thenReturn(Optional.of(character));
        when(characterMapper.toDto(character)).thenReturn(characterDTO);

        Optional<CharacterDTO> result = characterService.getCharacterByName("Gandalf");

        assertTrue(result.isPresent());
        assertEquals(characterDTO, result.get());
    }

    @Test
    public void testGetCharactersBySpellName() {
        List<Character> characters = Arrays.asList(character);
        when(characterRepository.findCharactersBySpellName("Fireball")).thenReturn(characters);
        when(characterMapper.toDto(character)).thenReturn(characterDTO);

        List<CharacterDTO> result = characterService.getCharactersBySpellName("Fireball");

        assertEquals(1, result.size());
        assertEquals(characterDTO, result.get(0));
    }

    @Test
    public void testGetSpellsByCharacterName() {
        List<Spell> spells = Arrays.asList(spell);
        when(characterRepository.findSpellsByCharacterName("Gandalf")).thenReturn(spells);
        when(characterMapper.convertSpellToDto(spell)).thenReturn(spellDTO);

        List<SpellDTO> result = characterService.getSpellsByCharacterName("Gandalf");

        assertEquals(1, result.size());
        assertEquals(spellDTO, result.get(0));
    }

    @ParameterizedTest
    @EnumSource(value = CasterClass.class, names = {"MAGE", "CLERIC", "DRUID", "SORCERER", "WIZARD"})
    public void testGetSpellsByCasterClassAndSpellCircle(CasterClass casterClass) {
        List<Spell> spells = Arrays.asList(spell);
        when(characterRepository.findSpellsByCasterClassAndSpellCircle(casterClass, SpellCircle.THIRD)).thenReturn(spells);
        when(characterMapper.convertSpellToDto(spell)).thenReturn(spellDTO);

        List<SpellDTO> result = characterService.getSpellsByCasterClassAndSpellCircle(casterClass, SpellCircle.THIRD);

        assertEquals(1, result.size());
        assertEquals(spellDTO, result.get(0));
    }

    @Test
    public void testAddNewSpellToCharacter() {
        when(characterMapper.convertDtoToSpell(spellDTO)).thenReturn(spell);
        doNothing().when(characterRepository).addNewSpellToCharacter(1L, spell);

        characterService.addNewSpellToCharacter(1L, spellDTO);

        verify(characterRepository, times(1)).addNewSpellToCharacter(1L, spell);
    }
}

