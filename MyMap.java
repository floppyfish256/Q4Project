public class MyMap<K, V> {
    private MyArrayList<K> keys;
    private MyArrayList<V> values;

    public MyMap() {
        keys = new MyArrayList<>();
        values = new MyArrayList<>();
    }

    public void put(K key, V value) {
        keys.add(key);
        values.add(value);
    }

    public V get(K key) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(key)) {
                return values.get(i);
            }
        }
        return null;
    }

    public void remove(K key) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(key)) {
                keys.remove(i);
                values.remove(i);
                return;
            }
        }
    }

    public MyArrayList<K> getKeys() {
        return keys;
    }

    public MyArrayList<V> getValues() {
        return values;
    }
}