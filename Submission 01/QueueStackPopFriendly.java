import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Stack implemented using two queues - POP FRIENDLY.
 * push = O(n)  (new item is placed in front of all old items)
 * pop  = O(1)  (just dequeue; the front is always the top of the stack)
 */
public class QueueStackPopFriendly {
    private Queue<Integer> main = new LinkedList<>();
    private Queue<Integer> helper = new LinkedList<>();

    public void push(int x) {
        helper.add(x);                 // new item goes in first
        while (!main.isEmpty()) {
            helper.add(main.remove()); // then all older items behind it
        }
        Queue<Integer> tmp = main; main = helper; helper = tmp; // swap roles
    }

    public int pop() {
        if (main.isEmpty()) throw new NoSuchElementException("Stack is empty");
        return main.remove();
    }

    public int peek() {
        if (main.isEmpty()) throw new NoSuchElementException("Stack is empty");
        return main.peek();
    }

    public boolean isEmpty() { return main.isEmpty(); }
    public int size() { return main.size(); }

    public static void main(String[] args) {
        QueueStackPopFriendly s = new QueueStackPopFriendly();
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
