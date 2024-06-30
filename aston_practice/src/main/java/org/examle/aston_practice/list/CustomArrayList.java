package org.examle.aston_practice.list;

import org.examle.aston_practice.exception.InvalidIndexException;

/**
 * Implementation of a list based on a dynamic array.
 *
 * @param <T> Type of elements in the list.
 */
public class CustomArrayList<T extends Comparable<T>> extends CustomAbstractList<T> {
    /**
     * Array to store elements.
     */
    private Object[] elements;

    /**
     * Default capacity of the array.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Default constructor.
     * Initializes the array with default capacity.
     */
    public CustomArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param element Element to add.
     */
    @Override
    public void add(T element) {
        ensureCapacity(size + 1);
        elements[size++] = element;
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
        ensureCapacity(size + 1);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Retrieves the element at the specified index.
     *
     * @param index Index of the element.
     * @return Element at the specified index.
     * @throws InvalidIndexException if the index is out of range.
     */
    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    /**
     * Removes the element at the specified index.
     *
     * @param index Index of the element to remove.
     * @return Removed element.
     * @throws InvalidIndexException if the index is out of range.
     */
    @SuppressWarnings("unchecked")
    @Override
    public T remove(int index) {
        checkIndex(index);
        T oldValue = (T) elements[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null;
        return oldValue;
    }

    /**
     * Clears the entire list.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Compares two elements in the list based on their indices.
     *
     * @param index1 Index of the first element.
     * @param index2 Index of the second element.
     * @return Result of comparison.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected int compare(int index1, int index2) {
        return ((T) elements[index1]).compareTo((T) elements[index2]);
    }

    /**
     * Swaps two elements in the list based on their indices.
     *
     * @param index1 Index of the first element.
     * @param index2 Index of the second element.
     */
    @Override
    protected void swap(int index1, int index2) {
        Object temp = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = temp;
    }

    /**
     * Ensures that the array has enough capacity to accommodate a minimum capacity.
     *
     * @param minCapacity Minimum required capacity.
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = elements.length * 2;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            Object[] newElements = new Object[newCapacity];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }
}
