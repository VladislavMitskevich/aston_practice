package org.examle.aston_practice.spellbook.validator;

import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.exception.InvalidInputException;

public class CharacterValidator {
    public static void validate(CharacterDTO characterDTO) {
        if (characterDTO.getName() == null || characterDTO.getName().isEmpty()) {
            throw new InvalidInputException("Character name is required");
        }
        if (characterDTO.getCasterClass() == null) {
            throw new InvalidInputException("Caster class is required");
        }
        if (characterDTO.getLevel() < 1 || characterDTO.getLevel() > 20) {
            throw new InvalidInputException("Character level must be between 1 and 20");
        }
    }
}
