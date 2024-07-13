package org.examle.aston_practice.spellbook.repository.impl;

import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.enums.SpellSchool;
import org.examle.aston_practice.spellbook.repository.SpellRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of SpellRepository
 */
public class SpellRepositoryImpl implements SpellRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/spellbook";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    @Override
    public List<Spell> findAll() {
        List<Spell> spells = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM spells")) {
            while (resultSet.next()) {
                Spell spell = mapResultSetToSpell(resultSet);
                spells.add(spell);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spells;
    }

    @Override
    public Optional<Spell> findById(Long id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM spells WHERE id = ?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToSpell(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Spell spell) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO spells (name, description, school, circle, spell_class) VALUES (?, ?, ?, ?, ?)")) {
            statement.setString(1, spell.getName());
            statement.setString(2, spell.getDescription());
            statement.setString(3, spell.getSchool().name());
            statement.setString(4, spell.getCircle().name());
            statement.setString(5, spell.getSpellClass());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Spell spell) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE spells SET name = ?, description = ?, school = ?, circle = ?, spell_class = ? WHERE id = ?")) {
            statement.setString(1, spell.getName());
            statement.setString(2, spell.getDescription());
            statement.setString(3, spell.getSchool().name());
            statement.setString(4, spell.getCircle().name());
            statement.setString(5, spell.getSpellClass());
            statement.setLong(6, spell.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM spells WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Spell> findByClassAndCircle(String spellClass, String circle) {
        List<Spell> spells = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM spells WHERE spell_class = ? AND circle = ?")) {
            statement.setString(1, spellClass);
            statement.setString(2, circle);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Spell spell = mapResultSetToSpell(resultSet);
                    spells.add(spell);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spells;
    }

    private Spell mapResultSetToSpell(ResultSet resultSet) throws SQLException {
        Spell spell = new Spell();
        spell.setId(resultSet.getLong("id"));
        spell.setName(resultSet.getString("name"));
        spell.setDescription(resultSet.getString("description"));
        spell.setSchool(SpellSchool.valueOf(resultSet.getString("school")));
        spell.setCircle(SpellCircle.valueOf(resultSet.getString("circle")));
        spell.setSpellClass(resultSet.getString("spell_class"));
        return spell;
    }
}
