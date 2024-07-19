package org.examle.aston_practice.spellbook.exception;

/**
 * Exception thrown when a spell is not found.
 */
public class SpellNotFoundException extends RuntimeException {
    public SpellNotFoundException(String message) {
        super(message);
    }

    public SpellNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
