package org.examle.aston_practice.spellbook.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.examle.aston_practice.spellbook.dto.SpellDTO;
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
        spellDTO.setDescription("A bright streak flashes from your pointing finger...");

        spell = new Spell();
        spell.setId(1L);
        spell.setName("Fireball");
        spell.setSchool(SchoolOfMagic.EVOCATION);
        spell.setCircle(SpellCircle.THIRD);
        spell.setDescription("A bright streak flashes from your pointing finger...");
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

    @Test
    public void testGetSpellsByCasterClassAndCircle() {
        List<Spell> spells = Arrays.asList(spell);
        when(spellRepository.findByCasterClassAndCircle(CasterClass.MAGE, SpellCircle.THIRD)).thenReturn(spells);
        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        List<SpellDTO> result = spellService.getSpellsByCasterClassAndCircle(CasterClass.MAGE, SpellCircle.THIRD);

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
    public void testGetSpellById_NotFound() {
        when(spellRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<SpellDTO> result = spellService.getSpellById(2L);

        assertFalse(result.isPresent());
    }

    @Test
    public void testGetSpellsByCasterClassAndCircle_NotFound() {
        when(spellRepository.findByCasterClassAndCircle(CasterClass.MAGE, SpellCircle.FOURTH)).thenReturn(Arrays.asList());

        List<SpellDTO> result = spellService.getSpellsByCasterClassAndCircle(CasterClass.MAGE, SpellCircle.FOURTH);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllSpells_EmptyList() {
        when(spellRepository.findAll()).thenReturn(Arrays.asList());

        List<SpellDTO> result = spellService.getAllSpells();

        assertTrue(result.isEmpty());
    }
}
