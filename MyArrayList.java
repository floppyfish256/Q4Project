import java.util.Iterator;

public class MyArrayList<E> implements Iterable<E> {
    private E[] data;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public MyArrayList(int capacity) {
        this.capacity = capacity;
        data = (E[]) new Object[capacity];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public void add(E item) {
        if (size == capacity) {
            capacity *= 2;
            E[] temp = (E[]) new Object[capacity];
            System.arraycopy(data, 0, temp, 0, size);
            data = temp;
        }
        data[size] = item;
        size++;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return data[index];
    }

    public int size() {
        return size;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E temp = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        size--;
        return temp;
    }

    public E remove(E item) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(item)) {
                return remove(i);
            }
        }
        return null;
    }

    public void clear() {
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyArrayListIterator<>(this);
    }
}
