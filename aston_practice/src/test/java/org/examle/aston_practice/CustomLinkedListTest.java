package org.examle.aston_practice;

import org.examle.aston_practice.list.CustomLinkedList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for testing CustomLinkedList functionality.
 * This class contains multiple test methods to validate the behavior
 * of CustomLinkedList for different operations such as adding elements,
 * retrieving elements, removing elements, clearing the list.
 */
public class CustomLinkedListTest {

    /**
     * Test case for CustomLinkedList with String elements.
     * Tests add(), get(), remove(), and clear() methods.
     */
    @Test
    void testCustomLinkedListWithStrings() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        list.add(1, "date");

        assertEquals("[apple, date, banana, cherry]", toString(list));

        assertEquals("banana", list.get(2));

        list.remove(2);
        assertEquals("[apple, date, cherry]", toString(list));

        list.clear();
        assertEquals("[]", toString(list));
    }

    /**
     * Test case for CustomLinkedList with Character elements.
     * Tests add(), get(), remove(), and clear() methods.
     */
    @Test
    void testCustomLinkedListWithChars() {
        CustomLinkedList<Character> list = new CustomLinkedList<>();
        list.add('a');
        list.add('c');
        list.add('b');
        list.add(1, 'd');

        assertEquals("[a, d, c, b]", toString(list));

        assertEquals('c', list.get(2));

        list.remove(2);
        assertEquals("[a, d, b]", toString(list));

        list.clear();
        assertEquals("[]", toString(list));
    }

    /**
     * Test case for CustomLinkedList with Integer elements.
     * Tests add(), get(), remove(), and clear() methods.
     */
    @Test
    void testCustomLinkedListWithIntegers() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        list.add(3);
        list.add(1);
        list.add(2);
        list.add(1, 4);

        assertEquals("[3, 4, 1, 2]", toString(list));

        assertEquals(1, list.get(2));

        list.remove(2);
        assertEquals("[3, 4, 2]", toString(list));

        list.clear();
        assertEquals("[]", toString(list));
    }

    /**
     * Helper method to convert CustomLinkedList to String representation.
     * @param list The CustomLinkedList to convert.
     * @param <T> Type parameter of the CustomLinkedList.
     * @return String representation of the CustomLinkedList.
     */
    private <T extends Comparable<T>> String toString(CustomLinkedList<T> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
