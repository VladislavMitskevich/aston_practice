package org.examle.aston_practice.spellbook.service;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;

import java.util.List;

/**
 * Service interface for managing characters.
 */
public interface CharacterService {
    List<CharacterDTO> findAll();
    CharacterDTO findById(Long id);
    void save(CharacterDTO characterDTO);
    void update(CharacterDTO characterDTO);
    void deleteById(Long id);
}
