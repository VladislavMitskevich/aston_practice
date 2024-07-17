package org.examle.aston_practice.spellbook.repository.impl;

import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SchoolOfMagic;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.repository.SpellRepository;
import org.examle.aston_practice.spellbook.util.DatabaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SpellRepositoryImpl implements SpellRepository {

    private static final Logger logger = LoggerFactory.getLogger(SpellRepositoryImpl.class);

    @Override
    public List<Spell> findAll() {
        List<Spell> spells = new ArrayList<>();
        String sql = "SELECT * FROM spells";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Spell spell = mapResultSetToSpell(resultSet);
                spells.add(spell);
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve spells from the database", e);
            throw new RuntimeException("Failed to retrieve spells from the database", e);
        }
        return spells;
    }

    @Override
    public Optional<Spell> findById(Long id) {
        String sql = "SELECT * FROM spells WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToSpell(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve spell with id " + id, e);
            throw new RuntimeException("Failed to retrieve spell with id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Spell spell) {
        String sql = "INSERT INTO spells (name, school, circle, description) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, spell.getName());
            statement.setString(2, spell.getSchool().name());
            statement.setString(3, spell.getCircle().name());
            statement.setString(4, spell.getDescription());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating spell failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    spell.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating spell failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to save spell", e);
            throw new RuntimeException("Failed to save spell", e);
        }
    }

    @Override
    public void update(Spell spell) {
        String sql = "UPDATE spells SET name = ?, school = ?, circle = ?, description = ? WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, spell.getName());
            statement.setString(2, spell.getSchool().name());
            statement.setString(3, spell.getCircle().name());
            statement.setString(4, spell.getDescription());
            statement.setLong(5, spell.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to update spell with id " + spell.getId(), e);
            throw new RuntimeException("Failed to update spell with id " + spell.getId(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM spells WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to delete spell with id " + id, e);
            throw new RuntimeException("Failed to delete spell with id " + id, e);
        }
    }

    @Override
    public List<Spell> findByCasterClassAndCircle(CasterClass casterClass, SpellCircle circle) {
        List<Spell> spells = new ArrayList<>();
        String sql = "SELECT s.* FROM spells s JOIN spell_caster_classes sc ON s.id = sc.spell_id WHERE sc.caster_class = ? AND s.circle = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, casterClass.name());
            statement.setString(2, circle.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Spell spell = mapResultSetToSpell(resultSet);
                    spells.add(spell);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve spells by caster class and circle", e);
            throw new RuntimeException("Failed to retrieve spells by caster class and circle", e);
        }
        return spells;
    }

    @Override
    public Optional<Spell> findByName(String name) {
        String sql = "SELECT * FROM spells WHERE name = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToSpell(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve spell by name", e);
            throw new RuntimeException("Failed to retrieve spell by name", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Character> findCharactersBySpellName(String spellName) {
        List<Character> characters = new ArrayList<>();
        String sql = "SELECT c.* FROM characters c " +
                "JOIN character_spells cs ON c.id = cs.character_id " +
                "JOIN spells s ON cs.spell_id = s.id " +
                "WHERE s.name = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, spellName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Character character = mapResultSetToCharacter(resultSet);
                    characters.add(character);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve characters by spell name", e);
            throw new RuntimeException("Failed to retrieve characters by spell name", e);
        }
        return characters;
    }

    private Spell mapResultSetToSpell(ResultSet resultSet) throws SQLException {
        Spell spell = new Spell();
        spell.setId(resultSet.getLong("id"));
        spell.setName(resultSet.getString("name"));
        spell.setSchool(SchoolOfMagic.valueOf(resultSet.getString("school")));
        spell.setCircle(SpellCircle.valueOf(resultSet.getString("circle")));
        spell.setDescription(resultSet.getString("description"));
        spell.setCasterClasses(getCasterClassesForSpell(spell.getId()));
        return spell;
    }

    private Set<CasterClass> getCasterClassesForSpell(Long spellId) {
        Set<CasterClass> casterClasses = new HashSet<>();
        String sql = "SELECT caster_class FROM spell_caster_classes WHERE spell_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, spellId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    casterClasses.add(CasterClass.valueOf(resultSet.getString("caster_class")));
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve caster classes for spell with id " + spellId, e);
            throw new RuntimeException("Failed to retrieve caster classes for spell with id " + spellId, e);
        }
        return casterClasses;
    }

    private Character mapResultSetToCharacter(ResultSet resultSet) throws SQLException {
        Character character = new Character();
        character.setId(resultSet.getLong("id"));
        character.setName(resultSet.getString("name"));
        character.setCasterClass(CasterClass.valueOf(resultSet.getString("caster_class")));
        character.setLevel(resultSet.getInt("level"));
        return character;
    }
}
