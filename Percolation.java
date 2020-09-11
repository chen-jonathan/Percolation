/* *****************************************************************************
 *  Name: Jonathan Chen
 *  Date: April 24th, 2020
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// https://www.coursera.org/learn/algorithms-part1/programming/Lhp5z/percolation/discussions/threads/esi1vQYAEee-uQ7R_UJsXg
public class Percolation {
    private boolean[][] grid;
    private int numOpen;
    private WeightedQuickUnionUF union;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("row index i out of bounds");
        }
        else {
            grid = new boolean[n + 1][n + 1];
            for (int i = 1; i < n; i++) {
                for (int j = 1; j < n; j++) {
                    grid[i][j] = false;
                }
            }
            union = new WeightedQuickUnionUF(n * n + 2);
            numOpen = 0;
        }
    }

    private int convertCoordinate(int row, int col) {
        return ((row - 1) * (grid.length - 1)) + col;
    }

    public void open(int row, int col) {
        if (row <= 0 || row >= grid.length || col <= 0 || col >= grid.length) {
            throw new IllegalArgumentException("row index i out of bounds");
        }
        else {
            if (!isOpen(row, col)) {
                // Open specific site
                grid[row][col] = true;
                numOpen++;
                int index = convertCoordinate(row, col);
                // Check if it is a top or bottom site
                if (row == 1) {
                    union.union(0, index);
                }
                if (row == grid.length - 1) {
                    union.union((grid.length - 1) * (grid.length - 1) + 1, index);
                }
                // Connect to adjacent open sites
                if (col > 1) {
                    int indexLeft = convertCoordinate(row, col - 1);
                    if (isOpen(row, col - 1)) {
                        union.union(index, indexLeft);
                    }
                }
                if (row > 1) {
                    int indexTop = convertCoordinate(row - 1, col);
                    if (isOpen(row - 1, col)) {
                        union.union(index, indexTop);
                    }
                }
                if (row < grid.length - 1) {
                    int indexBottom = convertCoordinate(row + 1, col);
                    if (isOpen(row + 1, col)) {
                        union.union(index, indexBottom);
                    }
                }
                if (col < grid.length - 1) {
                    int indexRight = convertCoordinate(row, col + 1);
                    if (isOpen(row, col + 1)) {
                        union.union(index, indexRight);
                    }
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || row >= grid.length || col <= 0 || col >= grid.length) {
            throw new IllegalArgumentException("row index i out of bounds");
        }
        else {
            return grid[row][col];
        }
    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || row >= grid.length || col <= 0 || col >= grid.length) {
            throw new IllegalArgumentException("row index i out of bounds");
        }
        else {
            int index = convertCoordinate(row, col);
            return union.find(0) == union.find(index);
        }
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public boolean percolates() {
        return union.find(0) == union.find((grid.length - 1) * (grid.length - 1) + 1);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(10);
        System.out.print(p.numberOfOpenSites());
        p.isOpen(-1, 5);
        p.isFull(-1, 5);
        p.open(-1, 5);
    }
}
