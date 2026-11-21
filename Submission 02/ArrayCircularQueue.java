import java.util.NoSuchElementException;

/**
 * ArrayCircularQueue
 * ==================
 * GOAL: a queue stored in a fixed-size array where both enqueue and dequeue are O(1).
 *
 * WHY "CIRCULAR"?
 *   A naive array queue removes from index 0, which forces you to shift every item left (O(n)).
 *   Alternative: don't shift - just move a 'front' marker forward. But then the front drifts
 *   to the end of the array while free space piles up at the start. The fix is to treat the
 *   array as a circle: after the last index, go back to index 0. We do that with the
 *   modulo operator:   next index = (index + 1) % capacity.
 *
 * WHAT WE STORE
 *   data  : the array            front : index of the oldest item
 *   size  : how many items       rear  : not stored; it is (front + size) % capacity
 *   (Tracking 'size' avoids the classic "is front==rear empty or full?" confusion.)
 *
 * EXAMPLE with capacity 3:
 *   enqueue 1,2,3 -> data=[1,2,3], front=0, size=3 (full)
 *   dequeue()     -> returns 1,  front=1, size=2
 *   enqueue 4     -> goes to (1+2)%3 = index 0 -> data=[4,2,3]   <-- wrapped around!
 */
public class ArrayCircularQueue {
    private final int[] data;     // fixed-size storage
    private int front = 0;        // index of the oldest item (next to be dequeued)
    private int size = 0;         // number of items currently stored

    public ArrayCircularQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
        data = new int[capacity];
    }

    /** Enqueue: write at the first free slot after the last item, wrapping with %. */
    public void enqueue(int x) {
        if (isFull()) throw new IllegalStateException("Queue is full");
        int rear = (front + size) % data.length;   // first free slot
        data[rear] = x;
        size++;
    }

    /** Dequeue: read the front item, then advance front (wrapping with %). */
    public int dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        int v = data[front];
        front = (front + 1) % data.length;         // move forward, wrap to 0 after the end
        size--;
        return v;
    }

    /** Peek: look at the front item without removing it. */
    public int peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        return data[front];
    }

    public boolean isEmpty() { return size == 0; }
    public boolean isFull() { return size == data.length; }
    public int size() { return size; }

    public static void main(String[] args) {
        ArrayCircularQueue q = new ArrayCircularQueue(3);
        q.enqueue(1); q.enqueue(2); q.enqueue(3);
        System.out.println(q.isFull());   // true
        System.out.println(q.dequeue());  // 1
        q.enqueue(4);                     // wraps around into index 0
        System.out.println(q.dequeue());  // 2
        System.out.println(q.dequeue());  // 3
        System.out.println(q.dequeue());  // 4
        System.out.println(q.isEmpty());  // true
    }
}
