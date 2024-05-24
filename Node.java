public class Node <E> {
    private E data;
    private Node<E> next;
    private Node<E> prev;

    public Node(E data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public E get() {
        return this.data;
    }

    public void set(E data) {
        this.data = data;
    }

    public Node<E> next() {
        return this.next;
    }

    public Node<E> prev() {
        return this.prev;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }
}