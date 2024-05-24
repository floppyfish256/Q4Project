import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayListIterator<E> implements Iterator<E> {
    private int index;
    private MyArrayList<E> list;

    public MyArrayListIterator(MyArrayList<E> list) {
        this.list = list;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < list.size();
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return list.get(index++);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
