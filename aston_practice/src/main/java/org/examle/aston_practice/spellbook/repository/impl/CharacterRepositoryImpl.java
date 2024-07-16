package org.examle.aston_practice.spellbook.repository.impl;

import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.repository.CharacterRepository;
import org.examle.aston_practice.spellbook.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of Character repository.
 * This class provides the implementation for the CRUD operations on Character entities.
 */
public class CharacterRepositoryImpl implements CharacterRepository {

    @Override
    public List<Character> findAll() {
        List<Character> characters = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM characters")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Character character = mapResultSetToCharacter(resultSet);
                characters.add(character);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return characters;
    }

    @Override
    public Optional<Character> findById(Long id) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM characters WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Character character = mapResultSetToCharacter(resultSet);
                return Optional.of(character);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Character character) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO characters (name, caster_class, level) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
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
            e.printStackTrace();
        }
    }

    @Override
    public void update(Character character) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE characters SET name = ?, caster_class = ?, level = ? WHERE id = ?")) {
            statement.setString(1, character.getName());
            statement.setString(2, character.getCasterClass().name());
            statement.setInt(3, character.getLevel());
            statement.setLong(4, character.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM characters WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Character> findByCasterClass(CasterClass casterClass) {
        List<Character> characters = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM characters WHERE caster_class = ?")) {
            statement.setString(1, casterClass.name());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Character character = mapResultSetToCharacter(resultSet);
                characters.add(character);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return characters;
    }

    @Override
    public void addSpellToCharacter(Long characterId, Long spellId) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO character_spells (character_id, spell_id) VALUES (?, ?)")) {
            statement.setLong(1, characterId);
            statement.setLong(2, spellId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Character> findByName(String name) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM characters WHERE name = ?")) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Character character = mapResultSetToCharacter(resultSet);
                return Optional.of(character);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Character> findBySpellName(String spellName) {
        List<Character> characters = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT c.* FROM characters c " +
                             "JOIN character_spells cs ON c.id = cs.character_id " +
                             "JOIN spells s ON cs.spell_id = s.id " +
                             "WHERE s.name = ?")) {
            statement.setString(1, spellName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Character character = mapResultSetToCharacter(resultSet);
                characters.add(character);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return characters;
    }

    /**
     * Maps a ResultSet to a Character entity.
     * @param resultSet the ResultSet to map
     * @return the mapped Character entity
     * @throws SQLException if a database access error occurs
     */
    private Character mapResultSetToCharacter(ResultSet resultSet) throws SQLException {
        Character character = new Character();
        character.setId(resultSet.getLong("id"));
        character.setName(resultSet.getString("name"));
        character.setCasterClass(CasterClass.valueOf(resultSet.getString("caster_class")));
        character.setLevel(resultSet.getInt("level"));
        return character;
    }
}
