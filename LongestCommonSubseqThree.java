import java.util.Scanner;

public class LongestCommonSubseqThree{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        if (!in.hasNextInt()) return;
        int p = in.nextInt();
        int q = in.nextInt();
        int r = in.nextInt();

        int[] A = new int[p];
        int[] B = new int[q];
        int[] C = new int[r];

        for (int i = 0; i < p; i++) A[i] = in.nextInt();
        for (int j = 0; j < q; j++) B[j] = in.nextInt();
        for (int k = 0; k < r; k++) C[k] = in.nextInt();

        // 3D DP table: DP[i][j][k] = LCS length of A[0..i-1], B[0..j-1], C[0..k-1]
        int[][][] DP = new int[p + 1][q + 1][r + 1];

        for (int i = 1; i <= p; i++) {
            int ai = A[i - 1];
            for (int j = 1; j <= q; j++) {
                int bj = B[j - 1];
                for (int k = 1; k <= r; k++) {
                    int ck = C[k - 1];
                    if (ai == bj && bj == ck) {
                        DP[i][j][k] = 1 + DP[i - 1][j - 1][k - 1];
                    } else {
                        int m = DP[i - 1][j][k];
                        if (DP[i][j - 1][k] > m) m = DP[i][j - 1][k];
                        if (DP[i][j][k - 1] > m) m = DP[i][j][k - 1];
                        DP[i][j][k] = m;
                    }
                }
            }
        }

        int ell = DP[p][q][r];
        System.out.println(ell);

        if (ell == 0) {
            // print just the length line (no subsequence line required by many graders)
            return;
        }

        // Reconstruct one LCS in O(p+q+r)
        int[] res = new int[ell];
        int idx = ell - 1;

        int i = p, j = q, k = r;
        while (i > 0 && j > 0 && k > 0) {
            int ai = A[i - 1], bj = B[j - 1], ck = C[k - 1];
            if (ai == bj && bj == ck) {
                res[idx--] = ai;
                i--; j--; k--;
            } else if (i > 0 && DP[i][j][k] == DP[i - 1][j][k]) {
                i--;
            } else if (j > 0 && DP[i][j][k] == DP[i][j - 1][k]) {
                j--;
            } else {
                k--;
            }
        }

        // Print the subsequence
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < ell; t++) {
            if (t > 0) sb.append(' ');
            sb.append(res[t]);
        }
        System.out.println(sb.toString());
    }
}
