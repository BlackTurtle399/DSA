import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * StackQueue - DEQUEUE FRIENDLY
 * =============================
 * GOAL: build a QUEUE using only STACKS, but make DEQUEUE the cheap operation.
 *
 * THE IDEA
 *   Keep 'main' arranged so the OLDEST item is always on TOP. Then dequeue is a simple pop.
 *   The cost moves to enqueue: a new item has to go to the BOTTOM of the stack, underneath
 *   everything already there.
 *
 *   - enqueue: pour main into helper (reverses it), push the new item onto the now-empty
 *              main (it becomes the bottom), pour helper back on top.   -> O(n)
 *   - dequeue: pop main.                                               -> O(1)
 *
 * EXAMPLE: enqueue 1, 2, 3
 *   enqueue 1: main=[1]
 *   enqueue 2: pour -> helper=[1]; main=[2]; pour back -> main=[2,1] (1 on top)
 *   enqueue 3: pour -> helper=[1,2]; main=[3]; pour back -> main=[3,2,1] (1 on top)
 *   dequeue(): pop -> 1  (the oldest) - correct queue behaviour.
 */
public class StackQueueDequeueFriendly {
    private final Stack<Integer> main = new Stack<>();    // top = oldest item
    private final Stack<Integer> helper = new Stack<>();  // temporary storage while inserting

    /** Enqueue: slide the new item underneath all existing ones. */
    public void enqueue(int x) {
        while (!main.isEmpty()) helper.push(main.pop());   // move everything out of the way
        main.push(x);                                      // new item becomes the BOTTOM
        while (!helper.isEmpty()) main.push(helper.pop()); // put the old items back on top
    }

    /** Dequeue: the oldest item is already on top. */
    public int dequeue() {
        if (main.isEmpty()) throw new NoSuchElementException("Queue is empty");
        return main.pop();
    }

    /** Peek: look at the oldest item. */
    public int peek() {
        if (main.isEmpty()) throw new NoSuchElementException("Queue is empty");
        return main.peek();
    }

    public boolean isEmpty() { return main.isEmpty(); }
    public int size() { return main.size(); }

    public static void main(String[] args) {
        StackQueueDequeueFriendly q = new StackQueueDequeueFriendly();
        q.enqueue(1); q.enqueue(2); q.enqueue(3);
        System.out.println(q.dequeue());  // 1
        q.enqueue(4);
        System.out.println(q.peek());     // 2
        System.out.println(q.dequeue());  // 2
        System.out.println(q.dequeue());  // 3
        System.out.println(q.dequeue());  // 4
        System.out.println(q.isEmpty());  // true
    }
}
