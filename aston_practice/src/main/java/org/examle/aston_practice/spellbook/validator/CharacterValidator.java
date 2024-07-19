package org.examle.aston_practice.spellbook.validator;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator class for validating CharacterDTO objects.
 */
public class CharacterValidator {
    private final Logger logger = LoggerFactory.getLogger(CharacterValidator.class);

    /**
     * Validates the given CharacterDTO object.
     *
     * @param characterDTO The CharacterDTO object to validate.
     * @throws InvalidInputException if the CharacterDTO object is invalid.
     */
    public void validate(CharacterDTO characterDTO) {
        // Validate that the character name is not null or empty
        if (characterDTO.getName() == null || characterDTO.getName().isEmpty()) {
            logger.error("Character name is required. Received: {}", characterDTO.getName());
            throw new InvalidInputException("Character name is required");
        }

        // Validate that the caster class is not null
        if (characterDTO.getCasterClass() == null) {
            logger.error("Caster class is required. Received: {}", characterDTO.getCasterClass());
            throw new InvalidInputException("Caster class is required");
        }

        // Validate that the character level is between 1 and 20
        if (characterDTO.getLevel() < 1 || characterDTO.getLevel() > 20) {
            logger.error("Character level must be between 1 and 20. Received: {}", characterDTO.getLevel());
            throw new InvalidInputException("Character level must be between 1 and 20");
        }
    }
}
