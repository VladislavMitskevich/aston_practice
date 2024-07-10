package org.examle.aston_practice.spellbook.repository;

import org.examle.aston_practice.spellbook.entity.Class;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Class entities.
 */
public interface ClassRepository {

    /**
     * Find all classes.
     *
     * @return List of all classes
     */
    List<Class> findAll();

    /**
     * Find a class by its ID.
     *
     * @param id ID of the class
     * @return Optional containing the class if found, otherwise empty
     */
    Optional<Class> findById(Long id);

    /**
     * Find classes by their names.
     *
     * @param classNames List of class names
     * @return List of classes with the specified names
     */
    List<Class> findByClassNames(List<String> classNames);

    /**
     * Save a new class.
     *
     * @param clazz Class to be saved
     */
    void save(Class clazz);

    /**
     * Update an existing class.
     *
     * @param clazz Class to be updated
     */
    void update(Class clazz);

    /**
     * Delete a class by its ID.
     *
     * @param id ID of the class to be deleted
     */
    void delete(Long id);
}