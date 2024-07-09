package org.examle.aston_practice.spellbook.mapper;

import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.entity.Class;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Spell entity and SpellDTO.
 */
public class SpellMapper {

    /**
     * Converts Spell entity to SpellDTO.
     * @param spell the Spell entity
     * @return the SpellDTO
     */
    public SpellDTO toDTO(Spell spell) {
        SpellDTO spellDTO = new SpellDTO();
        spellDTO.setId(spell.getId());
        spellDTO.setName(spell.getName());
        spellDTO.setDescription(spell.getDescription());
        spellDTO.setSchool(spell.getSchool());
        spellDTO.setCircle(spell.getCircle());
        spellDTO.setClassCircles(spell.getClasses().stream()
                .map(c -> c.getName() + "(" + spell.getCircle().name() + ")")
                .collect(Collectors.toSet()));
        return spellDTO;
    }

    /**
     * Converts SpellDTO to Spell entity.
     * @param spellDTO the SpellDTO
     * @param classes the set of classes
     * @return the Spell entity
     */
    public Spell toEntity(SpellDTO spellDTO, Set<Class> classes) {
        Spell spell = new Spell();
        spell.setId(spellDTO.getId());
        spell.setName(spellDTO.getName());
        spell.setDescription(spellDTO.getDescription());
        spell.setSchool(spellDTO.getSchool());
        spell.setCircle(spellDTO.getCircle());
        spell.setClasses(classes);
        return spell;
    }
}
