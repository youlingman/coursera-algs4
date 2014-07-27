import java.util.Arrays;

public class Board {
    private int[] blocks;
    private int zeroCol, zeroRow;
    private int size;
    private int hammingDis = -1;
    private int manhattanDis = -1;
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        size = blocks.length;
        this.blocks = new int[blocks.length*blocks.length];
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks[i].length; j++) {
            if (blocks[i][j] == 0) {
                zeroRow = i;
                zeroCol = j;
            }
            put(i, j, blocks[i][j]);
        }
        size = blocks.length;
        hammingDis = hamming();
        manhattanDis = manhattan();
    }
    
    private Board(int[] blocks, int size, int row, int col) {
        this.blocks = Arrays.copyOf(blocks, blocks.length);
        this.size = size;
        this.zeroRow = row;
        this.zeroCol = col;
    }
        
    private void put(int i, int j, int num) {
        blocks[i * size + j] = num;
    }
    
    private int get(int i, int j) {
        return blocks[i * size + j];
    }
    
    // board dimension N
    public int dimension() {
        return size;
    }
    
    // number of blocks out of place
    public int hamming() {
        if (hammingDis != -1) return hammingDis;
        int count = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
            if (get(i, j) == 0) continue;
            if (get(i, j) != (i * size + j + 1)) count++;
        }
        return count;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattanDis != -1) return manhattanDis;
        int count = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
            if (get(i, j) == 0) continue;
            count += Math.abs((get(i, j) - 1) / size - i);
            count += Math.abs((get(i, j) - 1) % size - j);
        }
        return count;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }
    
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        Board twin = new Board(blocks, size, zeroRow, zeroCol);
        
        while (true) {
            int row = StdRandom.uniform(size);
            int col = StdRandom.uniform(size - 1);
            if (twin.get(row, col) == 0 || twin.get(row, col + 1) == 0) continue;
            int tmp = twin.get(row, col);
            twin.put(row, col, twin.get(row, col + 1));
            twin.put(row, col + 1, tmp);
            break;
        }
        
        return twin;
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        
        Board that = (Board) y;
        
        if (that.size != this.size) {
            return false;
        }
        
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            if (this.get(i, j) != that.get(i, j)) return false;
            
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<Board>();
        // up neighbor
        if (zeroRow != 0) addNeighbor(boards, blocks, zeroRow, zeroCol, 0);
        // down neighbor
        if (zeroRow != (size - 1)) addNeighbor(boards, blocks, zeroRow, zeroCol, 1);
        // left neighbor
        if (zeroCol != 0) addNeighbor(boards, blocks, zeroRow, zeroCol, 2);
        // left neighbor
        if (zeroCol != (size - 1)) addNeighbor(boards, blocks, zeroRow, zeroCol, 3);
        
        return boards;
    }
    
    private void addNeighbor(Stack<Board> boards, 
                             int[] block, 
                             int row, 
                             int col, 
                             int direct) {
        int newRow = row, newCol = col;
        switch(direct) {
            case 0:
                newRow = row - 1;
                break;
            case 1:
                newRow = row + 1;
                break;
            case 2:
                newCol = col - 1;
                break;
            case 3:
                newCol = col + 1;
                break;
            default:
                break;
        }
        block[row * size + col] = block[newRow * size + newCol];
        block[newRow * size + newCol] = 0;
        boards.push(new Board(block, size, newRow, newCol));
        block[newRow * size + newCol] = block[row * size + col];
        block[row* size + col] = 0;
    }
    
    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", blocks[i * size + j]));
            }
            s.append("\n");
        }
        
        return s.toString();
    }
    // unit test
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        StdOut.println(initial);
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.twin());
        // solve the puzzle
        for (Board board : initial.neighbors())
            StdOut.println(board);
    }
}