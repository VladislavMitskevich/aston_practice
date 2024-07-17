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
import org.examle.aston_practice.spellbook.mapper.SpellMapper;
import org.examle.aston_practice.spellbook.repository.SpellRepository;
import org.examle.aston_practice.spellbook.service.impl.SpellServiceImpl;
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

    @InjectMocks
    private SpellServiceImpl spellService;

    private SpellDTO spellDTO;
    private Spell spell;

    @BeforeEach
    public void setUp() {
        spellDTO = new SpellDTO();
        spellDTO.setId(1L);
        spellDTO.setName("Fireball");
        spellDTO.setSchool(SchoolOfMagic.EVOCATION);
        spellDTO.setCircle(SpellCircle.THIRD);
        spellDTO.setDescription("A bright streak flashes from your pointing finger to a point you choose within range and then blossoms with a low roar into an explosion of flame."); // Установите значение для description

        spell = new Spell();
        spell.setId(1L);
        spell.setName("Fireball");
        spell.setCircle(SpellCircle.THIRD);
    }

    @Test
    public void testCreateSpell() {
        when(spellMapper.toEntity(spellDTO)).thenReturn(spell);
        doNothing().when(spellRepository).save(spell);

        spellService.createSpell(spellDTO);

        verify(spellRepository, times(1)).save(spell);
    }

    @Test
    public void testGetSpellById() {
        when(spellRepository.findById(1L)).thenReturn(Optional.of(spell));
        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        Optional<SpellDTO> result = spellService.getSpellById(1L);

        assertTrue(result.isPresent());
        assertEquals(spellDTO, result.get());
    }

    @ParameterizedTest
    @EnumSource(CasterClass.class)
    public void testGetSpellsByCasterClassAndCircle(CasterClass casterClass) {
        List<Spell> spells = Arrays.asList(spell);
        when(spellRepository.findByCasterClassAndCircle(casterClass, SpellCircle.THIRD)).thenReturn(spells);
        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        List<SpellDTO> result = spellService.getSpellsByCasterClassAndCircle(casterClass, SpellCircle.THIRD);

        assertEquals(1, result.size());
        assertEquals(spellDTO, result.get(0));
    }

    @Test
    public void testUpdateSpell() {
        when(spellMapper.toEntity(spellDTO)).thenReturn(spell);
        doNothing().when(spellRepository).update(spell);

        spellService.updateSpell(spellDTO);

        verify(spellRepository, times(1)).update(spell);
    }

    @Test
    public void testDeleteSpell() {
        doNothing().when(spellRepository).deleteById(1L);

        spellService.deleteSpell(1L);

        verify(spellRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetAllSpells() {
        List<Spell> spells = Arrays.asList(spell);
        when(spellRepository.findAll()).thenReturn(spells);
        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        List<SpellDTO> result = spellService.getAllSpells();

        assertEquals(1, result.size());
        assertEquals(spellDTO, result.get(0));
    }

    @Test
    public void testGetSpellByName() {
        when(spellRepository.findByName("Fireball")).thenReturn(Optional.of(spell));
        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        Optional<SpellDTO> result = spellService.getSpellByName("Fireball");

        assertTrue(result.isPresent());
        assertEquals(spellDTO, result.get());
    }

    @Test
    public void testGetCharactersBySpellName() {
        Character character = new Character();
        CharacterDTO characterDTO = new CharacterDTO();
        List<Character> characters = Arrays.asList(character);
        when(spellRepository.findCharactersBySpellName("Fireball")).thenReturn(characters);
        when(spellMapper.characterToDto(character)).thenReturn(characterDTO);

        List<CharacterDTO> result = spellService.getCharactersBySpellName("Fireball");

        assertEquals(1, result.size());
        assertEquals(characterDTO, result.get(0));
    }
}