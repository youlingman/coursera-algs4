public class Percolation {
    private WeightedQuickUnionUF grid;
    private boolean [][]open;
    private int n, top, bottom;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("Nonnegative arguments");
        grid = new WeightedQuickUnionUF(N * N + 2);
        open = new boolean[N][N];
        n = N;
        top = 0;
        bottom = n * n + 1;
    }
    
    // transfer a (x, y) site to the index in union set
    private int index(int i, int j) {
        return (i - 1) * n + j;
    }
    
    // juage if site is out of range
    private boolean validate(int i, int j) {
        if (i <= 0 || i > n || j <= 0 || j > n) return false;
        return true;
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (!validate(i, j)) throw new IndexOutOfBoundsException();
        else if (!open[i - 1][j - 1]) {
            open[i - 1][j - 1] = true;
            if (i == 1) grid.union(top, index(i, j));
            if (i == n) grid.union(bottom, index(i, j));
            // union up down left right respectively
            if (validate(i - 1, j) && isOpen(i - 1, j))
                grid.union(index(i, j), index(i - 1, j));
            if (validate(i + 1, j) && isOpen(i + 1, j))
                grid.union(index(i, j), index(i + 1, j));
            if (validate(i, j - 1) && isOpen(i, j - 1))
                grid.union(index(i, j), index(i, j - 1));
            if (validate(i, j + 1) && isOpen(i, j + 1))
                grid.union(index(i, j), index(i, j + 1));
        }
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if (!validate(i, j)) throw new IndexOutOfBoundsException();
        return open[i-1][j-1];
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (!validate(i, j)) throw new IndexOutOfBoundsException();
        return grid.connected(top, index(i, j));
    }
    
    // does the system percolate?
    public boolean percolates() {
        return grid.connected(top, bottom);
    }
}