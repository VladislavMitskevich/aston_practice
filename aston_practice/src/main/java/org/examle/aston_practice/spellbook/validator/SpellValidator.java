package org.examle.aston_practice.spellbook.validator;

import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.exception.InvalidInputException;
import org.examle.aston_practice.spellbook.exception.SpellNotFoundException;
import org.examle.aston_practice.spellbook.service.SpellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpellValidator {
    private final Logger logger = LoggerFactory.getLogger(SpellValidator.class);
    private final SpellService spellService;

    public SpellValidator(SpellService spellService) {
        this.spellService = spellService;
    }

    public void validate(SpellDTO spellDTO) {
        if (spellDTO.getName() == null || spellDTO.getName().isEmpty()) {
            logger.error("Spell name is required. Received: {}", spellDTO.getName());
            throw new InvalidInputException("Spell name is required");
        }
        if (spellDTO.getSchool() == null) {
            logger.error("School of magic is required. Received: {}", spellDTO.getSchool());
            throw new InvalidInputException("School of magic is required");
        }
        if (spellDTO.getCircle() == null) {
            logger.error("Spell circle is required. Received: {}", spellDTO.getCircle());
            throw new InvalidInputException("Spell circle is required");
        }
        if (spellDTO.getDescription() == null || spellDTO.getDescription().isEmpty()) {
            logger.error("Spell description is required. Received: {}", spellDTO.getDescription());
            throw new InvalidInputException("Spell description is required");
        }

        // Проверка на существование заклинания с таким именем
        try {
            SpellDTO existingSpell = spellService.findSpellByName(spellDTO.getName());
            // Если заклинание существует, проверяем, совпадают ли все поля
            if (!existingSpell.getId().equals(spellDTO.getId())) {
                logger.error("Spell with name {} already exists with different details", spellDTO.getName());
                throw new InvalidInputException("Spell with name " + spellDTO.getName() + " already exists with different details");
            }
        } catch (SpellNotFoundException e) {
            // Если заклинание не найдено, то валидация проходит успешно
        }
    }
}
