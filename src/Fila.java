/**
 * Implementação manual de uma Fila (FIFO) genérica.
 */
public class Fila<T> {
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public Fila() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("A fila está vazia!");
        }
        T data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("A fila está vazia!");
        }
        return head.data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Retorna um array com os elementos para facilidade de exibição.
     */
    @SuppressWarnings("unchecked")
    public T[] toArray(Class<T> clazz) {
        T[] arr = (T[]) java.lang.reflect.Array.newInstance(clazz, size);
        Node<T> current = head;
        int i = 0;
        while (current != null) {
            arr[i++] = current.data;
            current = current.next;
        }
        return arr;
    }
}
