/* *****************************************************************************
 *  Name: Jonathan Chen	
 *  Date: April 24th, 2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] threshold;
    private double meanVal;
    private double deviation;

    public PercolationStats(int n, int trials) {

        threshold = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    if (p.percolates()) {
                        threshold[i] = ((double) p.numberOfOpenSites() / (n * n));
                    }
                }
            }
        }
        meanVal = StdStats.mean(threshold);
        deviation = StdStats.stddev(threshold);
    }

    public double mean() {
        return meanVal;
    }

    public double stddev() {
        return deviation;
    }

    public double confidenceLo() {
        return meanVal - ((1.96 * deviation) / Math.sqrt(threshold.length));
    }

    public double confidenceHi() {
        return meanVal + ((1.96 * deviation) / Math.sqrt(threshold.length));
    }

    public static void main(String[] args) {
        System.out.println((double) 11 / 17);
    }
}
