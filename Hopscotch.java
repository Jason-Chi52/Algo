import java.util.Scanner;
import java.io.*;

public class Hopscotch {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;

        int n = sc.nextInt();
        int[] a = new int[n + 1];           // 1-based indexing
        for (int i = 1; i <= n; i++) a[i] = sc.nextInt();

        final long NEG = Long.MIN_VALUE / 4; // sentinel for unreachable
        long[] dp = new long[n + 1];
        for (int i = 0; i <= n; i++) dp[i] = NEG;

        if (n >= 1) dp[1] = a[1];           // start on square 1

        for (int i = 2; i <= n; i++) {
            long bestPrev = NEG;
            if (i - 2 >= 1 && dp[i - 2] > bestPrev) bestPrev = dp[i - 2];
            if (i - 3 >= 1 && dp[i - 3] > bestPrev) bestPrev = dp[i - 3];
            if (bestPrev > NEG) dp[i] = a[i] + bestPrev;
        }

        long ans = NEG;
        for (int i = 1; i <= n; i++) if (dp[i] > ans) ans = dp[i];
        System.out.println(ans);
        sc.close();
    }
}
