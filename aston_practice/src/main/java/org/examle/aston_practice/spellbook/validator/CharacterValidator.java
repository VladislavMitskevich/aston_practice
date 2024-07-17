package org.examle.aston_practice.spellbook.validator;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterValidator {
    private static final Logger logger = LoggerFactory.getLogger(CharacterValidator.class);

    public static void validate(CharacterDTO characterDTO) {
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
    }
}
