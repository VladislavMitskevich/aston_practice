package org.examle.aston_practice.spellbook.exception;

/**
 * Exception thrown when a character is not found.
 */
public class CharacterNotFoundException extends RuntimeException {
    public CharacterNotFoundException(String message) {
        super(message);
    }

    public CharacterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
