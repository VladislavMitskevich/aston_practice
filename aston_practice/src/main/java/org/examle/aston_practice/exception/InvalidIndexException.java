package org.examle.aston_practice.exception;

/**
 * Custom exception for invalid index access in the list.
 */
public class InvalidIndexException extends RuntimeException {
    public InvalidIndexException(String message) {
        super(message);
    }
}
