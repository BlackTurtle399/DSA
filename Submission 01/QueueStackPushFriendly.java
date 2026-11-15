import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Stack implemented using two queues - PUSH FRIENDLY.
 * push = O(1)  (just enqueue)
 * pop  = O(n)  (move n-1 items to the helper queue, remove the last one)
 */
public class QueueStackPushFriendly {
    private Queue<Integer> main = new LinkedList<>();
    private Queue<Integer> helper = new LinkedList<>();

    public void push(int x) {
        main.add(x);
    }

    public int pop() {
        if (main.isEmpty()) throw new NoSuchElementException("Stack is empty");
        while (main.size() > 1) {
            helper.add(main.remove());
        }
        int top = main.remove();
        Queue<Integer> tmp = main; main = helper; helper = tmp; // swap roles
        return top;
    }

    public int peek() {
        int top = pop();
        push(top);
        return top;
    }

    public boolean isEmpty() { return main.isEmpty(); }
    public int size() { return main.size(); }

    public static void main(String[] args) {
        QueueStackPushFriendly s = new QueueStackPushFriendly();
        s.push(1); s.push(2); s.push(3);
        System.out.println(s.pop()); // 3
        System.out.println(s.peek()); // 2
        s.push(4);
        System.out.println(s.pop()); // 4
        System.out.println(s.pop()); // 2
        System.out.println(s.pop()); // 1
        System.out.println(s.isEmpty()); // true
    }
}
