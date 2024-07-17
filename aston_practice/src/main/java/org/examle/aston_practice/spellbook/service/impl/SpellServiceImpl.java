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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of SpellService interface.
 */
public class SpellServiceImpl implements SpellService {

    private final SpellRepository spellRepository;
    private final SpellMapper spellMapper;

    public SpellServiceImpl(SpellRepository spellRepository, SpellMapper spellMapper) {
        this.spellRepository = spellRepository;
        this.spellMapper = spellMapper;
    }

    @Override
    public List<SpellDTO> getAllSpells() {
        return spellRepository.findAll()
                .stream()
                .map(spellMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SpellDTO> getSpellById(Long id) {
        Optional<Spell> spell = spellRepository.findById(id);
        if (spell.isPresent()) {
            return Optional.of(spellMapper.toDto(spell.get()));
        } else {
            throw new SpellNotFoundException("Spell with id " + id + " not found");
        }
    }

    @Override
    public Optional<SpellDTO> getSpellByName(String name) {
        Optional<Spell> spell = spellRepository.findByName(name);
        if (spell.isPresent()) {
            return Optional.of(spellMapper.toDto(spell.get()));
        } else {
            throw new SpellNotFoundException("Spell with name " + name + " not found");
        }
    }

    @Override
    public void createSpell(SpellDTO spellDTO) {
        SpellValidator.validate(spellDTO);
        Spell spell = spellMapper.toEntity(spellDTO);
        spellRepository.save(spell);
    }

    @Override
    public void updateSpell(SpellDTO spellDTO) {
        SpellValidator.validate(spellDTO);
        Spell spell = spellMapper.toEntity(spellDTO);
        spellRepository.update(spell);
    }

    @Override
    public void deleteSpell(Long id) {
        spellRepository.deleteById(id);
    }

    @Override
    public List<SpellDTO> getSpellsByCasterClassAndCircle(CasterClass casterClass, SpellCircle circle) {
        return spellRepository.findByCasterClassAndCircle(casterClass, circle)
                .stream()
                .map(spellMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharacterDTO> getCharactersBySpellName(String spellName) {
        return spellRepository.findCharactersBySpellName(spellName)
                .stream()
                .map(spellMapper::characterToDto)
                .collect(Collectors.toList());
    }
}
