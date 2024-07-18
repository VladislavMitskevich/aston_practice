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
        logger.info("Fetching all spells from the database");
        List<Spell> spells = new ArrayList<>();
        String sql = "SELECT * FROM spells";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Spell spell = mapResultSetToSpell(resultSet);
                spells.add(spell);
            }
            logger.info("Retrieved {} spells", spells.size());
        } catch (SQLException e) {
            logger.error("Failed to retrieve spells from the database", e);
            throw new RuntimeException("Failed to retrieve spells from the database", e);
        }
        return spells;
    }

    @Override
    public Optional<Spell> findById(Long id) {
        if (id == null) {
            logger.error("Spell ID cannot be null");
            throw new IllegalArgumentException("Spell ID cannot be null");
        }
        logger.info("Fetching spell by ID: {}", id);
        String sql = "SELECT * FROM spells WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Spell spell = mapResultSetToSpell(resultSet);
                    logger.info("Found spell: {}", spell);
                    return Optional.of(spell);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve spell with id {}", id, e);
            throw new RuntimeException("Failed to retrieve spell with id " + id, e);
        }
        logger.info("Spell with ID {} not found", id);
        return Optional.empty();
    }

    @Override
    public void save(Spell spell) {
        if (spell == null) {
            logger.error("Spell cannot be null");
            throw new IllegalArgumentException("Spell cannot be null");
        }
        logger.info("Saving spell: {}", spell);
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
                    saveCasterClassesForSpell(spell.getId(), spell.getCasterClasses());
                    logger.info("Spell saved with ID: {}", spell.getId());
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
        if (spell == null) {
            logger.error("Spell cannot be null");
            throw new IllegalArgumentException("Spell cannot be null");
        }
        logger.info("Updating spell: {}", spell);
        String sql = "UPDATE spells SET name = ?, school = ?, circle = ?, description = ? WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, spell.getName());
            statement.setString(2, spell.getSchool().name());
            statement.setString(3, spell.getCircle().name());
            statement.setString(4, spell.getDescription());
            statement.setLong(5, spell.getId());
            statement.executeUpdate();
            updateCasterClassesForSpell(spell.getId(), spell.getCasterClasses());
            logger.info("Spell with ID {} updated", spell.getId());
        } catch (SQLException e) {
            logger.error("Failed to update spell with id {}", spell.getId(), e);
            throw new RuntimeException("Failed to update spell with id " + spell.getId(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            logger.error("Spell ID cannot be null");
            throw new IllegalArgumentException("Spell ID cannot be null");
        }
        logger.info("Deleting spell by ID: {}", id);
        String sql = "DELETE FROM spells WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            deleteCasterClassesForSpell(id);
            logger.info("Spell with ID {} deleted", id);
        } catch (SQLException e) {
            logger.error("Failed to delete spell with id {}", id, e);
            throw new RuntimeException("Failed to delete spell with id " + id, e);
        }
    }

    @Override
    public List<Spell> findByCasterClassAndCircle(CasterClass casterClass, SpellCircle circle) {
        if (casterClass == null || circle == null) {
            logger.error("Caster class and circle cannot be null");
            throw new IllegalArgumentException("Caster class and circle cannot be null");
        }
        logger.info("Fetching spells by caster class {} and circle {}", casterClass, circle);
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
            logger.info("Retrieved {} spells for caster class {} and circle {}", spells.size(), casterClass, circle);
        } catch (SQLException e) {
            logger.error("Failed to retrieve spells by caster class {} and circle {}", casterClass, circle, e);
            throw new RuntimeException("Failed to retrieve spells by caster class and circle", e);
        }
        return spells;
    }

    @Override
    public Optional<Spell> findByName(String name) {
        if (name == null) {
            logger.error("Spell name cannot be null");
            throw new IllegalArgumentException("Spell name cannot be null");
        }
        logger.info("Fetching spell by name: {}", name);
        String sql = "SELECT * FROM spells WHERE name = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Spell spell = mapResultSetToSpell(resultSet);
                    logger.info("Found spell: {}", spell);
                    return Optional.of(spell);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve spell by name {}", name, e);
            throw new RuntimeException("Failed to retrieve spell by name", e);
        }
        logger.info("Spell with name {} not found", name);
        return Optional.empty();
    }

    @Override
    public List<Character> findCharactersBySpellName(String spellName) {
        if (spellName == null) {
            logger.error("Spell name cannot be null");
            throw new IllegalArgumentException("Spell name cannot be null");
        }
        logger.info("Fetching characters by spell name: {}", spellName);
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
            logger.info("Retrieved {} characters for spell name {}", characters.size(), spellName);
        } catch (SQLException e) {
            logger.error("Failed to retrieve characters by spell name {}", spellName, e);
            throw new RuntimeException("Failed to retrieve characters by spell name", e);
        }
        return characters;
    }

    /**
     * Maps a ResultSet to a Spell object.
     *
     * @param resultSet The ResultSet containing the spell data.
     * @return A Spell object populated with data from the ResultSet.
     * @throws SQLException if an error occurs while accessing the data in the ResultSet.
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
     * Retrieves the set of CasterClass values associated with a given spell ID.
     *
     * @param spellId The ID of the spell for which to retrieve the caster classes.
     * @return A Set of CasterClass values associated with the spell.
     * @throws RuntimeException if an error occurs while retrieving the caster classes.
     */
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
            logger.error("Failed to retrieve caster classes for spell with id {}", spellId, e);
            throw new RuntimeException("Failed to retrieve caster classes for spell with id " + spellId, e);
        }
        return casterClasses;
    }

    /**
     * Saves the caster classes for a given spell ID.
     *
     * @param spellId The ID of the spell for which to save the caster classes.
     * @param casterClasses The Set of CasterClass values to save.
     * @throws RuntimeException if an error occurs while saving the caster classes.
     */
    private void saveCasterClassesForSpell(Long spellId, Set<CasterClass> casterClasses) {
        String sql = "INSERT INTO spell_caster_classes (spell_id, caster_class) VALUES (?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (CasterClass casterClass : casterClasses) {
                statement.setLong(1, spellId);
                statement.setString(2, casterClass.name());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            logger.error("Failed to save caster classes for spell with id {}", spellId, e);
            throw new RuntimeException("Failed to save caster classes for spell with id " + spellId, e);
        }
    }

    /**
     * Updates the caster classes for a given spell ID.
     *
     * @param spellId The ID of the spell for which to update the caster classes.
     * @param casterClasses The Set of CasterClass values to update.
     * @throws RuntimeException if an error occurs while updating the caster classes.
     */
    private void updateCasterClassesForSpell(Long spellId, Set<CasterClass> casterClasses) {
        deleteCasterClassesForSpell(spellId);
        saveCasterClassesForSpell(spellId, casterClasses);
    }

    /**
     * Deletes the caster classes for a given spell ID.
     *
     * @param spellId The ID of the spell for which to delete the caster classes.
     * @throws RuntimeException if an error occurs while deleting the caster classes.
     */
    private void deleteCasterClassesForSpell(Long spellId) {
        String sql = "DELETE FROM spell_caster_classes WHERE spell_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, spellId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to delete caster classes for spell with id {}", spellId, e);
            throw new RuntimeException("Failed to delete caster classes for spell with id " + spellId, e);
        }
    }

    /**
     * Maps a ResultSet to a Character object.
     *
     * @param resultSet The ResultSet containing the character data.
     * @return A Character object populated with data from the ResultSet.
     * @throws SQLException if an error occurs while accessing the data in the ResultSet.
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
