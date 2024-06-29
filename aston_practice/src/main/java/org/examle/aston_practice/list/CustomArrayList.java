package org.examle.aston_practice.list;

/**
 * Реализация списка на основе динамического массива.
 *
 * @param <T> Тип элементов в списке.
 */
public class CustomArrayList<T extends Comparable<T>> extends CustomAbstractList<T> {
    /**
     * Массив для хранения элементов.
     */
    private Object[] elements;

    /**
     * Начальная емкость массива.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Конструктор по умолчанию.
     */
    public CustomArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element элемент для добавления.
     */
    @Override
    public void add(T element) {
        ensureCapacity(size + 1);
        elements[size++] = element;
    }

    /**
     * Добавляет элемент в указанный индекс списка.
     *
     * @param index   индекс для добавления элемента.
     * @param element элемент для добавления.
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + size);
        }
        ensureCapacity(size + 1);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Возвращает элемент по указанному индексу.
     *
     * @param index индекс элемента.
     * @return элемент по указанному индексу.
     */
    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    /**
     * Удаляет элемент по указанному индексу.
     *
     * @param index индекс элемента для удаления.
     * @return удаленный элемент.
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
     * Очищает весь список.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Сравнивает два элемента в списке по индексам.
     *
     * @param index1 индекс первого элемента.
     * @param index2 индекс второго элемента.
     * @return результат сравнения.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected int compare(int index1, int index2) {
        return ((T) elements[index1]).compareTo((T) elements[index2]);
    }

    /**
     * Меняет местами два элемента в списке по индексам.
     *
     * @param index1 индекс первого элемента.
     * @param index2 индекс второго элемента.
     */
    @Override
    protected void swap(int index1, int index2) {
        Object temp = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = temp;
    }

    /**
     * Обеспечивает достаточную емкость массива.
     *
     * @param minCapacity минимальная необходимая емкость.
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
