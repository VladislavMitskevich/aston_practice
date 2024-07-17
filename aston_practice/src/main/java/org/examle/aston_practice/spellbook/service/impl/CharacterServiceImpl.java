package org.examle.aston_practice.spellbook.service.impl;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.mapper.CharacterMapper;
import org.examle.aston_practice.spellbook.repository.CharacterRepository;
import org.examle.aston_practice.spellbook.service.CharacterService;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.exception.CharacterNotFoundException;
import org.examle.aston_practice.spellbook.validator.CharacterValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of CharacterService interface.
 */
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;
    private static final Logger logger = LoggerFactory.getLogger(CharacterServiceImpl.class);

    public CharacterServiceImpl(CharacterRepository characterRepository, CharacterMapper characterMapper) {
        this.characterRepository = characterRepository;
        this.characterMapper = characterMapper;
        logger.info("123: CharacterServiceImpl initialized");
    }

    @Override
    public List<CharacterDTO> getAllCharacters() {
        logger.info("123: Fetching all characters");
        return characterRepository.findAll()
                .stream()
                .map(characterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CharacterDTO> getCharacterById(Long id) {
        logger.info("123: Fetching character by ID: {}", id);
        return characterRepository.findById(id)
                .map(characterMapper::toDto);
    }

    @Override
    public Optional<CharacterDTO> getCharacterByName(String name) {
        logger.info("123: Fetching character by name: {}", name);
        return characterRepository.findByName(name)
                .map(characterMapper::toDto);
    }

    @Override
    public void createCharacter(CharacterDTO characterDTO) {
        logger.info("123: Creating character: {}", characterDTO);
        CharacterValidator.validate(characterDTO);
        Character character = characterMapper.toEntity(characterDTO);
        characterRepository.save(character);
    }

    @Override
    public void updateCharacter(CharacterDTO characterDTO) {
        logger.info("123: Updating character: {}", characterDTO);
        CharacterValidator.validate(characterDTO);
        Character character = characterMapper.toEntity(characterDTO);
        characterRepository.update(character);
    }

    @Override
    public void deleteCharacter(Long id) {
        logger.info("123: Deleting character by ID: {}", id);
        characterRepository.deleteById(id);
    }

    @Override
    public List<CharacterDTO> getCharactersByCasterClass(CasterClass casterClass) {
        logger.info("123: Fetching characters by caster class: {}", casterClass);
        return characterRepository.findByCasterClass(casterClass)
                .stream()
                .map(characterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addSpellToCharacter(Long characterId, Long spellId) {
        logger.info("123: Adding spell with ID {} to character with ID {}", spellId, characterId);
        characterRepository.addSpellToCharacter(characterId, spellId);
    }

    @Override
    public List<CharacterDTO> getCharactersBySpellName(String spellName) {
        logger.info("123: Fetching characters by spell name: {}", spellName);
        return characterRepository.findCharactersBySpellName(spellName)
                .stream()
                .map(characterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpellDTO> getSpellsByCharacterName(String name) {
        logger.info("123: Fetching spells by character name: {}", name);
        return characterRepository.findSpellsByCharacterName(name)
                .stream()
                .map(characterMapper::spellToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpellDTO> getSpellsByCasterClassAndSpellCircle(CasterClass casterClass, SpellCircle spellCircle) {
        logger.info("123: Fetching spells by caster class {} and spell circle {}", casterClass, spellCircle);
        return characterRepository.findSpellsByCasterClassAndSpellCircle(casterClass, spellCircle)
                .stream()
                .map(characterMapper::spellToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addNewSpellToCharacter(Long characterId, SpellDTO spellDTO) {
        logger.info("123: Adding new spell to character with ID {}: {}", characterId, spellDTO);
        Spell spell = characterMapper.dtoToSpell(spellDTO);
        characterRepository.addNewSpellToCharacter(characterId, spell);
    }
}
