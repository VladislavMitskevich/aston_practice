package org.examle.aston_practice.spellbook.repository.impl;

import org.examle.aston_practice.spellbook.entity.Class;
import org.examle.aston_practice.spellbook.enums.Circle;
import org.examle.aston_practice.spellbook.repository.ClassRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of ClassRepository interface.
 */

public class ClassRepositoryImpl implements ClassRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/spellbook";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public ClassRepositoryImpl() {
        try {
            java.lang.Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Class> findAll() {
        List<Class> classes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM classes")) {
            while (resultSet.next()) {
                Class clazz = mapResultSetToClass(resultSet);
                classes.add(clazz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    @Override
    public Optional<Class> findById(Long id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM classes WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Class clazz = mapResultSetToClass(resultSet);
                return Optional.of(clazz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Class> findByClassNames(List<String> classNames) {
        List<Class> classes = new ArrayList<>();
        String placeholders = String.join(",", classNames.stream().map(name -> "?").toArray(String[]::new));
        String query = "SELECT * FROM classes WHERE name IN (" + placeholders + ")";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < classNames.size(); i++) {
                statement.setString(i + 1, classNames.get(i));
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Class clazz = mapResultSetToClass(resultSet);
                classes.add(clazz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    @Override
    public void save(Class clazz) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO classes (name, circle) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, clazz.getName());
            statement.setString(2, clazz.getCircle().name());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                clazz.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Class clazz) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE classes SET name = ?, circle = ? WHERE id = ?")) {
            statement.setString(1, clazz.getName());
            statement.setString(2, clazz.getCircle().name());
            statement.setLong(3, clazz.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM classes WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Class mapResultSetToClass(ResultSet resultSet) throws SQLException {
        Class clazz = new Class();
        clazz.setId(resultSet.getLong("id"));
        clazz.setName(resultSet.getString("name"));
        clazz.setCircle(Circle.valueOf(resultSet.getString("circle")));
        return clazz;
    }
}
