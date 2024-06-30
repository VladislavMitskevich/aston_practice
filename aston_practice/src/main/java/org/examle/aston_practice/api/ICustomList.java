package org.examle.aston_practice.api;

/**
 * ICustomList is a custom interface that defines the operations for a list.
 *
 * @param <T> the type of elements in this list
 */
public interface ICustomList<T> {
    /**
     * Adds an element to the end of this list.
     *
     * @param element the element to add
     */
    void add(T element);

    /**
     * Adds an element at the specified index in this list.
     *
     * @param index   the index at which the element should be added
     * @param element the element to add
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    void add(int index, T element);

    /**
     * Returns the element at the specified index in this list.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    T get(int index);

    /**
     * Removes the element at the specified index in this list.
     *
     * @param index the index of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    T remove(int index);

    /**
     * Removes all elements from this list.
     */
    void clear();

    /**
     * Sorts the elements of this list into ascending order.
     */
    void sort();

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    int size();
}
