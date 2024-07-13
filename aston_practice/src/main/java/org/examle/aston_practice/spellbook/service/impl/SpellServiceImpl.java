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
 * Implementation of SpellService
 */
public class SpellServiceImpl implements SpellService {

    private final SpellRepository spellRepository;

    public SpellServiceImpl(SpellRepository spellRepository) {
        this.spellRepository = spellRepository;
    }

    @Override
    public List<SpellDTO> findAll() {
        return spellRepository.findAll().stream()
                .map(SpellMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SpellDTO> findById(Long id) {
        return spellRepository.findById(id).map(SpellMapper::toDTO);
    }

    @Override
    public void save(SpellDTO spellDTO) {
        Spell spell = SpellMapper.toEntity(spellDTO);
        spellRepository.save(spell);
    }

    @Override
    public void update(SpellDTO spellDTO) {
        Spell spell = SpellMapper.toEntity(spellDTO);
        spellRepository.update(spell);
    }

    @Override
    public void delete(Long id) {
        spellRepository.delete(id);
    }

    @Override
    public List<SpellDTO> findByClassAndCircle(String spellClass, String circle) {
        return spellRepository.findByClassAndCircle(spellClass, circle).stream()
                .map(SpellMapper::toDTO)
                .collect(Collectors.toList());
    }
}