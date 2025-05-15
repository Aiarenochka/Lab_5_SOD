package main.hashTable;

public class HashTable {
    private String[] table;
    private int capacity;
    private int size;
    private final float loadFactor = 1.0f;

    public HashTable(int initialCapacity) {
        this.capacity = initialCapacity;
        this.table = new String[capacity];
        this.size = 0;
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    public void insert(String key) {
        if (key == null) return;
        if ((float) size / capacity >= loadFactor) resize();

        int index = hash(key);
        while (table[index] != null && !table[index].equals(key)) {
            index = (index + 1) % capacity;
        }

        if (table[index] == null) {
            table[index] = key;
            size++;
        }
    }

    public boolean search(String key) {
        int index = hash(key);
        int start = index;
        while (table[index] != null) {
            if (table[index].equals(key)) return true;
            index = (index + 1) % capacity;
            if (index == start) break;
        }
        return false;
    }

    public boolean delete(String key) {
        int index = hash(key);
        int start = index;
        while (table[index] != null) {
            if (table[index].equals(key)) {
                table[index] = null;
                size--;
                rehashFrom(index); // Prevent search failure after deletion
                return true;
            }
            index = (index + 1) % capacity;
            if (index == start) break;
        }
        return false;
    }

    private void rehashFrom(int deletedIndex) {
        int index = (deletedIndex + 1) % capacity;
        while (table[index] != null) {
            String rehashKey = table[index];
            table[index] = null;
            size--;
            insert(rehashKey);
            index = (index + 1) % capacity;
        }
    }

    private void resize() {
        int newCapacity = capacity * 2;
        String[] oldTable = table;
        table = new String[newCapacity];
        capacity = newCapacity;
        size = 0;

        for (String key : oldTable) {
            if (key != null) insert(key);
        }
    }

    public void display() {
        for (int i = 0; i < capacity; i++) {
            System.out.println(i + " -> " + table[i]);
        }
    }
}
