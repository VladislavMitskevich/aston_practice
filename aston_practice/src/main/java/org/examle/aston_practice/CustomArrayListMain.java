package org.examle.aston_practice;

import org.examle.aston_practice.list.CustomArrayList;

/**
 * Main class to demonstrate and test the functionality of CustomArrayList with different types of elements.
 * This class provides methods to perform operations such as adding, retrieving, removing, and clearing elements
 * from CustomArrayList instances containing String, Character, and Integer elements.
 */
public class CustomArrayListMain {

    /**
     * The main method is the entry point of the application, executing test cases for CustomArrayList.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        testCustomArrayListWithStrings();
        testCustomArrayListWithChars();
        testCustomArrayListWithIntegers();
    }

    /**
     * Test case for CustomArrayList with String elements.
     * Demonstrates adding elements, retrieving elements by index, removing elements,
     * and clearing the list.
     */
    private static void testCustomArrayListWithStrings() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        list.add(1, "date");

        System.out.println("List after adding elements: " + toString(list));

        System.out.println("Element at index 2: " + list.get(2));

        list.remove(2);
        System.out.println("List after removing element at index 2: " + toString(list));

        list.clear();
        System.out.println("List after clearing: " + toString(list));
    }

    /**
     * Test case for CustomArrayList with Character elements.
     * Demonstrates adding elements, retrieving elements by index, removing elements,
     * and clearing the list.
     */
    private static void testCustomArrayListWithChars() {
        CustomArrayList<Character> list = new CustomArrayList<>();
        list.add('a');
        list.add('c');
        list.add('b');
        list.add(1, 'd');

        System.out.println("List after adding elements: " + toString(list));

        System.out.println("Element at index 2: " + list.get(2));

        list.remove(2);
        System.out.println("List after removing element at index 2: " + toString(list));

        list.clear();
        System.out.println("List after clearing: " + toString(list));
    }

    /**
     * Test case for CustomArrayList with Integer elements.
     * Demonstrates adding elements, retrieving elements by index, removing elements,
     * and clearing the list.
     */
    private static void testCustomArrayListWithIntegers() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);
        list.add(1, 4);

        System.out.println("List after adding elements: " + toString(list));

        System.out.println("Element at index 2: " + list.get(2));

        list.remove(2);
        System.out.println("List after removing element at index 2: " + toString(list));

        list.clear();
        System.out.println("List after clearing: " + toString(list));
    }

    /**
     * Utility method to convert a CustomArrayList instance to a String representation.
     *
     * @param list The CustomArrayList instance to convert.
     * @param <T>  The type of elements in the list.
     * @return A String representation of the CustomArrayList, showing all elements enclosed in square brackets.
     */
    private static <T extends Comparable<T>> String toString(CustomArrayList<T> list) {
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
