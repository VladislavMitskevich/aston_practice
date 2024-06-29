package org.examle.aston_practice.list;

import org.examle.aston_practice.api.ICustomList;

/**
 * Абстрактный класс, представляющий базовый функционал списка.
 *
 * @param <T> Тип элементов в списке.
 */
public abstract class CustomAbstractList<T extends Comparable<T>> implements ICustomList<T> {
    protected int size = 0;

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element элемент для добавления.
     */
    public abstract void add(T element);

    /**
     * Добавляет элемент в указанный индекс списка.
     *
     * @param index   индекс для добавления элемента.
     * @param element элемент для добавления.
     */
    public abstract void add(int index, T element);

    /**
     * Возвращает элемент по указанному индексу.
     *
     * @param index индекс элемента.
     * @return элемент по указанному индексу.
     */
    public abstract T get(int index);

    /**
     * Удаляет элемент по указанному индексу.
     *
     * @param index индекс элемента для удаления.
     * @return удаленный элемент.
     */
    public abstract T remove(int index);

    /**
     * Очищает весь список.
     */
    public abstract void clear();

    /**
     * Сортирует список методом пузырьковой сортировки.
     */
    public void sort() {
        bubbleSort();
    }

    /**
     * Реализация пузырьковой сортировки.
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
     * Сравнивает два элемента в списке по индексам.
     *
     * @param index1 индекс первого элемента.
     * @param index2 индекс второго элемента.
     * @return результат сравнения.
     */
    protected abstract int compare(int index1, int index2);

    /**
     * Меняет местами два элемента в списке по индексам.
     *
     * @param index1 индекс первого элемента.
     * @param index2 индекс второго элемента.
     */
    protected abstract void swap(int index1, int index2);

    /**
     * Проверяет, что индекс находится в допустимом диапазоне.
     *
     * @param index индекс для проверки.
     */
    protected void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + size);
        }
    }

    /**
     * Возвращает размер списка.
     *
     * @return размер списка.
     */
    public int size() {
        return size;
    }
}
