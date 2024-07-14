package org.examle.aston_practice.spellbook.repository.impl;

import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SchoolOfMagic;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.repository.SpellRepository;
import org.examle.aston_practice.spellbook.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of Spell repository
 */
public class SpellRepositoryImpl implements SpellRepository {

    @Override
    public List<Spell> findAll() {
        List<Spell> spells = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM spells")) {
            ResultSet resultSet = statement.executeQuery();
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
        Spell spell = null;
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM spells WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                spell = mapResultSetToSpell(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(spell);
    }

    @Override
    public void save(Spell spell) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO spells (name, school, circle, caster_class) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, spell.getName());
            statement.setString(2, spell.getSchool().name());
            statement.setString(3, spell.getCircle().name());
            statement.setString(4, spell.getCasterClass().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Spell spell) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE spells SET name = ?, school = ?, circle = ?, caster_class = ? WHERE id = ?")) {
            statement.setString(1, spell.getName());
            statement.setString(2, spell.getSchool().name());
            statement.setString(3, spell.getCircle().name());
            statement.setString(4, spell.getCasterClass().name());
            statement.setLong(5, spell.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = DatabaseUtil.getConnection();
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
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM spells WHERE caster_class = ? AND circle = ?")) {
            statement.setString(1, spellClass);
            statement.setString(2, circle);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Spell spell = mapResultSetToSpell(resultSet);
                spells.add(spell);
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
        spell.setSchool(SchoolOfMagic.valueOf(resultSet.getString("school")));
        spell.setCircle(SpellCircle.valueOf(resultSet.getString("circle")));
        spell.setCasterClass(CasterClass.valueOf(resultSet.getString("caster_class")));
        return spell;
    }
}
