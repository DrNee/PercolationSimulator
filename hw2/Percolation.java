package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] o;
    private WeightedQuickUnionUF q, bw;
    private int N, vTop, vBottom, openSites;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        o = new boolean[N][N];
        q = new WeightedQuickUnionUF(N * N + 2);
        bw = new WeightedQuickUnionUF(N * N + 1);
        this.N = N;
        vTop = N * N;
        vBottom = N * N + 1;
        openSites = 0;
    }

    public void open(int row, int col) {
        if (row < 0 || col < 0 || row >= N || col >= N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)) {
            o[row][col] = true;
            openSites += 1;
        }
        if (row == 0) {
            q.union(xyTo1D(row, col), vTop);
            bw.union(xyTo1D(row, col), vTop);
        }
        if (row == N - 1) {
            q.union(xyTo1D(row, col), vBottom);
        }
        if (row < N - 1 && isOpen(row + 1, col)) {
            q.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            bw.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
        if (row > 0 && isOpen(row - 1, col)) {
            q.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            bw.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
        if (col < N - 1 && isOpen(row, col + 1)) {
            q.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            bw.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            q.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            bw.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= N || col >= N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return o[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || col < 0 || row >= N || col >= N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return bw.connected(vTop, xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return q.connected(vTop, vBottom);
    }

    private int xyTo1D(int row, int col) {
        return N * row + col;
    }

    public static void main(String[] args) { }
}
