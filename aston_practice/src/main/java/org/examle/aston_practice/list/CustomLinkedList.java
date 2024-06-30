package org.examle.aston_practice.list;

import org.examle.aston_practice.exception.InvalidIndexException;

/**
 * Implementation of a list based on a linked list.
 *
 * @param <T> Type of elements in the list.
 */
public class CustomLinkedList<T extends Comparable<T>> extends CustomAbstractList<T> {
    /**
     * Node of the linked list.
     */
    private static class Node<T> {
        T item;
        Node<T> next;

        Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }
    }

    /**
     * Head of the linked list.
     */
    private Node<T> head;

    /**
     * Adds an element to the end of the list.
     *
     * @param element Element to add.
     */
    @Override
    public void add(T element) {
        if (head == null) {
            head = new Node<>(element, null);
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node<>(element, null);
        }
        size++;
    }

    /**
     * Adds an element at the specified index in the list.
     *
     * @param index   Index at which the element should be added.
     * @param element Element to add.
     * @throws InvalidIndexException if the index is out of range.
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new InvalidIndexException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            head = new Node<>(element, head);
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = new Node<>(element, current.next);
        }
        size++;
    }

    /**
     * Retrieves the element at the specified index.
     *
     * @param index Index of the element.
     * @return Element at the specified index.
     * @throws InvalidIndexException if the index is out of range.
     */
    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    /**
     * Removes the element at the specified index.
     *
     * @param index Index of the element to remove.
     * @return Removed element.
     * @throws InvalidIndexException if the index is out of range.
     */
    @Override
    public T remove(int index) {
        checkIndex(index);
        T removed;
        if (index == 0) {
            removed = head.item;
            head = head.next;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            removed = current.next.item;
            current.next = current.next.next;
        }
        size--;
        return removed;
    }

    /**
     * Clears the entire list.
     */
    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Compares two elements in the list based on their indices.
     *
     * @param index1 Index of the first element.
     * @param index2 Index of the second element.
     * @return Result of comparison.
     */
    @Override
    protected int compare(int index1, int index2) {
        Node<T> node1 = getNode(index1);
        Node<T> node2 = getNode(index2);
        return node1.item.compareTo(node2.item);
    }

    /**
     * Swaps two elements in the list based on their indices.
     *
     * @param index1 Index of the first element.
     * @param index2 Index of the second element.
     */
    @Override
    protected void swap(int index1, int index2) {
        Node<T> node1 = getNode(index1);
        Node<T> node2 = getNode(index2);
        T temp = node1.item;
        node1.item = node2.item;
        node2.item = temp;
    }

    /**
     * Retrieves the node at the specified index.
     *
     * @param index Index of the node.
     * @return Node at the specified index.
     */
    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    /**
     * Checks if the index is within the valid range of the list.
     *
     * @param index Index to check.
     * @throws InvalidIndexException if the index is out of range.
     */
    @Override
    protected void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new InvalidIndexException("Index: " + index + ", Size: " + size);
        }
    }
}
