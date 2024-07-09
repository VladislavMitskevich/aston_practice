package org.examle.aston_practice.spellbook.repository.impl;

import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.Circle;
import org.examle.aston_practice.spellbook.enums.School;
import org.examle.aston_practice.spellbook.repository.SpellRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the Spell repository using JDBC.
 */
public class SpellRepositoryImpl implements SpellRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/spellbook";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

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
    public Spell save(Spell spell) {
        String sql = "INSERT INTO spells (name, description, school, circle) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, spell.getName());
            statement.setString(2, spell.getDescription());
            statement.setString(3, spell.getSchool().name());
            statement.setString(4, spell.getCircle().name());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                spell.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spell;
    }

    @Override
    public void update(Spell spell) {
        String sql = "UPDATE spells SET name = ?, description = ?, school = ?, circle = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, spell.getName());
            statement.setString(2, spell.getDescription());
            statement.setString(3, spell.getSchool().name());
            statement.setString(4, spell.getCircle().name());
            statement.setLong(5, spell.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM spells WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Spell> findByCircle(Circle circle) {
        List<Spell> spells = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM spells WHERE circle = ?")) {
            statement.setString(1, circle.name());
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
    public List<Spell> findByClass(Long classId) {
        List<Spell> spells = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT s.* FROM spells s JOIN spell_class sc ON s.id = sc.spell_id WHERE sc.class_id = ?")) {
            statement.setLong(1, classId);
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
    public List<Spell> findBySchool(School school) {
        List<Spell> spells = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM spells WHERE school = ?")) {
            statement.setString(1, school.name());
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
        spell.setDescription(resultSet.getString("description"));
        spell.setSchool(School.valueOf(resultSet.getString("school")));
        spell.setCircle(Circle.valueOf(resultSet.getString("circle")));
        return spell;
    }
}
