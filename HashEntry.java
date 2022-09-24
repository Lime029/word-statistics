public class HashEntry implements Comparable<HashEntry>{
    private String key;
    private int value;

    // Initializes a HashEntry with a key and a value. (Constant time)
    public HashEntry(String key, int value) {
        this.key = key;
        this.value = value;
    }

    // Returns this HashEntry's key. (Constant time)
    public String getKey() { return key; }

    // Returns this HashEntry's value. (Constant time)
    public int getValue() { return value; }

    // Sets this HashEntry's value to the specified int. (Constant time)
    public void setValue(int value) { this.value = value; }

    // Compares the HashEntries by value (input minus this). (Constant time)
    public int compareTo(HashEntry entry) {
        return entry.getValue() - getValue();
    }
}
