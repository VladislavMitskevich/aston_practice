package org.examle.aston_practice.list;

import org.examle.aston_practice.api.ICustomList;

/**
 * Abstract class representing basic functionality for a list.
 *
 * @param <T> Type of elements in the list.
 */
public abstract class CustomAbstractList<T extends Comparable<T>> implements ICustomList<T> {
    protected int size = 0;

    /**
     * Adds an element to the end of the list.
     *
     * @param element Element to add.
     */
    public abstract void add(T element);

    /**
     * Adds an element at the specified index in the list.
     *
     * @param index   Index at which the element should be added.
     * @param element Element to add.
     */
    public abstract void add(int index, T element);

    /**
     * Retrieves the element at the specified index.
     *
     * @param index Index of the element.
     * @return Element at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public abstract T get(int index);

    /**
     * Removes the element at the specified index.
     *
     * @param index Index of the element to remove.
     * @return Removed element.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public abstract T remove(int index);

    /**
     * Clears the entire list.
     */
    public abstract void clear();

    /**
     * Sorts the list using bubble sort algorithm.
     */
    public void sort() {
        bubbleSort();
    }

    /**
     * Implementation of bubble sort algorithm for sorting elements in the list.
     */
    protected void bubbleSort() {
        for (int i = 0; i < size - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < size - 1 - i; j++) {
                if (compare(j, j + 1) > 0) {
                    swap(j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    /**
     * Compares two elements in the list based on their indices.
     *
     * @param index1 Index of the first element.
     * @param index2 Index of the second element.
     * @return Negative value if the first element is less than the second,
     *         positive value if the first element is greater than the second,
     *         zero if the elements are equal.
     */
    protected abstract int compare(int index1, int index2);

    /**
     * Swaps two elements in the list based on their indices.
     *
     * @param index1 Index of the first element.
     * @param index2 Index of the second element.
     */
    protected abstract void swap(int index1, int index2);

    /**
     * Checks if the index is within the valid range of the list.
     *
     * @param index Index to check.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    protected void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Returns the size of the list.
     *
     * @return Size of the list.
     */
    public int size() {
        return size;
    }
}
