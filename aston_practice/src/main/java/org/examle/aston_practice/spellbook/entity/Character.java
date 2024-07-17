package org.examle.aston_practice.spellbook.entity;

import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Entity representing a Character.
 * This class is used to represent a character in the database.
 */
@Getter
@Setter
public class Character {
    /**
     * Unique identifier for the character.
     */
    private Long id;

    /**
     * Name of the character.
     */
    private String name;

    /**
     * Caster class of the character.
     */
    private CasterClass casterClass;

    /**
     * Level of the character.
     */
    private int level;

    /**
     * Map of spells available to the character by circle.
     */
    private Map<SpellCircle, List<Spell>> spellsByCircle;

    /**
     * Returns a list of all spells available to the character.
     * @return list of all spells
     */
    public List<Spell> getSpells() {
        if (spellsByCircle == null) {
            return java.util.Collections.emptyList();
        }
        return spellsByCircle.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * Sets the spells available to the character.
     * @param spellsByCircle the map of spells by circle
     */
    public void setSpells(Map<SpellCircle, List<Spell>> spellsByCircle) {
        this.spellsByCircle = spellsByCircle;
    }

    /**
     * Returns a string representation of the character.
     * @return character details as a string
     */
    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", casterClass=" + casterClass +
                ", level=" + level +
                ", spellsByCircle=" + spellsByCircle +
                '}';
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return level == character.level &&
                Objects.equals(id, character.id) &&
                Objects.equals(name, character.name) &&
                casterClass == character.casterClass &&
                Objects.equals(spellsByCircle, character.spellsByCircle);
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, casterClass, level, spellsByCircle);
    }
}
