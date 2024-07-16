package org.examle.aston_practice.spellbook.validator;

import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.exception.InvalidInputException;

public class SpellValidator {
    public static void validate(SpellDTO spellDTO) {
        if (spellDTO.getName() == null || spellDTO.getName().isEmpty()) {
            throw new InvalidInputException("Spell name is required");
        }
        if (spellDTO.getSchool() == null) {
            throw new InvalidInputException("School of magic is required");
        }
        if (spellDTO.getCircle() == null) {
            throw new InvalidInputException("Spell circle is required");
        }
        if (spellDTO.getDescription() == null || spellDTO.getDescription().isEmpty()) {
            throw new InvalidInputException("Spell description is required");
        }
    }
}
