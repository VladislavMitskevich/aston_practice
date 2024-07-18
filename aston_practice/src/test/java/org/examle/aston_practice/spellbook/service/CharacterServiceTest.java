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
import org.examle.aston_practice.spellbook.repository.SpellRepository;
import org.examle.aston_practice.spellbook.service.impl.CharacterServiceImpl;
import org.examle.aston_practice.spellbook.validator.CharacterValidator;
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
    private SpellRepository spellRepository;

    @Mock
    private CharacterMapper characterMapper;

    @Mock
    private CharacterValidator characterValidator;

    @InjectMocks
    private CharacterServiceImpl characterServiceImpl;

    private CharacterDTO characterDTO;
    private SpellDTO spellDTO;
    private Spell spell;
    private Character character;

    @BeforeEach
    public void setUp() {
        // Initialize test data
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
        // Mock the mapper and repository methods
        when(characterMapper.toEntity(characterDTO)).thenReturn(character);
        doNothing().when(characterRepository).save(character);
        doNothing().when(characterValidator).validate(characterDTO);
        when(characterRepository.findByName(characterDTO.getName())).thenReturn(Optional.empty());

        // Call the service method
        characterServiceImpl.createCharacter(characterDTO);

        // Verify the repository method was called
        verify(characterRepository, times(1)).save(character);
    }

    @Test
    public void testGetCharacterById() {
        // Mock the repository and mapper methods
        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));
        when(characterMapper.toDto(character)).thenReturn(characterDTO);

        // Call the service method
        Optional<CharacterDTO> result = characterServiceImpl.getCharacterById(1L);

        // Verify the result
        assertTrue(result.isPresent());
        assertEquals(characterDTO, result.get());
    }

    @Test
    public void testGetCharacterByName() {
        // Mock the repository and mapper methods
        when(characterRepository.findByName("Gandalf")).thenReturn(Optional.of(character));
        when(characterMapper.toDto(character)).thenReturn(characterDTO);

        // Call the service method
        CharacterDTO result = characterServiceImpl.getCharacterByName("Gandalf");

        // Verify the result
        assertEquals(characterDTO, result);
    }

    @ParameterizedTest
    @EnumSource(CasterClass.class)
    public void testGetCharactersByCasterClass(CasterClass casterClass) {
        // Mock the repository and mapper methods
        List<Character> characters = Arrays.asList(character);
        when(characterRepository.findByCasterClass(casterClass)).thenReturn(characters);
        when(characterMapper.toDto(character)).thenReturn(characterDTO);

        // Call the service method
        List<CharacterDTO> result = characterServiceImpl.getCharactersByCasterClass(casterClass);

        // Verify the result
        assertEquals(1, result.size());
        assertEquals(characterDTO, result.get(0));
    }

    @Test
    public void testUpdateCharacter() {
        // Mock the mapper and repository methods
        when(characterMapper.toEntity(characterDTO)).thenReturn(character);
        doNothing().when(characterRepository).update(character);
        doNothing().when(characterValidator).validate(characterDTO);

        // Call the service method
        characterServiceImpl.updateCharacter(characterDTO);

        // Verify the repository method was called
        verify(characterRepository, times(1)).update(character);
    }

    @Test
    public void testDeleteCharacter() {
        // Mock the repository method
        doNothing().when(characterRepository).deleteById(1L);

        // Call the service method
        characterServiceImpl.deleteCharacter(1L);

        // Verify the repository method was called
        verify(characterRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetAllCharacters() {
        // Mock the repository and mapper methods
        List<Character> characters = Arrays.asList(character);
        when(characterRepository.findAll()).thenReturn(characters);
        when(characterMapper.toDto(character)).thenReturn(characterDTO);

        // Call the service method
        List<CharacterDTO> result = characterServiceImpl.getAllCharacters();

        // Verify the result
        assertEquals(1, result.size());
        assertEquals(characterDTO, result.get(0));
    }

    @Test
    public void testGetCharactersBySpellName() {
        // Mock the repository and mapper methods
        List<Character> characters = Arrays.asList(character);
        when(characterRepository.findCharactersBySpellName("Fireball")).thenReturn(characters);
        when(characterMapper.toDto(character)).thenReturn(characterDTO);

        // Call the service method
        List<CharacterDTO> result = characterServiceImpl.getCharactersBySpellName("Fireball");

        // Verify the result
        assertEquals(1, result.size());
        assertEquals(characterDTO, result.get(0));
    }

    @Test
    public void testGetSpellsByCharacterName() {
        // Mock the repository and mapper methods
        List<Spell> spells = Arrays.asList(spell);
        when(characterRepository.findSpellsByCharacterName("Gandalf")).thenReturn(spells);
        when(characterMapper.convertSpellToDto(spell)).thenReturn(spellDTO);

        // Call the service method
        List<SpellDTO> result = characterServiceImpl.getSpellsByCharacterName("Gandalf");

        // Verify the result
        assertEquals(1, result.size());
        assertEquals(spellDTO, result.get(0));
    }

    @ParameterizedTest
    @EnumSource(value = CasterClass.class, names = {"MAGE", "CLERIC", "DRUID", "SORCERER", "WIZARD"})
    public void testGetSpellsByCasterClassAndSpellCircle(CasterClass casterClass) {
        // Mock the repository and mapper methods
        List<Spell> spells = Arrays.asList(spell);
        when(characterRepository.findSpellsByCasterClassAndSpellCircle(casterClass, SpellCircle.THIRD)).thenReturn(spells);
        when(characterMapper.convertSpellToDto(spell)).thenReturn(spellDTO);

        // Call the service method
        List<SpellDTO> result = characterServiceImpl.getSpellsByCasterClassAndSpellCircle(casterClass, SpellCircle.THIRD);

        // Verify the result
        assertEquals(1, result.size());
        assertEquals(spellDTO, result.get(0));
    }

    @Test
    public void testAddNewSpellToCharacter() {
        // Mock the mapper and repository methods
        when(characterMapper.convertDtoToSpell(spellDTO)).thenReturn(spell);
        doNothing().when(characterRepository).addNewSpellToCharacter(1L, spell);

        // Call the service method
        characterServiceImpl.addNewSpellToCharacter(1L, spellDTO);

        // Verify the repository method was called
        verify(characterRepository, times(1)).addNewSpellToCharacter(1L, spell);
    }

    @Test
    public void testAddSpellToCharacterByName() {
        // Mock the repository methods
        when(characterRepository.findByName("Gandalf")).thenReturn(Optional.of(character));
        when(spellRepository.findByName("Fireball")).thenReturn(Optional.of(spell));
        doNothing().when(characterRepository).addSpellToCharacter(character.getId(), spell.getId());

        // Call the service method
        characterServiceImpl.addSpellToCharacterByName("Gandalf", "Fireball");

        // Verify the repository method was called
        verify(characterRepository, times(1)).addSpellToCharacter(character.getId(), spell.getId());
    }

    @Test
    public void testExistsByCharacterName() {
        // Mock the repository method
        when(characterRepository.findByName("Gandalf")).thenReturn(Optional.of(character));

        // Call the service method
        boolean result = characterServiceImpl.existsByCharacterName("Gandalf");

        // Verify the result
        assertTrue(result);
    }

    @Test
    public void testExistsByCharacterName_NotFound() {
        // Mock the repository method
        when(characterRepository.findByName("Gandalf")).thenReturn(Optional.empty());

        // Call the service method
        boolean result = characterServiceImpl.existsByCharacterName("Gandalf");

        // Verify the result
        assertFalse(result);
    }
}
