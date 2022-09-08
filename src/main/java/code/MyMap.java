package code;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MyMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Entry<K, V>[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    private int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    private int size;
    private float loadFactor = DEFAULT_LOAD_FACTOR;

    public class Entry<K, V> {
        private K key;
        private V value;
        private Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }

            if (o == this) {
                return true;
            }

            Entry<K, V> entry = (Entry<K, V>) o;
            return key.equals(entry.key) && value.equals(entry.value);
        }

        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public String toString() {
            return key + "=" + value;
        }
    }

    public V getValue(K key) {
        if (size == 0) {
            return null;
        }

        int index = indexFor(hash(key), table.length);
        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        return null;
    }

    public K getKey(V value) {
        if (size == 0) {
            return null;
        }

        for (int i = 0; i < table.length; i++) {
            for (Entry<K, V> entry = table[i]; entry != null; entry = entry.next) {
                if (entry.value.equals(value)) {
                    return entry.key;
                }
            }
        }

        return null;
    }

    public void put(K key, V value) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);

        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        addEntry(hash, key, value, index);
    }

    public boolean containsKey(K key) {
        if (size == 0) {
            return false;
        }

        int hash = hash(key);
        int index = indexFor(hash, table.length);

        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsValue(V value) {
        return getKey(value) != null;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> result = new HashSet<>();

        for (Entry<K, V> entry : table) {
            while (entry != null) {
                result.add(entry);
                entry = entry.next;
            }
        }

        return result;
    }

    private int hash(K key) {
        return key.hashCode();
    }

    private int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    private void addEntry(int hash, K key, V value, int bucketIndex) {
        if (size >= threshold) {
            resize(table.length * 2);
            hash = hash(key);
            bucketIndex = indexFor(hash, table.length);
        }
        createEntry(hash, key, value, bucketIndex);
    }

    private void createEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K, V> e = table[bucketIndex];
        table[bucketIndex] = new Entry<>(key, value, e);
        size++;
    }

    private void transfer(Entry[] newTable) {
        int newCapacity = newTable.length;

        for (Entry e : table) {
            while (e != null) {
                Entry next = e.next;
                int index = indexFor(hash((K) e.key), newCapacity);
                e.next = newTable[index];
                newTable[index] = e;
                e = next;
            }
        }
    }

    private void resize(int newCapacity) {
        Entry<K, V>[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }
}
