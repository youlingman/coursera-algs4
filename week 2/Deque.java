import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private int n;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    // construct an empty deque
    public Deque() {
        n = 0;
        first = null;
        last = null;
    }
    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }
    // return the number of items on the deque
    public int size() {
        return n;
    }
    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldfirst;
        
        if (isEmpty()) last = first;
        else oldfirst.prev = first;
        n++;
    }
    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;
        last.next = null;
        
        if (isEmpty()) first = last;
        else oldlast.next = last;
        n++;
    }
    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (first == null) last = null;
        else first.prev = null;
        n--;
        return item;
    }
    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        if (last == null) first = null;
        else last.next = null;
        n--;
        return item;
    }
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return this.new DequeIterator<Item>();
    }
    private class DequeIterator<Item> implements Iterator<Item> {
        private Node current;
        
        public DequeIterator() {
            current = first;
        }
        @Override
        public boolean hasNext() {
            if (current == null) return false;
            return true;
        }
        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = (Item) current.item;
            current = current.next;
            return item;
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    // unit testing
    public static void main(String[] args) {
        Deque<String> p = new Deque<String>();
        p.addFirst("Hi");
        p.addFirst("Hello");
        p.addLast("Noah");
        p.addLast("test4");
        p.removeLast();
        //p.removeLast();
        //p.removeLast();
        StdOut.println(p.size());
        Iterator it = p.iterator();
        StdOut.println(it.next());
        StdOut.println(it.next());
        StdOut.println(it.next());
    }
}