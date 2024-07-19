package org.examle.aston_practice.spellbook.repository.impl;

import org.examle.aston_practice.spellbook.entity.Character;
import org.examle.aston_practice.spellbook.entity.Spell;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.enums.SchoolOfMagic;
import org.examle.aston_practice.spellbook.repository.CharacterRepository;
import org.examle.aston_practice.spellbook.util.DatabaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the CharacterRepository interface that provides CRUD operations
 * for Character entities using a relational database.
 */
public class CharacterRepositoryImpl implements CharacterRepository {

    private static final Logger logger = LoggerFactory.getLogger(CharacterRepositoryImpl.class);

    /**
     * Fetches all characters from the database.
     *
     * @return a list of all characters
     */
    @Override
    public List<Character> findAll() {
        logger.info("Fetching all characters from the database");
        List<Character> characters = new ArrayList<>();
        String sql = "SELECT * FROM characters";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                characters.add(mapResultSetToCharacter(resultSet));
            }
            logger.info("Retrieved {} characters", characters.size());
        } catch (SQLException e) {
            logger.error("Failed to retrieve characters from the database", e);
            throw new RuntimeException("Failed to retrieve characters from the database", e);
        }
        return characters;
    }

    /**
     * Fetches a character by its ID from the database.
     *
     * @param id the ID of the character to fetch
     * @return an Optional containing the character if found, otherwise empty
     */
    @Override
    public Optional<Character> findById(Long id) {
        logger.info("Fetching character by ID: {}", id);
        String sql = "SELECT * FROM characters WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Character character = mapResultSetToCharacter(resultSet);
                    logger.info("Found character: {}", character);
                    return Optional.of(character);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve character with id {}", id, e);
            throw new RuntimeException("Failed to retrieve character with id " + id, e);
        }
        logger.info("Character with ID {} not found", id);
        return Optional.empty();
    }

    /**
     * Fetches a character by its name from the database.
     *
     * @param name the name of the character to fetch
     * @return an Optional containing the character if found, otherwise empty
     */
    @Override
    public Optional<Character> findByName(String name) {
        logger.info("Fetching character by name: {}", name);
        String sql = "SELECT * FROM characters WHERE name = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Character character = mapResultSetToCharacter(resultSet);
                    logger.info("Found character: {}", character);
                    return Optional.of(character);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve character with name {}", name, e);
            throw new RuntimeException("Failed to retrieve character with name " + name, e);
        }
        logger.info("Character with name {} not found", name);
        return Optional.empty();
    }

    /**
     * Saves a new character to the database.
     *
     * @param character the character to save
     */
    @Override
    public void save(Character character) {
        if (character == null) {
            logger.error("Character cannot be null");
            throw new IllegalArgumentException("Character cannot be null");
        }
        logger.info("Saving character: {}", character);
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
                    logger.info("Character saved with ID: {}", character.getId());
                } else {
                    throw new SQLException("Creating character failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to save character {}", character, e);
            throw new RuntimeException("Failed to save character", e);
        }
    }

    /**
     * Updates an existing character in the database.
     *
     * @param character the character to update
     */
    @Override
    public void update(Character character) {
        if (character == null) {
            logger.error("Character cannot be null");
            throw new IllegalArgumentException("Character cannot be null");
        }
        logger.info("Updating character: {}", character);
        String sql = "UPDATE characters SET name = ?, caster_class = ?, level = ? WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, character.getName());
            statement.setString(2, character.getCasterClass().name());
            statement.setInt(3, character.getLevel());
            statement.setLong(4, character.getId());
            statement.executeUpdate();
            logger.info("Character with ID {} updated", character.getId());
        } catch (SQLException e) {
            logger.error("Failed to update character with id {}", character.getId(), e);
            throw new RuntimeException("Failed to update character with id " + character.getId(), e);
        }
    }

    /**
     * Deletes a character by its ID from the database.
     *
     * @param id the ID of the character to delete
     */
    @Override
    public void deleteById(Long id) {
        if (id == null) {
            logger.error("ID cannot be null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        logger.info("Deleting character by ID: {}", id);
        String sql = "DELETE FROM characters WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            logger.info("Character with ID {} deleted", id);
        } catch (SQLException e) {
            logger.error("Failed to delete character with id {}", id, e);
            throw new RuntimeException("Failed to delete character with id " + id, e);
        }
    }

    /**
     * Fetches characters by their caster class from the database.
     *
     * @param casterClass the caster class to filter by
     * @return a list of characters with the specified caster class
     */
    @Override
    public List<Character> findByCasterClass(CasterClass casterClass) {
        if (casterClass == null) {
            logger.error("Caster class cannot be null");
            throw new IllegalArgumentException("Caster class cannot be null");
        }
        logger.info("Fetching characters by caster class: {}", casterClass);
        List<Character> characters = new ArrayList<>();
        String sql = "SELECT * FROM characters WHERE caster_class = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, casterClass.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    characters.add(mapResultSetToCharacter(resultSet));
                }
            }
            logger.info("Retrieved {} characters for caster class {}", characters.size(), casterClass);
        } catch (SQLException e) {
            logger.error("Failed to retrieve characters by caster class {}", casterClass, e);
            throw new RuntimeException("Failed to retrieve characters by caster class " + casterClass, e);
        }
        return characters;
    }

    /**
     * Adds a spell to a character's spell list in the database.
     *
     * @param characterId the ID of the character
     * @param spellId     the ID of the spell
     */
    @Override
    public void addSpellToCharacter(Long characterId, Long spellId) {
        if (characterId == null || spellId == null) {
            logger.error("Character ID and Spell ID cannot be null");
            throw new IllegalArgumentException("Character ID and Spell ID cannot be null");
        }
        logger.info("Adding spell with ID {} to character with ID {}", spellId, characterId);
        String sql = "INSERT INTO character_spells (character_id, spell_id) VALUES (?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, characterId);
            statement.setLong(2, spellId);
            statement.executeUpdate();
            logger.info("Spell with ID {} added to character with ID {}", spellId, characterId);
        } catch (SQLException e) {
            logger.error("Failed to add spell with id {} to character with id {}", spellId, characterId, e);
            throw new RuntimeException("Failed to add spell to character", e);
        }
    }

    /**
     * Fetches characters by a spell name from the database.
     *
     * @param spellName the name of the spell to filter by
     * @return a list of characters who have the specified spell
     */
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
                    characters.add(mapResultSetToCharacter(resultSet));
                }
            }
            logger.info("Retrieved {} characters for spell name {}", characters.size(), spellName);
        } catch (SQLException e) {
            logger.error("Failed to retrieve characters by spell name {}", spellName, e);
            throw new RuntimeException("Failed to retrieve characters by spell name " + spellName, e);
        }
        return characters;
    }

    /**
     * Fetches spells by a character name from the database.
     *
     * @param name the name of the character to filter by
     * @return a list of spells that the character has
     */
    @Override
    public List<Spell> findSpellsByCharacterName(String name) {
        if (name == null) {
            logger.error("Character name cannot be null");
            throw new IllegalArgumentException("Character name cannot be null");
        }
        logger.info("Fetching spells by character name: {}", name);
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
                    spells.add(mapResultSetToSpell(resultSet));
                }
            }
            logger.info("Retrieved {} spells for character name {}", spells.size(), name);
        } catch (SQLException e) {
            logger.error("Failed to retrieve spells by character name {}", name, e);
            throw new RuntimeException("Failed to retrieve spells by character name " + name, e);
        }
        return spells;
    }

    /**
     * Fetches spells by caster class and spell circle from the database.
     *
     * @param casterClass the caster class to filter by
     * @param spellCircle the spell circle to filter by
     * @return a list of spells that match the specified caster class and spell circle
     */
    @Override
    public List<Spell> findSpellsByCasterClassAndSpellCircle(CasterClass casterClass, SpellCircle spellCircle) {
        if (casterClass == null || spellCircle == null) {
            logger.error("Caster class and Spell circle cannot be null");
            throw new IllegalArgumentException("Caster class and Spell circle cannot be null");
        }
        logger.info("Fetching spells by caster class {} and spell circle {}", casterClass, spellCircle);
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
                    spells.add(mapResultSetToSpell(resultSet));
                }
            }
            logger.info("Retrieved {} spells for caster class {} and spell circle {}", spells.size(), casterClass, spellCircle);
        } catch (SQLException e) {
            logger.error("Failed to retrieve spells by caster class {} and spell circle {}", casterClass, spellCircle, e);
            throw new RuntimeException("Failed to retrieve spells by caster class and spell circle", e);
        }
        return spells;
    }

    /**
     * Adds a new spell to a character's spell list in the database.
     *
     * @param characterId the ID of the character
     * @param spell       the spell to add
     */
    @Override
    public void addNewSpellToCharacter(Long characterId, Spell spell) {
        if (characterId == null || spell == null) {
            logger.error("Character ID and Spell cannot be null");
            throw new IllegalArgumentException("Character ID and Spell cannot be null");
        }
        logger.info("Adding new spell {} to character with ID {}", spell, characterId);
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
                    logger.info("New spell with ID {} added to character with ID {}", spellId, characterId);
                } else {
                    throw new SQLException("Creating spell failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to add new spell {} to character with id {}", spell, characterId, e);
            throw new RuntimeException("Failed to add new spell to character", e);
        }
    }

    /**
     * Maps a ResultSet to a Character object.
     *
     * @param resultSet the ResultSet to map
     * @return the mapped Character object
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

    /**
     * Maps a ResultSet to a Spell object.
     *
     * @param resultSet the ResultSet to map
     * @return the mapped Spell object
     * @throws SQLException if a database access error occurs
     */
    private Spell mapResultSetToSpell(ResultSet resultSet) throws SQLException {
        Spell spell = new Spell();
        spell.setId(resultSet.getLong("id"));
        spell.setName(resultSet.getString("name"));
        spell.setSchool(SchoolOfMagic.valueOf(resultSet.getString("school")));
        spell.setCircle(SpellCircle.valueOf(resultSet.getString("circle")));
        spell.setDescription(resultSet.getString("description"));
        return spell;
    }
}
