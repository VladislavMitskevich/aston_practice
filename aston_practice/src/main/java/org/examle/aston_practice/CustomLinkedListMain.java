package org.examle.aston_practice;

import org.examle.aston_practice.list.CustomLinkedList;

/**
 * Main class to demonstrate and test the functionality of CustomLinkedList with different types of elements.
 * This class provides methods to perform operations such as adding, retrieving, removing, and clearing elements
 * from CustomLinkedList instances containing String, Character, and Integer elements.
 */
public class CustomLinkedListMain {

    /**
     * The main method is the entry point of the application.
     * It demonstrates the use of CustomLinkedList with String, Character, and Integer elements
     * by executing test cases specific to each data type.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        testCustomLinkedListWithStrings();
        testCustomLinkedListWithChars();
        testCustomLinkedListWithIntegers();
    }

    /**
     * Test case for CustomLinkedList with String elements.
     * Demonstrates adding elements, retrieving elements by index, removing elements,
     * and clearing the list.
     */
    private static void testCustomLinkedListWithStrings() {
        CustomLinkedList<String> list = new CustomLinkedList<>();

        // Adding elements
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        list.add(1, "date");

        // Printing list after adding elements
        System.out.println("List after adding elements: " + toString(list));

        // Retrieving element at index 2
        System.out.println("Element at index 2: " + list.get(2));

        // Removing element at index 2
        list.remove(2);
        System.out.println("List after removing element at index 2: " + toString(list));

        // Clearing the list
        list.clear();
        System.out.println("List after clearing: " + toString(list));
    }

    /**
     * Test case for CustomLinkedList with Character elements.
     * Demonstrates adding elements, retrieving elements by index, removing elements,
     * and clearing the list.
     */
    private static void testCustomLinkedListWithChars() {
        CustomLinkedList<Character> list = new CustomLinkedList<>();

        // Adding elements
        list.add('a');
        list.add('c');
        list.add('b');
        list.add(1, 'd');

        // Printing list after adding elements
        System.out.println("List after adding elements: " + toString(list));

        // Retrieving element at index 2
        System.out.println("Element at index 2: " + list.get(2));

        // Removing element at index 2
        list.remove(2);
        System.out.println("List after removing element at index 2: " + toString(list));

        // Clearing the list
        list.clear();
        System.out.println("List after clearing: " + toString(list));
    }

    /**
     * Test case for CustomLinkedList with Integer elements.
     * Demonstrates adding elements, retrieving elements by index, removing elements,
     * and clearing the list.
     */
    private static void testCustomLinkedListWithIntegers() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();

        // Adding elements
        list.add(3);
        list.add(1);
        list.add(2);
        list.add(1, 4);

        // Printing list after adding elements
        System.out.println("List after adding elements: " + toString(list));

        // Retrieving element at index 2
        System.out.println("Element at index 2: " + list.get(2));

        // Removing element at index 2
        list.remove(2);
        System.out.println("List after removing element at index 2: " + toString(list));

        // Clearing the list
        list.clear();
        System.out.println("List after clearing: " + toString(list));
    }

    /**
     * Utility method to convert a CustomLinkedList instance to a String representation.
     * This method is used to print the contents of the CustomLinkedList.
     *
     * @param list The CustomLinkedList instance to convert.
     * @param <T>  The type of elements in the list.
     * @return A String representation of the CustomLinkedList, showing all elements enclosed in square brackets.
     */
    private static <T extends Comparable<T>> String toString(CustomLinkedList<T> list) {
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
