package org.examle.aston_practice.spellbook.validator;

import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.exception.InvalidInputException;
import org.examle.aston_practice.spellbook.exception.SpellNotFoundException;
import org.examle.aston_practice.spellbook.service.SpellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator class for validating SpellDTO objects.
 */
public class SpellValidator {
    private final Logger logger = LoggerFactory.getLogger(SpellValidator.class);
    private final SpellService spellService;

    /**
     * Constructor for SpellValidator.
     *
     * @param spellService The SpellService instance used to check for existing spells.
     */
    public SpellValidator(SpellService spellService) {
        this.spellService = spellService;
    }

    /**
     * Validates the given SpellDTO object.
     *
     * @param spellDTO The SpellDTO object to validate.
     * @throws InvalidInputException if the SpellDTO object is invalid.
     */
    public void validate(SpellDTO spellDTO) {
        // Validate that the spell name is not null or empty
        if (spellDTO.getName() == null || spellDTO.getName().isEmpty()) {
            logger.error("Spell name is required. Received: {}", spellDTO.getName());
            throw new InvalidInputException("Spell name is required");
        }

        // Validate that the school of magic is not null
        if (spellDTO.getSchool() == null) {
            logger.error("School of magic is required. Received: {}", spellDTO.getSchool());
            throw new InvalidInputException("School of magic is required");
        }

        // Validate that the spell circle is not null
        if (spellDTO.getCircle() == null) {
            logger.error("Spell circle is required. Received: {}", spellDTO.getCircle());
            throw new InvalidInputException("Spell circle is required");
        }

        // Validate that the spell description is not null or empty
        if (spellDTO.getDescription() == null || spellDTO.getDescription().isEmpty()) {
            logger.error("Spell description is required. Received: {}", spellDTO.getDescription());
            throw new InvalidInputException("Spell description is required");
        }

        // Check if a spell with the same name already exists
        try {
            SpellDTO existingSpell = spellService.findSpellByName(spellDTO.getName());
            // If a spell with the same name exists, ensure it is the same spell being updated
            if (!existingSpell.getId().equals(spellDTO.getId())) {
                logger.error("Spell with name {} already exists with different details", spellDTO.getName());
                throw new InvalidInputException("Spell with name " + spellDTO.getName() + " already exists with different details");
            }
        } catch (SpellNotFoundException e) {
            // If the spell is not found, it means there is no existing spell with the same name, so validation passes
        }
    }
}
