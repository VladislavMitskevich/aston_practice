package org.examle.aston_practice.spellbook.service.impl;

import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.mapper.SpellMapper;
import org.examle.aston_practice.spellbook.repository.SpellRepository;
import org.examle.aston_practice.spellbook.service.SpellService;

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
    public List<SpellDTO> findAll() {
        List<Spell> spells = spellRepository.findAll();
        return spells.stream()
                .map(spellMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpellDTO findById(Long id) {
        Optional<Spell> spell = spellRepository.findById(id);
        return spell.map(spellMapper::toDto).orElse(null);
    }

    @Override
    public void save(SpellDTO spellDTO) {
        Spell spell = spellMapper.toEntity(spellDTO);
        spellRepository.save(spell);
    }

    @Override
    public void update(SpellDTO spellDTO) {
        Spell spell = spellMapper.toEntity(spellDTO);
        spellRepository.update(spell);
    }

    @Override
    public void deleteById(Long id) {
        spellRepository.delete(id);
    }

    @Override
    public List<SpellDTO> findByClassAndCircle(String spellClass, String circle) {
        List<Spell> spells = spellRepository.findByClassAndCircle(spellClass, circle);
        return spells.stream()
                .map(spellMapper::toDto)
                .collect(Collectors.toList());
    }
}
