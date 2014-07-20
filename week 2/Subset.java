public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int n = 1;
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
		// use Reservoir sampling algorithm below to get bonus
        while (!StdIn.isEmpty()) {
            String tmp = StdIn.readString();
            if (n <= k) {
                queue.enqueue(tmp);
            }
            else {
                int dice = StdRandom.uniform(n);
                if (dice <= (k - 1)) {
                    queue.dequeue();
                    queue.enqueue(tmp);
                }
            }
            n++;
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}