package org.examle.aston_practice;
import org.examle.aston_practice.list.CustomArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomArrayListTest {

    @Test
    public void testAddAndGet() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("apple");
        list.add("banana");
        assertEquals("apple", list.get(0));
        assertEquals("banana", list.get(1));
    }

    @Test
    public void testAddByIndex() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("apple");
        list.add(0, "banana");
        assertEquals("banana", list.get(0));
        assertEquals("apple", list.get(1));
    }

    @Test
    public void testRemove() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("apple");
        list.add("banana");
        list.remove(0);
        assertEquals("banana", list.get(0));
    }

    @Test
    public void testClear() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("apple");
        list.add("banana");
        list.clear();
        assertEquals(0, list.size());
    }

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
