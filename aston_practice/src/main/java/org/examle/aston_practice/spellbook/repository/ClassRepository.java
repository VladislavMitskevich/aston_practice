package org.examle.aston_practice.spellbook.repository;

import org.examle.aston_practice.spellbook.entity.Class;

import java.util.Optional;

/**
 * Repository interface for Class.
 */
public interface ClassRepository {
    Optional<Class> findById(Long id); // Find class by ID
    Optional<Class> findByName(String name); // Find class by name
}