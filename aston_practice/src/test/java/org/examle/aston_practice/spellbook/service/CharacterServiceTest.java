package org.examle.aston_practice.spellbook.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        characterDTO.setName("Gandalf");
        characterDTO.setCasterClass(CasterClass.MAGE);
        characterDTO.setLevel(10);

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

    @Test
    public void testAddSpellToCharacter() {
        doNothing().when(characterRepository).addSpellToCharacter(1L, 1L);

        characterService.addSpellToCharacter(1L, 1L);

        verify(characterRepository, times(1)).addSpellToCharacter(1L, 1L);
    }

    @Test
    public void testGetCharactersByCasterClass() {
        List<Character> characters = Arrays.asList(character);
        when(characterRepository.findByCasterClass(CasterClass.MAGE)).thenReturn(characters);
        when(characterMapper.toDto(character)).thenReturn(characterDTO);

        List<CharacterDTO> result = characterService.getCharactersByCasterClass(CasterClass.MAGE);

        assertEquals(1, result.size());
        assertEquals(characterDTO, result.get(0));
    }
}
