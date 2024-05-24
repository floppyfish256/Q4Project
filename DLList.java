public class DLList <E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DLList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private Node<E> getNode(int index) {
        Node<E> current = head;
        for(int i = 0; i < index; i++){
            current = current.next();
        }
        return current;
    }

    public boolean add(E item) {
        if(head == null){
            head = new Node<E>(item);
            tail = head;
        } else {
            Node<E> newNode = new Node<E>(item);
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
        return true;
    }

    public void add(int index, E item) {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }
        if(index == size){
            add(item);
        } else if(index == 0){
            Node<E> newNode = new Node<E>(item);
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
            size++;
        } else {
            Node<E> current = getNode(index);
            Node<E> newNode = new Node<E>(item);
            newNode.setNext(current);
            newNode.setPrev(current.prev());
            current.prev().setNext(newNode);
            current.setPrev(newNode);
            size++;
        }
    }

    public E get(int index) {
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).get();
    }

    public int size() {
        return size;
    }

    public E remove(int index) {
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){
            E temp = head.get();
            head = head.next();
            head.setPrev(null);
            size--;
            return temp;
        } else if(index == size - 1){
            E temp = tail.get();
            tail = tail.prev();
            tail.setNext(null);
            size--;
            return temp;
        } else {
            Node<E> current = getNode(index);
            current.prev().setNext(current.next());
            current.next().setPrev(current.prev());
            size--;
            return current.get();
        }
    }

    public E remove(E item) {
        Node<E> current = head;
        while(current != null){
            if(current.get().equals(item)){
                if(current == head){
                    head = current.next();
                    head.setPrev(null);
                } else if(current == tail){
                    tail = current.prev();
                    tail.setNext(null);
                } else {
                    current.prev().setNext(current.next());
                    current.next().setPrev(current.prev());
                }
                size--;
                return current.get();
            }
            current = current.next();
        }
        return null;
    }

    public void set(int index, E item) {
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        getNode(index).set(item);
    }

    public void shuffle() {
        for(int i = 0; i < size; i++){
            int randIndex = (int)(Math.random() * size);
            E temp = get(i);
            set(i, get(randIndex));
            set(randIndex, temp);
        }
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean contains(E item) {
        Node<E> current = head;
        while(current != null){
            if(current.get().equals(item)){
                return true;
            }
            current = current.next();
        }
        return false;
    }

    public String toString() {
        String result = "";
        for(int i = 0; i < size; i++){
            result += (i + 1) + ". " + get(i) + "\n";
        }
        return result;
    }

    public Object[] toArray() {
        Object[] result = new Object[size];
        for(int i = 0; i < size; i++){
            result[i] = get(i);
        }
        return result;
    }
}