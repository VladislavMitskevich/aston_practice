package org.examle.aston_practice.spellbook.service.impl;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.mapper.CharacterMapper;
import org.examle.aston_practice.spellbook.repository.CharacterRepository;
import org.examle.aston_practice.spellbook.service.CharacterService;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.exception.CharacterNotFoundException;
import org.examle.aston_practice.spellbook.validator.CharacterValidator;

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
        Optional<Character> character = characterRepository.findById(id);
        if (character.isPresent()) {
            return Optional.of(characterMapper.toDto(character.get()));
        } else {
            throw new CharacterNotFoundException("Character with id " + id + " not found");
        }
    }

    @Override
    public Optional<CharacterDTO> getCharacterByName(String name) {
        Optional<Character> character = characterRepository.findByName(name);
        if (character.isPresent()) {
            return Optional.of(characterMapper.toDto(character.get()));
        } else {
            throw new CharacterNotFoundException("Character with name " + name + " not found");
        }
    }

    @Override
    public void createCharacter(CharacterDTO characterDTO) {
        CharacterValidator.validate(characterDTO);
        Character character = characterMapper.toEntity(characterDTO);
        characterRepository.save(character);
    }

    @Override
    public void updateCharacter(CharacterDTO characterDTO) {
        CharacterValidator.validate(characterDTO);
        Character character = characterMapper.toEntity(characterDTO);
        characterRepository.update(character);
    }

    @Override
    public void deleteCharacter(Long id) {
        characterRepository.deleteById(id);
    }

    @Override
    public List<CharacterDTO> getCharactersByCasterClass(CasterClass casterClass) {
        return characterRepository.findByCasterClass(casterClass)
                .stream()
                .map(characterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addSpellToCharacter(Long characterId, Long spellId) {
        characterRepository.addSpellToCharacter(characterId, spellId);
    }

    @Override
    public List<CharacterDTO> getCharactersBySpellName(String spellName) {
        return characterRepository.findBySpellName(spellName)
                .stream()
                .map(characterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpellDTO> getSpellsByCharacterName(String name) {
        Optional<Character> character = characterRepository.findByName(name);
        if (character.isPresent()) {
            return character.get().getSpells()
                    .stream()
                    .map(characterMapper::spellToDto)
                    .collect(Collectors.toList());
        } else {
            throw new CharacterNotFoundException("Character with name " + name + " not found");
        }
    }

    @Override
    public List<SpellDTO> getSpellsByCasterClassAndSpellCircle(CasterClass casterClass, SpellCircle spellCircle) {
        return characterRepository.findSpellsByCasterClassAndSpellCircle(casterClass, spellCircle)
                .stream()
                .map(characterMapper::spellToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addNewSpellToCharacter(Long characterId, SpellDTO spellDTO) {
        Spell spell = characterMapper.dtoToSpell(spellDTO);
        characterRepository.addNewSpellToCharacter(characterId, spell);
    }
}
