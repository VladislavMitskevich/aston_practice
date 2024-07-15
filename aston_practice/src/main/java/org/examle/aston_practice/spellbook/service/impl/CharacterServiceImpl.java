package org.examle.aston_practice.spellbook.service.impl;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.mapper.CharacterMapper;
import org.examle.aston_practice.spellbook.repository.CharacterRepository;
import org.examle.aston_practice.spellbook.service.CharacterService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of CharacterService interface.
 */
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    public CharacterServiceImpl(CharacterRepository characterRepository, CharacterMapper characterMapper) {
        this.characterRepository = characterRepository;
        this.characterMapper = characterMapper;
    }

    @Override
    public List<CharacterDTO> getAllCharacters() {
        return characterRepository.findAll()
                .stream()
                .map(characterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CharacterDTO> getCharacterById(Long id) {
        return characterRepository.findById(id)
                .map(characterMapper::toDto);
    }

    @Override
    public void createCharacter(CharacterDTO characterDTO) {
        Character character = characterMapper.toEntity(characterDTO);
        characterRepository.save(character);
    }

    @Override
    public void updateCharacter(CharacterDTO characterDTO) {
        Character character = characterMapper.toEntity(characterDTO);
        characterRepository.update(character);
    }

    @Override
    public void deleteCharacter(Long id) {
        characterRepository.deleteById(id);
    }
}
