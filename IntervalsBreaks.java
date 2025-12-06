import java.util.Scanner;

public class IntervalsBreaks {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        if (!in.hasNextInt()) return;
        int n = in.nextInt();

        int[] s = new int[n];
        int[] f = new int[n];
        for (int i = 0; i < n; i++) {
            s[i] = in.nextInt();
            f[i] = in.nextInt();
        }

        int[][] b = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                b[i][j] = in.nextInt();
            }
        }

        // indices 0..n-1
        int[] order = new int[n];
        for (int i = 0; i < n; i++) order[i] = i;

        // insertion sort by (s, then f)
        for (int i = 1; i < n; i++) {
            int key = order[i];
            int keyS = s[key], keyF = f[key];
            int j = i - 1;
            while (j >= 0) {
                int oj = order[j];
                if (keyS < s[oj] || (keyS == s[oj] && keyF < f[oj])) {
                    order[j + 1] = order[j];
                    j--;
                } else break;
            }
            order[j + 1] = key;
        }

        int[] dp = new int[n];
        int ans = 0;

        for (int pos = 0; pos < n; pos++) {
            int j = order[pos];
            dp[j] = 1; // take j alone
            for (int p = 0; p < pos; p++) {
                int i = order[p];
                if (f[i] + b[i][j] <= s[j]) {
                    int cand = dp[i] + 1;
                    if (cand > dp[j]) dp[j] = cand;
                }
            }
            if (dp[j] > ans) ans = dp[j];
        }

        System.out.println(ans);
    }
}
