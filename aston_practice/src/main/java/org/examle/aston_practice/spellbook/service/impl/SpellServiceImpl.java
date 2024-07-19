package org.examle.aston_practice.spellbook.service.impl;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.mapper.SpellMapper;
import org.examle.aston_practice.spellbook.repository.SpellRepository;
import org.examle.aston_practice.spellbook.service.SpellService;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.exception.SpellNotFoundException;
import org.examle.aston_practice.spellbook.validator.SpellValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the SpellService interface that provides business logic
 * for managing Spell entities.
 */
public class SpellServiceImpl implements SpellService {

    private final SpellRepository spellRepository;
    private final SpellMapper spellMapper;
    private final SpellValidator spellValidator;
    private static final Logger logger = LoggerFactory.getLogger(SpellServiceImpl.class);

    /**
     * Constructs a new SpellServiceImpl with the given dependencies.
     *
     * @param spellRepository the repository for Spell entities
     * @param spellMapper the mapper for converting between Spell entities and DTOs
     * @param spellValidator the validator for Spell DTOs
     */
    public SpellServiceImpl(SpellRepository spellRepository, SpellMapper spellMapper, SpellValidator spellValidator) {
        this.spellRepository = spellRepository;
        this.spellMapper = spellMapper;
        this.spellValidator = spellValidator;
        logger.info("SpellServiceImpl initialized");
    }

    /**
     * Fetches all spells from the repository and converts them to DTOs.
     *
     * @return a list of all SpellDTOs
     */
    @Override
    public List<SpellDTO> getAllSpells() {
        logger.info("Fetching all spells");
        return spellRepository.findAll()
                .stream()
                .map(spellMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Fetches a spell by its ID from the repository and converts it to a DTO.
     * Throws a SpellNotFoundException if the spell is not found.
     *
     * @param id the ID of the spell to fetch
     * @return an Optional containing the SpellDTO if found, otherwise empty
     * @throws SpellNotFoundException if the spell is not found
     */
    @Override
    public Optional<SpellDTO> getSpellById(Long id) {
        logger.info("Fetching spell by ID: {}", id);
        Optional<Spell> spell = spellRepository.findById(id);
        if (spell.isPresent()) {
            return Optional.of(spellMapper.toDto(spell.get()));
        } else {
            logger.error("Spell with id {} not found", id);
            throw new SpellNotFoundException("Spell with id " + id + " not found");
        }
    }

    /**
     * Fetches a spell by its name from the repository and converts it to a DTO.
     * Throws a SpellNotFoundException if the spell is not found.
     *
     * @param spellName the name of the spell to fetch
     * @return an Optional containing the SpellDTO if found, otherwise empty
     * @throws SpellNotFoundException if the spell is not found
     */
    @Override
    public Optional<SpellDTO> getSpellByName(String spellName) {
        logger.info("Fetching spell by name: {}", spellName);
        Optional<Spell> spell = spellRepository.findByName(spellName);
        if (spell.isPresent()) {
            return Optional.of(spellMapper.toDto(spell.get()));
        } else {
            logger.error("Spell with name {} not found", spellName);
            throw new SpellNotFoundException("Spell with name " + spellName + " not found");
        }
    }

    /**
     * Validates and creates a new spell from the given DTO.
     * Throws an IllegalArgumentException if a spell with the same name already exists.
     *
     * @param spellDTO the DTO containing the spell data to create
     */
    @Override
    public void createSpell(SpellDTO spellDTO) {
        logger.info("Creating spell: {}", spellDTO);
        spellValidator.validate(spellDTO);
        if (existsBySpellName(spellDTO.getName())) {
            throw new IllegalArgumentException("Spell with name " + spellDTO.getName() + " already exists");
        }
        Spell spell = spellMapper.toEntity(spellDTO);
        spellRepository.save(spell);
    }

    /**
     * Validates and updates an existing spell from the given DTO.
     *
     * @param spellDTO the DTO containing the spell data to update
     */
    @Override
    public void updateSpell(SpellDTO spellDTO) {
        logger.info("Updating spell: {}", spellDTO);
        spellValidator.validate(spellDTO);
        Spell spell = spellMapper.toEntity(spellDTO);
        spellRepository.update(spell);
    }

    /**
     * Deletes a spell by its ID.
     *
     * @param id the ID of the spell to delete
     */
    @Override
    public void deleteSpell(Long id) {
        logger.info("Deleting spell by ID: {}", id);
        spellRepository.deleteById(id);
    }

    /**
     * Fetches spells by caster class and spell circle from the repository and converts them to DTOs.
     *
     * @param casterClass the caster class to filter by
     * @param circle the spell circle to filter by
     * @return a list of SpellDTOs that match the specified caster class and spell circle
     */
    @Override
    public List<SpellDTO> getSpellsByCasterClassAndCircle(CasterClass casterClass, SpellCircle circle) {
        logger.info("Fetching spells by caster class {} and circle {}", casterClass, circle);
        return spellRepository.findByCasterClassAndCircle(casterClass, circle)
                .stream()
                .map(spellMapper::toDto)
                .collect(Collectors.toList());
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
        return spellRepository.findCharactersBySpellName(spellName)
                .stream()
                .map(spellMapper::characterToDto)
                .collect(Collectors.toList());
    }

    /**
     * Checks if a spell with the given name exists in the repository.
     *
     * @param spellName the name of the spell to check
     * @return true if the spell exists, otherwise false
     */
    @Override
    public boolean existsBySpellName(String spellName) {
        logger.info("Checking if spell with name {} exists", spellName);
        return spellRepository.findByName(spellName).isPresent();
    }

    /**
     * Fetches a spell by its name from the repository and converts it to a DTO.
     * Throws a SpellNotFoundException if the spell is not found.
     *
     * @param spellName the name of the spell to fetch
     * @return the SpellDTO if found
     * @throws SpellNotFoundException if the spell is not found
     */
    @Override
    public SpellDTO findSpellByName(String spellName) throws SpellNotFoundException {
        logger.info("Fetching spell by name: {}", spellName);
        Optional<Spell> spell = spellRepository.findByName(spellName);
        if (spell.isPresent()) {
            return spellMapper.toDto(spell.get());
        } else {
            logger.error("Spell with name {} not found", spellName);
            throw new SpellNotFoundException("Spell with name " + spellName + " not found");
        }
    }
}

