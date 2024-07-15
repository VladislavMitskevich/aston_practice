package org.examle.aston_practice.spellbook.repository.impl;

import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SchoolOfMagic;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.repository.SpellRepository;
import org.examle.aston_practice.spellbook.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM spells WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Spell spell = mapResultSetToSpell(resultSet);
                return Optional.of(spell);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Spell spell) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO spells (name, school, circle, description) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
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
            e.printStackTrace();
        }
    }

    @Override
    public void update(Spell spell) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE spells SET name = ?, school = ?, circle = ?, description = ? WHERE id = ?")) {
            statement.setString(1, spell.getName());
            statement.setString(2, spell.getSchool().name());
            statement.setString(3, spell.getCircle().name());
            statement.setString(4, spell.getDescription());
            statement.setLong(5, spell.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM spells WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Spell> findByCasterClassAndCircle(CasterClass casterClass, SpellCircle circle) {
        List<Spell> spells = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT s.* FROM spells s JOIN spell_caster_classes sc ON s.id = sc.spell_id WHERE sc.caster_class = ? AND s.circle = ?")) {
            statement.setString(1, casterClass.name());
            statement.setString(2, circle.name());
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

    /**
     * Maps a ResultSet to a Spell entity.
     * @param resultSet the ResultSet to map
     * @return the mapped Spell entity
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Retrieves the caster classes for a given spell ID.
     * @param spellId the ID of the spell
     * @return a set of caster classes that can cast the spell
     */
    private Set<CasterClass> getCasterClassesForSpell(Long spellId) {
        Set<CasterClass> casterClasses = new HashSet<>();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT caster_class FROM spell_caster_classes WHERE spell_id = ?")) {
            statement.setLong(1, spellId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                casterClasses.add(CasterClass.valueOf(resultSet.getString("caster_class")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return casterClasses;
    }
}
