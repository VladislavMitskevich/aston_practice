package org.examle.aston_practice;

import org.examle.aston_practice.list.CustomLinkedList;

public class CustomLinkedListMain {
    public static void main(String[] args) {
        testCustomLinkedListWithStrings();
        testCustomLinkedListWithChars();
        testCustomLinkedListWithIntegers();
    }

    private static void testCustomLinkedListWithStrings() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
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

    private static void testCustomLinkedListWithChars() {
        CustomLinkedList<Character> list = new CustomLinkedList<>();
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

    private static void testCustomLinkedListWithIntegers() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
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
