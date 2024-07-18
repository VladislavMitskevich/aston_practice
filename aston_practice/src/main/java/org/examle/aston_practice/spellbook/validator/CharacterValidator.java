package org.examle.aston_practice.spellbook.validator;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.exception.InvalidInputException;
import org.examle.aston_practice.spellbook.service.CharacterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterValidator {
    private final Logger logger = LoggerFactory.getLogger(CharacterValidator.class);
    private final CharacterService characterService;

    public CharacterValidator(CharacterService characterService) {
        this.characterService = characterService;
    }

    public void validate(CharacterDTO characterDTO) {
        if (characterDTO.getName() == null || characterDTO.getName().isEmpty()) {
            logger.error("Character name is required. Received: {}", characterDTO.getName());
            throw new InvalidInputException("Character name is required");
        }
        if (characterDTO.getCasterClass() == null) {
            logger.error("Caster class is required. Received: {}", characterDTO.getCasterClass());
            throw new InvalidInputException("Caster class is required");
        }
        if (characterDTO.getLevel() < 1 || characterDTO.getLevel() > 20) {
            logger.error("Character level must be between 1 and 20. Received: {}", characterDTO.getLevel());
            throw new InvalidInputException("Character level must be between 1 and 20");
        }

        // New validation for existing character
        CharacterDTO existingCharacter = characterService.getCharacterByName(characterDTO.getName());
        if (existingCharacter != null && !existingCharacter.getId().equals(characterDTO.getId())) {
            logger.error("Character with name {} already exists", characterDTO.getName());
            throw new InvalidInputException("Character with name " + characterDTO.getName() + " already exists");
        }
    }
}
