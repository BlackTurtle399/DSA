import java.util.NoSuchElementException;

/**
 * SinglyLinkedList
 * ================
 * A linked list is a chain of small objects called NODES. Each node stores one value
 * and a reference ("pointer") to the NEXT node. The last node's next is null.
 *
 *   head -> [1|*]-> [2|*]-> [3|null]    <- tail
 *
 * We keep two references: 'head' (first node) and 'tail' (last node).
 * Keeping 'tail' makes addLast O(1). Without it we would have to walk the whole list.
 *
 * Unlike an array, nodes aren't side by side in memory, so there is no "jump to index i":
 * to reach position i we must walk from head, i steps (O(n)). In return, adding or
 * removing at the front needs no shifting of other items.
 *
 * "Singly" means links only go FORWARD, so we can't step backwards. That's why removeLast
 * is O(n): we must walk from head to find the node just before the tail.
 */
public class SinglyLinkedList {
    /** One link in the chain. */
    private static class Node {
        int data;       // the stored value
        Node next;      // reference to the following node (null at the end)
        Node(int d) { data = d; }
    }

    private Node head, tail;
    private int size;

    /** Add at the front: new node points to the old head, then becomes the head. O(1). */
    public void addFirst(int x) {
        Node n = new Node(x);
        n.next = head;                 // link new node in front of the old first node
        head = n;                      // new node is now the first
        if (tail == null) tail = n;    // list was empty: the new node is also the last
        size++;
    }

    /** Add at the end: old tail points to the new node, which becomes the tail. O(1). */
    public void addLast(int x) {
        Node n = new Node(x);
        if (head == null) {
            head = tail = n;           // empty list: one node is both first and last
        } else {
            tail.next = n;             // hook the new node after the current last one
            tail = n;                  // update the tail reference
        }
        size++;
    }

    /** Insert so the new value sits at position 'index'. O(n) because we must walk there. */
    public void insertAt(int index, int x) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index: " + index);
        if (index == 0) { addFirst(x); return; }       // front and back are special cases
        if (index == size) { addLast(x); return; }
        Node p = head;
        for (int i = 0; i < index - 1; i++) p = p.next; // stop at the node BEFORE the target spot
        Node n = new Node(x);
        n.next = p.next;               // new node points at what used to follow p
        p.next = n;                    // p now points at the new node
        size++;
    }

    /** Remove the first node and return its value. O(1): just move head forward. */
    public int removeFirst() {
        if (head == null) throw new NoSuchElementException("List is empty");
        int v = head.data;
        head = head.next;
        if (head == null) tail = null; // list became empty
        size--;
        return v;
    }

    /** Remove the last node. O(n): we need the node before the tail, and can't go backwards. */
    public int removeLast() {
        if (head == null) throw new NoSuchElementException("List is empty");
        if (head == tail) return removeFirst();         // only one node
        Node p = head;
        while (p.next != tail) p = p.next;              // find the second-to-last node
        int v = tail.data;
        p.next = null;                                  // cut the last node off
        tail = p;
        size--;
        return v;
    }

    /** Remove the first node holding value x. Returns true if one was found. */
    public boolean removeValue(int x) {
        if (head == null) return false;
        if (head.data == x) { removeFirst(); return true; }
        Node p = head;
        // Look one node AHEAD (p.next) so that we still hold the node before the one to delete.
        while (p.next != null && p.next.data != x) p = p.next;
        if (p.next == null) return false;               // reached the end: not found
        if (p.next == tail) tail = p;                   // deleting the last node moves the tail back
        p.next = p.next.next;                           // skip over the unwanted node
        size--;
        return true;
    }

    /** Get the value at an index by walking from the head. O(n). */
    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index);
        Node p = head;
        for (int i = 0; i < index; i++) p = p.next;
        return p.data;
    }

    /** Linear search. */
    public boolean contains(int x) {
        for (Node p = head; p != null; p = p.next) if (p.data == x) return true;
        return false;
    }

    /**
     * Reverse the list in place by flipping every 'next' arrow.
     * Three references move along together: prev (already-reversed part),
     * cur (node being processed), next (saved so we don't lose the rest).
     *   before: 1 -> 2 -> 3        after: 3 -> 2 -> 1
     */
    public void reverse() {
        Node prev = null, cur = head;
        tail = head;                                    // the old first node becomes the last
        while (cur != null) {
            Node next = cur.next;                       // 1) remember what comes next
            cur.next = prev;                            // 2) flip the arrow backwards
            prev = cur;                                 // 3) advance prev
            cur = next;                                 //    and cur
        }
        head = prev;                                    // prev ends on the old last node
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Node p = head; p != null; p = p.next) {
            sb.append(p.data);
            if (p.next != null) sb.append(" -> ");
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        SinglyLinkedList l = new SinglyLinkedList();
        l.addLast(2); l.addLast(3); l.addFirst(1);
        System.out.println(l);                           // [1 -> 2 -> 3]
        l.insertAt(2, 99);
        System.out.println(l);                           // [1 -> 2 -> 99 -> 3]
        l.removeValue(99);
        l.reverse();
        System.out.println(l);                           // [3 -> 2 -> 1]
        System.out.println(l.removeLast() + " " + l.removeFirst()); // 1 3
        System.out.println(l + " size=" + l.size());     // [2] size=1
    }
}
