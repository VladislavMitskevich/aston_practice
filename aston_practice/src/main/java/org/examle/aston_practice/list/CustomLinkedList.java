package org.examle.aston_practice.list;

/**
 * Реализация списка на основе связного списка.
 *
 * @param <T> Тип элементов в списке.
 */
public class CustomLinkedList<T extends Comparable<T>> extends CustomAbstractList<T> {
    /**
     * Узел связного списка.
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
     * Голова списка.
     */
    private Node<T> head;

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element элемент для добавления.
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
     * Возвращает элемент по указанному индексу.
     *
     * @param index индекс элемента.
     * @return элемент по указанному индексу.
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
     * Удаляет элемент по указанному индексу.
     *
     * @param index индекс элемента для удаления.
     * @return удаленный элемент.
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
     * Очищает весь список.
     */
    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Сравнивает два элемента в списке по индексам.
     *
     * @param index1 индекс первого элемента.
     * @param index2 индекс второго элемента.
     * @return результат сравнения.
     */
    @Override
    protected int compare(int index1, int index2) {
        Node<T> node1 = getNode(index1);
        Node<T> node2 = getNode(index2);
        return node1.item.compareTo(node2.item);
    }

    /**
     * Меняет местами два элемента в списке по индексам.
     *
     * @param index1 индекс первого элемента.
     * @param index2 индекс второго элемента.
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
     * Возвращает узел по указанному индексу.
     *
     * @param index индекс узла.
     * @return узел по указанному индексу.
     */
    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
}
