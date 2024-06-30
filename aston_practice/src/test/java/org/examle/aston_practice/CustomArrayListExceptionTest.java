package org.examle.aston_practice;

import org.examle.aston_practice.exception.InvalidIndexException;
import org.examle.aston_practice.list.CustomArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CustomArrayList class, specifically testing exception handling.
 */
public class CustomArrayListExceptionTest {

    /**
     * Tests adding an element at an invalid index and expects an InvalidIndexException.
     */
    @Test
    public void testAddWithInvalidIndex() {
        CustomArrayList<String> list = new CustomArrayList<>();
        InvalidIndexException thrown = assertThrows(
                InvalidIndexException.class,
                () -> list.add(-1, "apple"),
                "Expected add() to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Index: -1, Size: 0"));

        thrown = assertThrows(
                InvalidIndexException.class,
                () -> list.add(1, "apple"),
                "Expected add() to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Index: 1, Size: 0"));
    }

    /**
     * Tests retrieving an element at an invalid index and expects an InvalidIndexException.
     */
    @Test
    public void testGetWithInvalidIndex() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("apple");

        InvalidIndexException thrown = assertThrows(
                InvalidIndexException.class,
                () -> list.get(-1),
                "Expected get() to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Index: -1, Size: 1"));

        thrown = assertThrows(
                InvalidIndexException.class,
                () -> list.get(1),
                "Expected get() to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Index: 1, Size: 1"));
    }

    /**
     * Tests removing an element at an invalid index and expects an InvalidIndexException.
     */
    @Test
    public void testRemoveWithInvalidIndex() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("apple");

        InvalidIndexException thrown = assertThrows(
                InvalidIndexException.class,
                () -> list.remove(-1),
                "Expected remove() to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Index: -1, Size: 1"));

        thrown = assertThrows(
                InvalidIndexException.class,
                () -> list.remove(1),
                "Expected remove() to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Index: 1, Size: 1"));
    }
}
