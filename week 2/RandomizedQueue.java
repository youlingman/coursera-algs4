import java.util.ArrayList;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private ArrayList<Item> list;
    private Item[] queue;
    private int n;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        n = 0;
    }
    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }
    // return the number of items on the queue
    public int size() {
        return n;
    }
    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (n == queue.length)
            resize(2 * queue.length);
        queue[n++] = item;
    }
    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int index = StdRandom.uniform(n);
        Item item = queue[index];
        queue[index] = queue[n - 1];
        queue[n - 1] = null;
        n--;
        
        // shrink the queue if nesesary
        if (n < queue.length / 4)
            resize(queue.length / 2);
        
        return item;
    }
    
    private void resize(int size) {
        Item[] newQueue = (Item[]) new Object[size];
        for (int i = 0; i < n; i++)
            newQueue[i] = queue[i];
        queue = newQueue;
    }
    
    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int index = StdRandom.uniform(n);
        return (Item) queue[index];
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>();
    }
    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private int []random;
        private int current;
        
        public RandomizedQueueIterator() {
            current = 0;
            random = new int[n];
            for (int i = 0; i < n; i++)
                random[i] = i;
            StdRandom.shuffle(random);
        }
        @Override
        public boolean hasNext() {
            return current < random.length;
        }
        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return (Item) queue[random[current++]];
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<String> p = new RandomizedQueue<String>();
        p.enqueue("Hi");
        p.enqueue("Hello");
        p.enqueue("Noah");
        //StdOut.println(p.dequeue());
        //StdOut.println(p.dequeue());
        Iterator it = p.iterator();
        StdOut.println(it.next());
        StdOut.println(it.next());
        StdOut.println(it.next());
        StdOut.println(it.next());
    }
}