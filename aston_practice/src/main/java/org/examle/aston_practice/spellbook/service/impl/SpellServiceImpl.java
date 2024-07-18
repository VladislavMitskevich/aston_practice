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

public class SpellServiceImpl implements SpellService {

    private final SpellRepository spellRepository;
    private final SpellMapper spellMapper;
    private final SpellValidator spellValidator;
    private static final Logger logger = LoggerFactory.getLogger(SpellServiceImpl.class);

    public SpellServiceImpl(SpellRepository spellRepository, SpellMapper spellMapper, SpellValidator spellValidator) {
        this.spellRepository = spellRepository;
        this.spellMapper = spellMapper;
        this.spellValidator = spellValidator;
        logger.info("SpellServiceImpl initialized");
    }

    @Override
    public List<SpellDTO> getAllSpells() {
        logger.info("Fetching all spells");
        return spellRepository.findAll()
                .stream()
                .map(spellMapper::toDto)
                .collect(Collectors.toList());
    }

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

    @Override
    public void updateSpell(SpellDTO spellDTO) {
        logger.info("Updating spell: {}", spellDTO);
        spellValidator.validate(spellDTO);
        Spell spell = spellMapper.toEntity(spellDTO);
        spellRepository.update(spell);
    }

    @Override
    public void deleteSpell(Long id) {
        logger.info("Deleting spell by ID: {}", id);
        spellRepository.deleteById(id);
    }

    @Override
    public List<SpellDTO> getSpellsByCasterClassAndCircle(CasterClass casterClass, SpellCircle circle) {
        logger.info("Fetching spells by caster class {} and circle {}", casterClass, circle);
        return spellRepository.findByCasterClassAndCircle(casterClass, circle)
                .stream()
                .map(spellMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharacterDTO> getCharactersBySpellName(String spellName) {
        logger.info("Fetching characters by spell name: {}", spellName);
        return spellRepository.findCharactersBySpellName(spellName)
                .stream()
                .map(spellMapper::characterToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsBySpellName(String spellName) {
        logger.info("Checking if spell with name {} exists", spellName);
        return spellRepository.findByName(spellName).isPresent();
    }

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
