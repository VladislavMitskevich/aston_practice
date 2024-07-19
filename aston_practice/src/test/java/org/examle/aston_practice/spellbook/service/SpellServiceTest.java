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
import org.examle.aston_practice.spellbook.enums.SchoolOfMagic;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.exception.SpellNotFoundException;
import org.examle.aston_practice.spellbook.mapper.SpellMapper;
import org.examle.aston_practice.spellbook.repository.SpellRepository;
import org.examle.aston_practice.spellbook.service.impl.SpellServiceImpl;
import org.examle.aston_practice.spellbook.validator.SpellValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SpellServiceTest {

    @Mock
    private SpellRepository spellRepository;

    @Mock
    private SpellMapper spellMapper;

    @Mock
    private SpellValidator spellValidator;

    @InjectMocks
    private SpellServiceImpl spellServiceImpl;

    private SpellDTO spellDTO;
    private Spell spell;

    @BeforeEach
    public void setUp() {
        // Initialize test data
        spellDTO = new SpellDTO();
        spellDTO.setId(1L);
        spellDTO.setName("Fireball");
        spellDTO.setSchool(SchoolOfMagic.EVOCATION);
        spellDTO.setCircle(SpellCircle.THIRD);
        spellDTO.setDescription("A bright streak flashes from your pointing finger to a point you choose within range and then blossoms with a low roar into an explosion of flame.");

        spell = new Spell();
        spell.setId(1L);
        spell.setName("Fireball");
        spell.setCircle(SpellCircle.THIRD);
    }

    @Test
    public void testCreateSpell() {
        // Mock the mapper and repository methods
        when(spellMapper.toEntity(spellDTO)).thenReturn(spell);
        doNothing().when(spellRepository).save(spell);
        doNothing().when(spellValidator).validate(spellDTO);
        when(spellRepository.findByName(spellDTO.getName())).thenReturn(Optional.empty());

        // Call the service method
        spellServiceImpl.createSpell(spellDTO);

        // Verify the repository method was called
        verify(spellRepository, times(1)).save(spell);
    }

    @Test
    public void testCreateSpellAlreadyExists() {
        // Mock the repository method to return an existing spell
        when(spellRepository.findByName(spellDTO.getName())).thenReturn(Optional.of(spell));

        // Assert that an IllegalArgumentException is thrown
        assertThrows(IllegalArgumentException.class, () -> spellServiceImpl.createSpell(spellDTO));
    }

    @Test
    public void testGetSpellById() {
        // Mock the repository and mapper methods
        when(spellRepository.findById(1L)).thenReturn(Optional.of(spell));
        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        // Call the service method
        Optional<SpellDTO> result = spellServiceImpl.getSpellById(1L);

        // Verify the result
        assertTrue(result.isPresent());
        assertEquals(spellDTO, result.get());
    }

    @Test
    public void testGetSpellByIdNotFound() {
        // Mock the repository method to return an empty optional
        when(spellRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that a SpellNotFoundException is thrown
        assertThrows(SpellNotFoundException.class, () -> spellServiceImpl.getSpellById(1L));
    }

    @Test
    public void testGetSpellByName() {
        // Mock the repository and mapper methods
        when(spellRepository.findByName("Fireball")).thenReturn(Optional.of(spell));
        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        // Call the service method
        Optional<SpellDTO> result = spellServiceImpl.getSpellByName("Fireball");

        // Verify the result
        assertTrue(result.isPresent());
        assertEquals(spellDTO, result.get());
    }

    @Test
    public void testGetSpellByNameNotFound() {
        // Mock the repository method to return an empty optional
        when(spellRepository.findByName("Fireball")).thenReturn(Optional.empty());

        // Assert that a SpellNotFoundException is thrown
        assertThrows(SpellNotFoundException.class, () -> spellServiceImpl.getSpellByName("Fireball"));
    }

    @ParameterizedTest
    @EnumSource(CasterClass.class)
    public void testGetSpellsByCasterClassAndCircle(CasterClass casterClass) {
        // Mock the repository and mapper methods
        List<Spell> spells = Arrays.asList(spell);
        when(spellRepository.findByCasterClassAndCircle(casterClass, SpellCircle.THIRD)).thenReturn(spells);
        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        // Call the service method
        List<SpellDTO> result = spellServiceImpl.getSpellsByCasterClassAndCircle(casterClass, SpellCircle.THIRD);

        // Verify the result
        assertEquals(1, result.size());
        assertEquals(spellDTO, result.get(0));
    }

    @Test
    public void testUpdateSpell() {
        // Mock the mapper and repository methods
        when(spellMapper.toEntity(spellDTO)).thenReturn(spell);
        doNothing().when(spellRepository).update(spell);
        doNothing().when(spellValidator).validate(spellDTO);

        // Call the service method
        spellServiceImpl.updateSpell(spellDTO);

        // Verify the repository method was called
        verify(spellRepository, times(1)).update(spell);
    }

    @Test
    public void testDeleteSpell() {
        // Mock the repository method
        doNothing().when(spellRepository).deleteById(1L);

        // Call the service method
        spellServiceImpl.deleteSpell(1L);

        // Verify the repository method was called
        verify(spellRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetAllSpells() {
        // Mock the repository and mapper methods
        List<Spell> spells = Arrays.asList(spell);
        when(spellRepository.findAll()).thenReturn(spells);
        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        // Call the service method
        List<SpellDTO> result = spellServiceImpl.getAllSpells();

        // Verify the result
        assertEquals(1, result.size());
        assertEquals(spellDTO, result.get(0));
    }

    @Test
    public void testGetCharactersBySpellName() {
        // Mock the repository and mapper methods
        Character character = new Character();
        CharacterDTO characterDTO = new CharacterDTO();
        List<Character> characters = Arrays.asList(character);
        when(spellRepository.findCharactersBySpellName("Fireball")).thenReturn(characters);
        when(spellMapper.characterToDto(character)).thenReturn(characterDTO);

        // Call the service method
        List<CharacterDTO> result = spellServiceImpl.getCharactersBySpellName("Fireball");

        // Verify the result
        assertEquals(1, result.size());
        assertEquals(characterDTO, result.get(0));
    }

    @Test
    public void testFindSpellByName() {
        // Mock the repository and mapper methods
        when(spellRepository.findByName("Fireball")).thenReturn(Optional.of(spell));
        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        // Call the service method
        SpellDTO result = spellServiceImpl.findSpellByName("Fireball");

        // Verify the result
        assertEquals(spellDTO, result);
    }

    @Test
    public void testFindSpellByNameNotFound() {
        // Mock the repository method to return an empty optional
        when(spellRepository.findByName("Fireball")).thenReturn(Optional.empty());

        // Assert that a SpellNotFoundException is thrown
        assertThrows(SpellNotFoundException.class, () -> spellServiceImpl.findSpellByName("Fireball"));
    }
}
