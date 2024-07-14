package org.examle.aston_practice.spellbook.service;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.mapper.CharacterMapper;
import org.examle.aston_practice.spellbook.repository.CharacterRepository;
import org.examle.aston_practice.spellbook.service.impl.CharacterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private CharacterMapper characterMapper;

    @InjectMocks
    private CharacterServiceImpl characterService;

    private CharacterDTO characterDTO;
    private Character character;

    @BeforeEach
    public void setUp() {
        characterDTO = new CharacterDTO();
        characterDTO.setId(1L);
        characterDTO.setName("Test Character");
        Map<CasterClass, Integer> spellLimits = new HashMap<>();
        spellLimits.put(CasterClass.WIZARD, 3);
        characterDTO.setSpellLimits(spellLimits);

        character = new Character();
        character.setId(1L);
        character.setName("Test Character");
        character.setSpellLimits(spellLimits);
    }

    @Test
    public void testFindAll() {
        List<Character> characters = Arrays.asList(character);
        when(characterRepository.findAll()).thenReturn(characters);
        when(characterMapper.toDto(character)).thenReturn(characterDTO);

        List<CharacterDTO> result = characterService.findAll();

        assertEquals(1, result.size());
        assertEquals(characterDTO, result.get(0));
        verify(characterRepository, times(1)).findAll();
        verify(characterMapper, times(1)).toDto(character);
    }

    @Test
    public void testFindById() {
        when(characterRepository.findById(1L)).thenReturn(character);
        when(characterMapper.toDto(character)).thenReturn(characterDTO);

        CharacterDTO result = characterService.findById(1L);

        assertEquals(characterDTO, result);
        verify(characterRepository, times(1)).findById(1L);
        verify(characterMapper, times(1)).toDto(character);
    }

    @Test
    public void testSave() {
        when(characterMapper.toEntity(characterDTO)).thenReturn(character);

        characterService.save(characterDTO);

        verify(characterMapper, times(1)).toEntity(characterDTO);
        verify(characterRepository, times(1)).save(character);
    }

    @Test
    public void testUpdate() {
        when(characterMapper.toEntity(characterDTO)).thenReturn(character);

        characterService.update(characterDTO);

        verify(characterMapper, times(1)).toEntity(characterDTO);
        verify(characterRepository, times(1)).update(character);
    }

    @Test
    public void testDeleteById() {
        characterService.deleteById(1L);

        verify(characterRepository, times(1)).deleteById(1L);
    }
}
