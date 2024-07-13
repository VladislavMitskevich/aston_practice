package org.examle.aston_practice.spellbook.service.impl;

import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.mapper.SpellMapper;
import org.examle.aston_practice.spellbook.repository.SpellRepository;
import org.examle.aston_practice.spellbook.service.SpellService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of SpellService.
 */
public class SpellServiceImpl implements SpellService {

    private final SpellRepository spellRepository;

    public SpellServiceImpl(SpellRepository spellRepository) {
        this.spellRepository = spellRepository;
    }

    @Override
    public List<SpellDTO> getAllSpells() {
        return spellRepository.findAll().stream()
                .map(SpellMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SpellDTO getSpellById(Long id) {
        return spellRepository.findById(id)
                .map(SpellMapper::toDTO)
                .orElse(null);
    }

    @Override
    public void createSpell(SpellDTO spellDTO) {
        Spell spell = SpellMapper.toEntity(spellDTO);
        spellRepository.save(spell);
    }

    @Override
    public void updateSpell(SpellDTO spellDTO) {
        Spell spell = SpellMapper.toEntity(spellDTO);
        spellRepository.update(spell);
    }

    @Override
    public void deleteSpell(Long id) {
        spellRepository.delete(id);
    }

    @Override
    public List<SpellDTO> getSpellsByClass(String spellClass) {
        return spellRepository.findByClass(Long.parseLong(spellClass)).stream()
                .map(SpellMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpellDTO> getSpellsByCircle(String circle) {
        return spellRepository.findByCircle(circle).stream()
                .map(SpellMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpellDTO> getSpellsBySchool(String school) {
        return spellRepository.findBySchool(school).stream()
                .map(SpellMapper::toDTO)
                .collect(Collectors.toList());
    }
}