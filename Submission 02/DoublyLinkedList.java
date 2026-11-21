import java.util.NoSuchElementException;

/**
 * DoublyLinkedList
 * ================
 * Like a singly linked list, but every node has TWO links: 'next' (forward) and
 * 'prev' (backward).
 *
 *   null <- [1] <-> [2] <-> [3] -> null
 *           head             tail
 *
 * BENEFITS over a singly linked list
 *   - removeLast is O(1): tail.prev gives the new last node directly (no walking).
 *   - You can traverse backwards.
 *   - Deleting a node you already hold needs no search for its predecessor.
 * COST: one extra reference per node, and more pointers to keep consistent. Whenever
 * you change a 'next' link, remember to fix the matching 'prev' link as well.
 */
public class DoublyLinkedList {
    private static class Node {
        int data;
        Node prev, next;
        Node(int d) { data = d; }
    }

    private Node head, tail;
    private int size;

    /** Add at the front. O(1). */
    public void addFirst(int x) {
        Node n = new Node(x);
        if (head == null) {
            head = tail = n;                 // first node ever: it's both head and tail
        } else {
            n.next = head;                   // new node points forward to old head
            head.prev = n;                   // old head points back to new node
            head = n;
        }
        size++;
    }

    /** Add at the back. O(1). */
    public void addLast(int x) {
        Node n = new Node(x);
        if (tail == null) {
            head = tail = n;
        } else {
            n.prev = tail;                   // new node points back to old tail
            tail.next = n;                   // old tail points forward to new node
            tail = n;
        }
        size++;
    }

    /** Insert so the new value ends up at 'index'. */
    public void insertAt(int index, int x) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index: " + index);
        if (index == 0) { addFirst(x); return; }
        if (index == size) { addLast(x); return; }
        Node p = head;
        for (int i = 0; i < index; i++) p = p.next;     // p = the node currently AT 'index'
        Node n = new Node(x);
        // Splice n in between p.prev and p. Four pointer updates in total:
        n.prev = p.prev;                    // 1) n looks back at p's old predecessor
        n.next = p;                         // 2) n looks forward at p
        p.prev.next = n;                    // 3) predecessor now looks forward at n
        p.prev = n;                         // 4) p now looks back at n
        size++;
    }

    /** Remove from the front. O(1). */
    public int removeFirst() {
        if (head == null) throw new NoSuchElementException("List is empty");
        int v = head.data;
        head = head.next;
        if (head == null) tail = null;       // list is now empty
        else head.prev = null;               // the new first node has nothing before it
        size--;
        return v;
    }

    /** Remove from the back. O(1) thanks to the prev link. */
    public int removeLast() {
        if (tail == null) throw new NoSuchElementException("List is empty");
        int v = tail.data;
        tail = tail.prev;
        if (tail == null) head = null;
        else tail.next = null;               // new last node has nothing after it
        size--;
        return v;
    }

    /** Remove the first node holding x. */
    public boolean removeValue(int x) {
        for (Node p = head; p != null; p = p.next) {
            if (p.data == x) {
                if (p == head) removeFirst();
                else if (p == tail) removeLast();
                else {
                    p.prev.next = p.next;    // neighbours point at each other,
                    p.next.prev = p.prev;    // bypassing p
                    size--;
                }
                return true;
            }
        }
        return false;
    }

    /** Get by index. Start from whichever end is closer, so at most size/2 steps. */
    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index);
        Node p;
        if (index < size / 2) {
            p = head;
            for (int i = 0; i < index; i++) p = p.next;
        } else {
            p = tail;
            for (int i = size - 1; i > index; i--) p = p.prev;
        }
        return p.data;
    }

    public boolean contains(int x) {
        for (Node p = head; p != null; p = p.next) if (p.data == x) return true;
        return false;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Node p = head; p != null; p = p.next) {
            sb.append(p.data);
            if (p.next != null) sb.append(" <-> ");
        }
        return sb.append("]").toString();
    }

    /** Walk from tail to head using the prev links. */
    public String toStringReverse() {
        StringBuilder sb = new StringBuilder("[");
        for (Node p = tail; p != null; p = p.prev) {
            sb.append(p.data);
            if (p.prev != null) sb.append(" <-> ");
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList l = new DoublyLinkedList();
        l.addLast(2); l.addLast(3); l.addFirst(1);
        System.out.println(l);                    // [1 <-> 2 <-> 3]
        System.out.println(l.toStringReverse());  // [3 <-> 2 <-> 1]
        l.insertAt(1, 99);
        System.out.println(l);                    // [1 <-> 99 <-> 2 <-> 3]
        l.removeValue(99);
        System.out.println(l.removeLast() + " " + l.removeFirst()); // 3 1
        System.out.println(l + " size=" + l.size());                // [2] size=1
    }
}
