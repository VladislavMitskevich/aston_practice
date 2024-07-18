package org.examle.aston_practice.spellbook.service.impl;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.mapper.CharacterMapper;
import org.examle.aston_practice.spellbook.repository.CharacterRepository;
import org.examle.aston_practice.spellbook.repository.SpellRepository;
import org.examle.aston_practice.spellbook.service.CharacterService;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.exception.CharacterNotFoundException;
import org.examle.aston_practice.spellbook.exception.SpellNotFoundException;
import org.examle.aston_practice.spellbook.validator.CharacterValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final SpellRepository spellRepository;
    private final CharacterMapper characterMapper;
    private final CharacterValidator characterValidator;
    private static final Logger logger = LoggerFactory.getLogger(CharacterServiceImpl.class);

    public CharacterServiceImpl(CharacterRepository characterRepository, SpellRepository spellRepository, CharacterMapper characterMapper, CharacterValidator characterValidator) {
        this.characterRepository = characterRepository;
        this.spellRepository = spellRepository;
        this.characterMapper = characterMapper;
        this.characterValidator = characterValidator;
        logger.info("CharacterServiceImpl initialized");
    }

    @Override
    public List<CharacterDTO> getAllCharacters() {
        logger.info("Fetching all characters");
        return characterRepository.findAll()
                .stream()
                .map(characterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CharacterDTO> getCharacterById(Long id) {
        logger.info("Fetching character by ID: {}", id);
        return characterRepository.findById(id)
                .map(characterMapper::toDto);
    }

    @Override
    public Optional<CharacterDTO> getCharacterByName(String characterName) {
        logger.info("Fetching character by name: {}", characterName);
        return characterRepository.findByName(characterName)
                .map(characterMapper::toDto);
    }

    @Override
    public void createCharacter(CharacterDTO characterDTO) {
        logger.info("Creating character: {}", characterDTO);
        characterValidator.validate(characterDTO);
        if (existsByCharacterName(characterDTO.getName())) {
            throw new IllegalArgumentException("Character with name " + characterDTO.getName() + " already exists");
        }
        Character character = characterMapper.toEntity(characterDTO);
        characterRepository.save(character);
    }

    @Override
    public void updateCharacter(CharacterDTO characterDTO) {
        logger.info("Updating character: {}", characterDTO);
        characterValidator.validate(characterDTO);
        Character character = characterMapper.toEntity(characterDTO);
        characterRepository.update(character);
    }

    @Override
    public void deleteCharacter(Long id) {
        logger.info("Deleting character by ID: {}", id);
        characterRepository.deleteById(id);
    }

    @Override
    public List<CharacterDTO> getCharactersByCasterClass(CasterClass casterClass) {
        logger.info("Fetching characters by caster class: {}", casterClass);
        return characterRepository.findByCasterClass(casterClass)
                .stream()
                .map(characterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addSpellToCharacterByName(String characterName, String spellName) {
        logger.info("Adding spell with name {} to character with name {}", spellName, characterName);
        Optional<Character> characterOptional = characterRepository.findByName(characterName);
        Optional<Spell> spellOptional = spellRepository.findByName(spellName);

        if (!characterOptional.isPresent()) {
            throw new CharacterNotFoundException("Character with name " + characterName + " not found");
        }
        if (!spellOptional.isPresent()) {
            throw new SpellNotFoundException("Spell with name " + spellName + " not found");
        }

        characterRepository.addSpellToCharacter(characterOptional.get().getId(), spellOptional.get().getId());
    }

    @Override
    public List<CharacterDTO> getCharactersBySpellName(String spellName) {
        logger.info("Fetching characters by spell name: {}", spellName);
        return characterRepository.findCharactersBySpellName(spellName)
                .stream()
                .map(characterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpellDTO> getSpellsByCharacterName(String characterName) {
        logger.info("Fetching spells by character name: {}", characterName);
        return characterRepository.findSpellsByCharacterName(characterName)
                .stream()
                .map(characterMapper::convertSpellToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpellDTO> getSpellsByCasterClassAndSpellCircle(CasterClass casterClass, SpellCircle spellCircle) {
        logger.info("Fetching spells by caster class {} and spell circle {}", casterClass, spellCircle);
        return characterRepository.findSpellsByCasterClassAndSpellCircle(casterClass, spellCircle)
                .stream()
                .map(characterMapper::convertSpellToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addNewSpellToCharacter(Long characterId, SpellDTO spellDTO) {
        logger.info("Adding new spell to character with ID {}: {}", characterId, spellDTO);
        Spell spell = characterMapper.convertDtoToSpell(spellDTO);
        characterRepository.addNewSpellToCharacter(characterId, spell);
    }

    @Override
    public boolean existsByCharacterName(String characterName) {
        logger.info("Checking if character with name {} exists", characterName);
        return characterRepository.findByName(characterName).isPresent();
    }
}
