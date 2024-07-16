package org.examle.aston_practice.spellbook.exception;

public class SpellNotFoundException extends RuntimeException {
    public SpellNotFoundException(String message) {
        super(message);
    }
}
