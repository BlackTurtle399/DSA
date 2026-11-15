import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Queue implemented using two stacks - ENQUEUE FRIENDLY.
 * enqueue = O(1)  (just push onto the main stack)
 * dequeue = O(n)  (empty main into helper so the oldest item is on top,
 *                  pop it, then move everything back)
 */
public class StackQueueEnqueueFriendly {
    private final Stack<Integer> main = new Stack<>();
    private final Stack<Integer> helper = new Stack<>();

    public void enqueue(int x) {
        main.push(x);
    }

    public int dequeue() {
        if (main.isEmpty()) throw new NoSuchElementException("Queue is empty");
        while (!main.isEmpty()) helper.push(main.pop());
        int front = helper.pop();
        while (!helper.isEmpty()) main.push(helper.pop());
        return front;
    }

    public int peek() {
        if (main.isEmpty()) throw new NoSuchElementException("Queue is empty");
        while (!main.isEmpty()) helper.push(main.pop());
        int front = helper.peek();
        while (!helper.isEmpty()) main.push(helper.pop());
        return front;
    }

    public boolean isEmpty() { return main.isEmpty(); }
    public int size() { return main.size(); }

    public static void main(String[] args) {
        StackQueueEnqueueFriendly q = new StackQueueEnqueueFriendly();
        q.enqueue(1); q.enqueue(2); q.enqueue(3);
        System.out.println(q.dequeue()); // 1
        System.out.println(q.peek());    // 2
        q.enqueue(4);
        System.out.println(q.dequeue()); // 2
        System.out.println(q.dequeue()); // 3
        System.out.println(q.dequeue()); // 4
        System.out.println(q.isEmpty()); // true
    }
}
