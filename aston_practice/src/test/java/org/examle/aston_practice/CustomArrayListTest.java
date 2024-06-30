package org.examle.aston_practice;

import org.examle.aston_practice.list.CustomArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for testing CustomArrayList functionality.
 * This class contains multiple test methods to validate the behavior
 * of CustomArrayList for different operations such as adding elements,
 * retrieving elements, removing elements, clearing the list, and sorting.
 */
public class CustomArrayListTest {

    /**
     * Test case for adding elements to CustomArrayList and retrieving them.
     * Tests the add() and get() methods.
     */
    @Test
    public void testAddAndGet() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("apple");
        list.add("banana");
        assertEquals("apple", list.get(0));
        assertEquals("banana", list.get(1));
    }

    /**
     * Test case for adding elements to CustomArrayList by index and retrieving them.
     * Tests the add(int index, T element) and get() methods.
     */
    @Test
    public void testAddByIndex() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("apple");
        list.add(0, "banana");
        assertEquals("banana", list.get(0));
        assertEquals("apple", list.get(1));
    }

    /**
     * Test case for removing elements from CustomArrayList.
     * Tests the remove(int index) method.
     */
    @Test
    public void testRemove() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("apple");
        list.add("banana");
        list.remove(0);
        assertEquals("banana", list.get(0));
    }

    /**
     * Test case for clearing the CustomArrayList.
     * Tests the clear() method.
     */
    @Test
    public void testClear() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("apple");
        list.add("banana");
        list.clear();
        assertEquals(0, list.size());
    }

    /**
     * Test case for sorting elements in CustomArrayList.
     * Tests the sort() method.
     */
    @Test
    public void testSort() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);
        list.sort();
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }
}
