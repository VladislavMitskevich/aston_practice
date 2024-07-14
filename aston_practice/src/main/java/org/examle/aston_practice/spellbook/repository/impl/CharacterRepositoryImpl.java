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

/**
 * Implementation of Character repository
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
    public Character findById(Long id) {
        Character character = null;
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM characters WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                character = mapResultSetToCharacter(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return character;
    }

    @Override
    public void save(Character character) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO characters (name, spell_limits) VALUES (?, ?)")) {
            statement.setString(1, character.getName());
            statement.setString(2, mapSpellLimitsToString(character.getSpellLimits()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Character character) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE characters SET name = ?, spell_limits = ? WHERE id = ?")) {
            statement.setString(1, character.getName());
            statement.setString(2, mapSpellLimitsToString(character.getSpellLimits()));
            statement.setLong(3, character.getId());
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

    private Character mapResultSetToCharacter(ResultSet resultSet) throws SQLException {
        Character character = new Character();
        character.setId(resultSet.getLong("id"));
        character.setName(resultSet.getString("name"));
        character.setSpellLimits(mapStringToSpellLimits(resultSet.getString("spell_limits")));
        return character;
    }

    private String mapSpellLimitsToString(Map<CasterClass, Integer> spellLimits) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<CasterClass, Integer> entry : spellLimits.entrySet()) {
            sb.append(entry.getKey().name()).append(":").append(entry.getValue()).append(",");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); // Remove the last comma
        }
        return sb.toString();
    }

    private Map<CasterClass, Integer> mapStringToSpellLimits(String spellLimits) {
        Map<CasterClass, Integer> map = new HashMap<>();
        String[] pairs = spellLimits.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            map.put(CasterClass.valueOf(keyValue[0]), Integer.parseInt(keyValue[1]));
        }
        return map;
    }
}
