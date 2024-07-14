package org.examle.aston_practice.spellbook.service;

import org.examle.aston_practice.spellbook.dto.SpellDTO;

import java.util.List;

/**
 * Service interface for managing spells.
 */
public interface SpellService {
    List<SpellDTO> findAll();
    SpellDTO findById(Long id);
    void save(SpellDTO spellDTO);
    void update(SpellDTO spellDTO);
    void deleteById(Long id);
    List<SpellDTO> findByClassAndCircle(String spellClass, String circle);
}
