import java.util.Comparator;

public class Solver {
    private boolean solve = false;
    private Queue<Board> sequence = new Queue<Board>();
    private SearchNode goal;
    
    private final Comparator<SearchNode> byHamming = new ByHamming();
    private final Comparator<SearchNode> byManhattan = new ByManhattan();
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(byManhattan);
        MinPQ<SearchNode> twinPq = new MinPQ<SearchNode>(byManhattan);
        pq.insert(new SearchNode(initial, 0, null));
        twinPq.insert(new SearchNode(initial.twin(), 0, null));
        while (!pq.isEmpty() && !twinPq.isEmpty()) {
            // initial search
            SearchNode current = pq.delMin();
            sequence.enqueue(current.board);
            if (current.board.isGoal()) {
                solve = true;
                goal = current;
                return;
            }
            for (Board neighbor : current.board.neighbors()) {
                if (current.prev != null && neighbor.equals(current.prev.board))
                    continue;
                pq.insert(new SearchNode(neighbor, current.moves + 1, current));
            }
            // twin search
            current = twinPq.delMin();
            if (current.board.isGoal()) {
                solve = false;
                return;
            }
            for (Board neighbor : current.board.neighbors()) {
                if (current.prev != null && neighbor.equals(current.prev.board))
                    continue;
                twinPq.insert(new SearchNode(neighbor, current.moves + 1, current));
            }
        }
    }
    
    private class ByHamming implements Comparator<SearchNode> {
        public int compare(SearchNode  n1, SearchNode  n2) {
            return n1.board.hamming() + n1.moves - n2.board.hamming() - n2.moves;
        }
    }
    
    private class ByManhattan implements Comparator<SearchNode> {
        public int compare(SearchNode  n1, SearchNode  n2) {
            return n1.board.manhattan() + n1.moves - n2.board.manhattan() - n2.moves;
        }
    }
    
    private class SearchNode {
        private Board board;
        private int moves;
        private SearchNode prev;
        
        public SearchNode(Board b, int m, SearchNode p) {
            board = b;
            moves = m;
            prev = p;
        }
    }
    // is the initial board solvable?
    public boolean isSolvable() {
        return solve;
    }
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (solve) return goal.moves;
        return -1;
    }
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (solve) return sequence;
        return null;
    }
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}