package org.examle.aston_practice.spellbook.exception;

public class CharacterNotFoundException extends RuntimeException {
    public CharacterNotFoundException(String message) {
        super(message);
    }

    public CharacterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
