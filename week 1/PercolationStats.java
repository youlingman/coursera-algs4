public class PercolationStats {
    private double[] results;
    
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("Nonnegative arguments");
        results = new double[T];
        for (int t = 0; t < T; t++) {
            Percolation p = new Percolation(N);
            int threshold = 0;
            while (!p.percolates()) {
                int i, j;
                do {
                    i = StdRandom.uniform(1, N + 1);
                    j = StdRandom.uniform(1, N + 1);
                } while(p.isOpen(i, j));
                p.open(i, j);
                threshold++;
            }
            results[t] = ((double) threshold) / (N*N);
        }
    }
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }
    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(results.length));
    }
    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(results.length));
    }
    // test client, described below
    public static void main(String[] args) {
        int N, T;
        N = Integer.parseInt(args[0]);
        T = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(N, T);
        System.out.println("mean                    = " + p.mean());
        System.out.println("stddev                  = " + p.stddev());
        System.out.println("95% confidence interval = " 
                               + p.confidenceLo() + ", " 
                               + p.confidenceHi());
    }
}