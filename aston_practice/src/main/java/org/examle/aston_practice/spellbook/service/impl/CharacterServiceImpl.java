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

/**
 * Implementation of the CharacterService interface that provides business logic
 * for managing Character entities.
 */
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final SpellRepository spellRepository;
    private final CharacterMapper characterMapper;
    private final CharacterValidator characterValidator;
    private static final Logger logger = LoggerFactory.getLogger(CharacterServiceImpl.class);

    /**
     * Constructs a new CharacterServiceImpl with the given dependencies.
     *
     * @param characterRepository the repository for Character entities
     * @param spellRepository the repository for Spell entities
     * @param characterMapper the mapper for converting between Character entities and DTOs
     * @param characterValidator the validator for Character DTOs
     */
    public CharacterServiceImpl(CharacterRepository characterRepository, SpellRepository spellRepository, CharacterMapper characterMapper, CharacterValidator characterValidator) {
        this.characterRepository = characterRepository;
        this.spellRepository = spellRepository;
        this.characterMapper = characterMapper;
        this.characterValidator = characterValidator;
        logger.info("CharacterServiceImpl initialized");
    }

    /**
     * Fetches all characters from the repository and converts them to DTOs.
     *
     * @return a list of all CharacterDTOs
     */
    @Override
    public List<CharacterDTO> getAllCharacters() {
        logger.info("Fetching all characters");
        return characterRepository.findAll()
                .stream()
                .map(characterMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Fetches a character by its ID from the repository and converts it to a DTO.
     *
     * @param id the ID of the character to fetch
     * @return an Optional containing the CharacterDTO if found, otherwise empty
     */
    @Override
    public Optional<CharacterDTO> getCharacterById(Long id) {
        logger.info("Fetching character by ID: {}", id);
        return characterRepository.findById(id)
                .map(characterMapper::toDto);
    }

    /**
     * Fetches a character by its name from the repository and converts it to a DTO.
     * Throws a CharacterNotFoundException if the character is not found.
     *
     * @param characterName the name of the character to fetch
     * @return the CharacterDTO if found
     * @throws CharacterNotFoundException if the character is not found
     */
    @Override
    public CharacterDTO getCharacterByName(String characterName) {
        logger.info("Fetching character by name: {}", characterName);
        Optional<Character> characterOptional = characterRepository.findByName(characterName);
        if (characterOptional.isPresent()) {
            return characterMapper.toDto(characterOptional.get());
        } else {
            throw new CharacterNotFoundException("Character with name " + characterName + " not found");
        }
    }

    /**
     * Validates and creates a new character from the given DTO.
     * Throws an IllegalArgumentException if a character with the same name already exists.
     *
     * @param characterDTO the DTO containing the character data to create
     */
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

    /**
     * Validates and updates an existing character from the given DTO.
     *
     * @param characterDTO the DTO containing the character data to update
     */
    @Override
    public void updateCharacter(CharacterDTO characterDTO) {
        logger.info("Updating character: {}", characterDTO);
        characterValidator.validate(characterDTO);
        Character character = characterMapper.toEntity(characterDTO);
        characterRepository.update(character);
    }

    /**
     * Deletes a character by its ID.
     *
     * @param id the ID of the character to delete
     */
    @Override
    public void deleteCharacter(Long id) {
        logger.info("Deleting character by ID: {}", id);
        characterRepository.deleteById(id);
    }

    /**
     * Fetches characters by their caster class from the repository and converts them to DTOs.
     *
     * @param casterClass the caster class to filter by
     * @return a list of CharacterDTOs with the specified caster class
     */
    @Override
    public List<CharacterDTO> getCharactersByCasterClass(CasterClass casterClass) {
        logger.info("Fetching characters by caster class: {}", casterClass);
        return characterRepository.findByCasterClass(casterClass)
                .stream()
                .map(characterMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Adds a spell to a character by their names.
     * Throws CharacterNotFoundException if the character is not found.
     * Throws SpellNotFoundException if the spell is not found.
     *
     * @param characterName the name of the character to add the spell to
     * @param spellName the name of the spell to add
     */
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

    /**
     * Fetches characters by a spell name from the repository and converts them to DTOs.
     *
     * @param spellName the name of the spell to filter by
     * @return a list of CharacterDTOs who have the specified spell
     */
    @Override
    public List<CharacterDTO> getCharactersBySpellName(String spellName) {
        logger.info("Fetching characters by spell name: {}", spellName);
        return characterRepository.findCharactersBySpellName(spellName)
                .stream()
                .map(characterMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Fetches spells by a character name from the repository and converts them to DTOs.
     *
     * @param characterName the name of the character to filter by
     * @return a list of SpellDTOs that the character has
     */
    @Override
    public List<SpellDTO> getSpellsByCharacterName(String characterName) {
        logger.info("Fetching spells by character name: {}", characterName);
        return characterRepository.findSpellsByCharacterName(characterName)
                .stream()
                .map(characterMapper::convertSpellToDto)
                .collect(Collectors.toList());
    }

    /**
     * Fetches spells by caster class and spell circle from the repository and converts them to DTOs.
     *
     * @param casterClass the caster class to filter by
     * @param spellCircle the spell circle to filter by
     * @return a list of SpellDTOs that match the specified caster class and spell circle
     */
    @Override
    public List<SpellDTO> getSpellsByCasterClassAndSpellCircle(CasterClass casterClass, SpellCircle spellCircle) {
        logger.info("Fetching spells by caster class {} and spell circle {}", casterClass, spellCircle);
        return characterRepository.findSpellsByCasterClassAndSpellCircle(casterClass, spellCircle)
                .stream()
                .map(characterMapper::convertSpellToDto)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new spell to a character by their ID.
     *
     * @param characterId the ID of the character to add the spell to
     * @param spellDTO the DTO containing the spell data to add
     */
    @Override
    public void addNewSpellToCharacter(Long characterId, SpellDTO spellDTO) {
        logger.info("Adding new spell to character with ID {}: {}", characterId, spellDTO);
        Spell spell = characterMapper.convertDtoToSpell(spellDTO);
        characterRepository.addNewSpellToCharacter(characterId, spell);
    }

    /**
     * Checks if a character with the given name exists in the repository.
     *
     * @param characterName the name of the character to check
     * @return true if the character exists, otherwise false
     */
    @Override
    public boolean existsByCharacterName(String characterName) {
        logger.info("Checking if character with name {} exists", characterName);
        return characterRepository.findByName(characterName).isPresent();
    }
}
