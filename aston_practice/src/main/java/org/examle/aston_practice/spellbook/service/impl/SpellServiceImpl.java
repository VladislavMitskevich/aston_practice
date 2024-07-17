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
    private static final Logger logger = LoggerFactory.getLogger(SpellServiceImpl.class);

    public SpellServiceImpl(SpellRepository spellRepository, SpellMapper spellMapper) {
        this.spellRepository = spellRepository;
        this.spellMapper = spellMapper;
        logger.info("123: SpellServiceImpl initialized");
    }

    @Override
    public List<SpellDTO> getAllSpells() {
        logger.info("123: Fetching all spells");
        return spellRepository.findAll()
                .stream()
                .map(spellMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SpellDTO> getSpellById(Long id) {
        logger.info("123: Fetching spell by ID: {}", id);
        Optional<Spell> spell = spellRepository.findById(id);
        if (spell.isPresent()) {
            return Optional.of(spellMapper.toDto(spell.get()));
        } else {
            logger.error("123: Spell with id {} not found", id);
            throw new SpellNotFoundException("Spell with id " + id + " not found");
        }
    }

    @Override
    public Optional<SpellDTO> getSpellByName(String name) {
        logger.info("123: Fetching spell by name: {}", name);
        Optional<Spell> spell = spellRepository.findByName(name);
        if (spell.isPresent()) {
            return Optional.of(spellMapper.toDto(spell.get()));
        } else {
            logger.error("123: Spell with name {} not found", name);
            throw new SpellNotFoundException("Spell with name " + name + " not found");
        }
    }

    @Override
    public void createSpell(SpellDTO spellDTO) {
        logger.info("123: Creating spell: {}", spellDTO);
        SpellValidator.validate(spellDTO);
        Spell spell = spellMapper.toEntity(spellDTO);
        spellRepository.save(spell);
    }

    @Override
    public void updateSpell(SpellDTO spellDTO) {
        logger.info("123: Updating spell: {}", spellDTO);
        SpellValidator.validate(spellDTO);
        Spell spell = spellMapper.toEntity(spellDTO);
        spellRepository.update(spell);
    }

    @Override
    public void deleteSpell(Long id) {
        logger.info("123: Deleting spell by ID: {}", id);
        spellRepository.deleteById(id);
    }

    @Override
    public List<SpellDTO> getSpellsByCasterClassAndCircle(CasterClass casterClass, SpellCircle circle) {
        logger.info("123: Fetching spells by caster class {} and circle {}", casterClass, circle);
        return spellRepository.findByCasterClassAndCircle(casterClass, circle)
                .stream()
                .map(spellMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharacterDTO> getCharactersBySpellName(String spellName) {
        logger.info("123: Fetching characters by spell name: {}", spellName);
        return spellRepository.findCharactersBySpellName(spellName)
                .stream()
                .map(spellMapper::characterToDto)
                .collect(Collectors.toList());
    }
}
