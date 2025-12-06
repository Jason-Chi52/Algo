import java.util.Scanner;

public class DoubleKnapsackWithSolution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        if (!in.hasNextInt()) return;

        int n = in.nextInt();
        int C1 = in.nextInt();
        int C2 = in.nextInt();

        int[] w = new int[n + 1];
        int[] v = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            w[i] = in.nextInt();
            v[i] = in.nextInt();
        }

        // dp[i][c1][c2] = best value using first i items with capacities c1, c2
        int[][][] dp = new int[n + 1][C1 + 1][C2 + 1];
        // choice[i][c1][c2]: 0=skip i, 1=put i in knap1, 2=put i in knap2
        byte[][][] choice = new byte[n + 1][C1 + 1][C2 + 1];

        for (int i = 1; i <= n; i++) {
            int wi = w[i], vi = v[i];
            for (int c1 = 0; c1 <= C1; c1++) {
                for (int c2 = 0; c2 <= C2; c2++) {
                    int best = dp[i - 1][c1][c2];
                    byte pick = 0;

                    if (wi <= c1) {
                        int cand = dp[i - 1][c1 - wi][c2] + vi;
                        if (cand > best) {
                            best = cand;
                            pick = 1; // prefer knap1 on ties by using '>'
                        }
                    }
                    if (wi <= c2) {
                        int cand = dp[i - 1][c1][c2 - wi] + vi;
                        if (cand > best) {
                            best = cand;
                            pick = 2;
                        }
                    }

                    dp[i][c1][c2] = best;
                    choice[i][c1][c2] = pick;
                }
            }
        }

        // Output max value
        System.out.println(dp[n][C1][C2]);

        // Reconstruct one optimal assignment
        int[] k1 = new int[n];
        int[] k2 = new int[n];
        int p1 = 0, p2 = 0;
        int i = n, c1 = C1, c2 = C2;

        while (i > 0) {
            byte pick = choice[i][c1][c2];
            if (pick == 1) {
                k1[p1++] = i;
                c1 -= w[i];
                i--;
            } else if (pick == 2) {
                k2[p2++] = i;
                c2 -= w[i];
                i--;
            } else {
                i--;
            }
        }

        // Print indices in increasing order as in your example (reverse the collected lists)
        if (p1 == 0) {
            System.out.println();
        } else {
            StringBuilder sb = new StringBuilder();
            for (int t = p1 - 1; t >= 0; t--) {
                if (sb.length() > 0) sb.append(' ');
                sb.append(k1[t]);
            }
            System.out.println(sb.toString());
        }

        if (p2 == 0) {
            System.out.println();
        } else {
            StringBuilder sb = new StringBuilder();
            for (int t = p2 - 1; t >= 0; t--) {
                if (sb.length() > 0) sb.append(' ');
                sb.append(k2[t]);
            }
            System.out.println(sb.toString());
        }
    }
}
