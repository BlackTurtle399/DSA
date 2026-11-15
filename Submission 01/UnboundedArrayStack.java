import java.util.NoSuchElementException;

/**
 * Array-based stack that resizes itself in BOTH directions.
 * push: when the array is full, capacity doubles.
 * pop : when only 1/4 of the array is used, capacity halves (never below MIN_CAPACITY).
 * Shrinking at 1/4 (not 1/2) avoids "thrashing" when push/pop alternate at a boundary.
 * Amortized O(1) for push and pop.
 */
public class UnboundedArrayStack {
    private static final int MIN_CAPACITY = 2;
    private int[] data = new int[MIN_CAPACITY];
    private int size = 0;

    public void push(int x) {
        if (size == data.length) resize(data.length * 2);
        data[size++] = x;
    }

    public int pop() {
        if (size == 0) throw new NoSuchElementException("Stack is empty");
        int top = data[--size];
        if (size > 0 && size == data.length / 4 && data.length / 2 >= MIN_CAPACITY) {
            resize(data.length / 2);
        }
        return top;
    }

    public int peek() {
        if (size == 0) throw new NoSuchElementException("Stack is empty");
        return data[size - 1];
    }

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }
    public int capacity() { return data.length; }

    private void resize(int newCapacity) {
        int[] copy = new int[newCapacity];
        for (int i = 0; i < size; i++) copy[i] = data[i];
        data = copy;
        System.out.println("  [resized to capacity " + newCapacity + "]");
    }

    public static void main(String[] args) {
        UnboundedArrayStack s = new UnboundedArrayStack();
        for (int i = 1; i <= 9; i++) s.push(i);      // grows 2 -> 4 -> 8 -> 16
        System.out.println("size=" + s.size() + " capacity=" + s.capacity());
        while (!s.isEmpty()) System.out.println("pop " + s.pop()); // shrinks back down
        System.out.println("size=" + s.size() + " capacity=" + s.capacity());
    }
}
