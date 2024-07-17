package org.examle.aston_practice.spellbook.repository.impl;

import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SchoolOfMagic;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.repository.CharacterRepository;
import org.examle.aston_practice.spellbook.util.DatabaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CharacterRepositoryImpl implements CharacterRepository {

    private static final Logger logger = LoggerFactory.getLogger(CharacterRepositoryImpl.class);

    @Override
    public List<Character> findAll() {
        List<Character> characters = new ArrayList<>();
        String sql = "SELECT * FROM characters";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Character character = mapResultSetToCharacter(resultSet);
                characters.add(character);
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve characters from the database", e);
            throw new RuntimeException("Failed to retrieve characters from the database", e);
        }
        return characters;
    }

    @Override
    public Optional<Character> findById(Long id) {
        String sql = "SELECT * FROM characters WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToCharacter(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve character with id " + id, e);
            throw new RuntimeException("Failed to retrieve character with id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Character character) {
        String sql = "INSERT INTO characters (name, caster_class, level) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, character.getName());
            statement.setString(2, character.getCasterClass().name());
            statement.setInt(3, character.getLevel());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating character failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    character.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating character failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to save character", e);
            throw new RuntimeException("Failed to save character", e);
        }
    }

    @Override
    public void update(Character character) {
        String sql = "UPDATE characters SET name = ?, caster_class = ?, level = ? WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, character.getName());
            statement.setString(2, character.getCasterClass().name());
            statement.setInt(3, character.getLevel());
            statement.setLong(4, character.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to update character with id " + character.getId(), e);
            throw new RuntimeException("Failed to update character with id " + character.getId(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM characters WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to delete character with id " + id, e);
            throw new RuntimeException("Failed to delete character with id " + id, e);
        }
    }

    @Override
    public List<Character> findByCasterClass(CasterClass casterClass) {
        List<Character> characters = new ArrayList<>();
        String sql = "SELECT * FROM characters WHERE caster_class = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, casterClass.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Character character = mapResultSetToCharacter(resultSet);
                    characters.add(character);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve characters by caster class", e);
            throw new RuntimeException("Failed to retrieve characters by caster class", e);
        }
        return characters;
    }

    @Override
    public void addSpellToCharacter(Long characterId, Long spellId) {
        String sql = "INSERT INTO character_spells (character_id, spell_id) VALUES (?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, characterId);
            statement.setLong(2, spellId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to add spell to character", e);
            throw new RuntimeException("Failed to add spell to character", e);
        }
    }

    @Override
    public Optional<Character> findByName(String name) {
        String sql = "SELECT * FROM characters WHERE name = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToCharacter(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve character by name", e);
            throw new RuntimeException("Failed to retrieve character by name", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Character> findBySpellName(String spellName) {
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

    @Override
    public List<Spell> findSpellsByCharacterName(String name) {
        List<Spell> spells = new ArrayList<>();
        String sql = "SELECT s.* FROM spells s " +
                "JOIN character_spells cs ON s.id = cs.spell_id " +
                "JOIN characters c ON cs.character_id = c.id " +
                "WHERE c.name = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Spell spell = mapResultSetToSpell(resultSet);
                    spells.add(spell);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve spells by character name", e);
            throw new RuntimeException("Failed to retrieve spells by character name", e);
        }
        return spells;
    }

    @Override
    public List<Spell> findSpellsByCasterClassAndSpellCircle(CasterClass casterClass, SpellCircle spellCircle) {
        List<Spell> spells = new ArrayList<>();
        String sql = "SELECT s.* FROM spells s " +
                "JOIN spell_caster_classes scc ON s.id = scc.spell_id " +
                "WHERE scc.caster_class = ? AND s.circle = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, casterClass.name());
            statement.setString(2, spellCircle.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Spell spell = mapResultSetToSpell(resultSet);
                    spells.add(spell);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve spells by caster class and spell circle", e);
            throw new RuntimeException("Failed to retrieve spells by caster class and spell circle", e);
        }
        return spells;
    }

    @Override
    public void addNewSpellToCharacter(Long characterId, Spell spell) {
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
                    Long spellId = generatedKeys.getLong(1);
                    addSpellToCharacter(characterId, spellId);
                } else {
                    throw new SQLException("Creating spell failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to add new spell to character", e);
            throw new RuntimeException("Failed to add new spell to character", e);
        }
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
        spell.setSchool(SchoolOfMagic.valueOf(resultSet.getString("school"))); // Преобразование строки в enum
        spell.setCircle(SpellCircle.valueOf(resultSet.getString("circle")));
        spell.setDescription(resultSet.getString("description"));
        return spell;
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
