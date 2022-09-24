import java.util.LinkedList;

public class HashTable {
    private LinkedList<HashEntry>[] elements; // Array of linked hash entries
    private int numItems; // Initialized to 0

    // Initializes a table of a reasonable default size (1024 entries). (Constant time)
    public HashTable() {
        this(1024);
    }

    // Initializes a table of the specified size. (Constant time)
    public HashTable(int size) {
        elements = new LinkedList[size];
    }

    // Stores the key-value pair in the hash table. Handles collisions with chaining. (Constant time)
    public void put(String key, int value) {
        put(key, value, key.hashCode());
    }

    // Stores the key-value pair in the hash table with a given hash code. Handles collisions with chaining. (Constant time)
    public void put(String key, int value, int hashCode) {
        if (numItems >= elements.length) rehash(); // Load factor is 1, so need to rehash
        int index = Math.abs(hashCode % elements.length);
        if (elements[index] == null) elements[index] = new LinkedList<HashEntry>();
        // Check if the value exists yet (don't insert again if it does but increase the frequency counter)
        for (HashEntry entry : elements[index]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(entry.getValue() + value);
                return;
            }
        }
        elements[index].add(new HashEntry(key, value));
        numItems++;
    }

    // Update value associated with key in the hash table. If key does not exist, it is added to the table. (Constant time)
    public void update(String key, int value) {
        if (numItems >= elements.length) rehash(); // Load factor is 1, so need to rehash
        int index = Math.abs(key.hashCode()) % elements.length;
        if (elements[index] == null) elements[index] = new LinkedList<HashEntry>();
        // Check if the value exists yet (don't insert again if it does but change the frequency counter)
        for (HashEntry entry : elements[index]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        elements[index].add(new HashEntry(key, value));
        numItems++;
    }

    // Returns the value associated with key if key exists and -1 if it does not. (Constant time)
    public int get(String key) {
        return get(key, key.hashCode());
    }

    // Returns the value associated with key and its given hashCode if key exists and -1 if it does not. (Constant time)
    public int get(String key, int hashCode) {
        if (elements.length == 0) return -1; // Done first to avoid divide by 0 error
        int index = Math.abs(key.hashCode()) % elements.length;
        if (elements[index] != null) {
            for (HashEntry entry : elements[index]) {
                if (entry.getKey().equals(key)) return entry.getValue();
            }
        }
        return -1;
    }

    // Expands the hash table and reassigns positions of all values. (Linear time)
    private void rehash() {
        LinkedList<HashEntry>[] newHash = new LinkedList[elements.length * 2 + 1];
        for(LinkedList<HashEntry> chain : elements) {
            if (chain != null) {
                for (HashEntry entry : chain) {
                    int index = Math.abs(entry.getKey().hashCode()) % newHash.length;
                    if (newHash[index] == null) newHash[index] = new LinkedList<HashEntry>();
                    // No need to account for duplicate cases
                    newHash[index].add(entry);
                }
            }
        }
        elements = newHash;
    }
}
