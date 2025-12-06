import java.util.Scanner;

public class MatrixChainParenthesize {
    // returns string in EXACT checker format:
    // "( " + left + " x " + right + " )" and leaves like "A1"
    static String formatParens(int[][] split, int i, int j) {
        if (i == j) return "A" + i;
        int k = split[i][j];
        String left  = formatParens(split, i, k);
        String right = formatParens(split, k + 1, j);
        return "( " + left + " x " + right + " )";
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        if (!in.hasNextInt()) return;

        int n = in.nextInt();              // number of matrices A1..An
        int[] a = new int[n + 1];          // dimensions a0..an
        for (int i = 0; i <= n; i++) a[i] = in.nextInt();

        long[][] m = new long[n + 1][n + 1]; // minimal costs
        int[][] split = new int[n + 1][n + 1]; // argmin k for (i,j)

        // m[i][i] = 0 by default

        // chain length from 2..n
        for (int len = 2; len <= n; len++) {
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1;
                long best = Long.MAX_VALUE / 4;
                int bestK = i;
                for (int k = i; k < j; k++) {
                    long cost = m[i][k] + m[k + 1][j] + (long) a[i - 1] * a[k] * a[j];
                    if (cost < best) { best = cost; bestK = k; }
                }
                m[i][j] = best;
                split[i][j] = bestK;
            }
        }

        System.out.println(m[1][n]);
        System.out.println(formatParens(split, 1, n));
    }
}
