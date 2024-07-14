package org.examle.aston_practice.spellbook.service;

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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        spellDTO.setName("Test Spell");
        spellDTO.setSchool(SchoolOfMagic.ABJURATION);
        spellDTO.setCircle(SpellCircle.FIRST);
        spellDTO.setCasterClass(CasterClass.WIZARD);

        spell = new Spell();
        spell.setId(1L);
        spell.setName("Test Spell");
        spell.setSchool(SchoolOfMagic.ABJURATION);
        spell.setCircle(SpellCircle.FIRST);
        spell.setCasterClass(CasterClass.WIZARD);
    }

    @Test
    public void testFindAll() {
        List<Spell> spells = Arrays.asList(spell);
        when(spellRepository.findAll()).thenReturn(spells);
        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        List<SpellDTO> result = spellService.findAll();

        assertEquals(1, result.size());
        assertEquals(spellDTO, result.get(0));
        verify(spellRepository, times(1)).findAll();
        verify(spellMapper, times(1)).toDto(spell);
    }

    @Test
    public void testFindById() {
        when(spellRepository.findById(1L)).thenReturn(Optional.of(spell));
        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        SpellDTO result = spellService.findById(1L);

        assertEquals(spellDTO, result);
        verify(spellRepository, times(1)).findById(1L);
        verify(spellMapper, times(1)).toDto(spell);
    }

    @Test
    public void testSave() {
        when(spellMapper.toEntity(spellDTO)).thenReturn(spell);

        spellService.save(spellDTO);

        verify(spellMapper, times(1)).toEntity(spellDTO);
        verify(spellRepository, times(1)).save(spell);
    }

    @Test
    public void testUpdate() {
        when(spellMapper.toEntity(spellDTO)).thenReturn(spell);

        spellService.update(spellDTO);

        verify(spellMapper, times(1)).toEntity(spellDTO);
        verify(spellRepository, times(1)).update(spell);
    }

    @Test
    public void testDeleteById() {
        spellService.deleteById(1L);

        verify(spellRepository, times(1)).delete(1L);
    }

    @Test
    public void testFindByClassAndCircle() {
        List<Spell> spells = Arrays.asList(spell);
        when(spellRepository.findByClassAndCircle("WIZARD", "FIRST")).thenReturn(spells);
        when(spellMapper.toDto(spell)).thenReturn(spellDTO);

        List<SpellDTO> result = spellService.findByClassAndCircle("WIZARD", "FIRST");

        assertEquals(1, result.size());
        assertEquals(spellDTO, result.get(0));
        verify(spellRepository, times(1)).findByClassAndCircle("WIZARD", "FIRST");
        verify(spellMapper, times(1)).toDto(spell);
    }
}
