import java.util.Scanner;
import java.io.*;

public class LongestIncreasingSubseqDP {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;

        int n = sc.nextInt();
        if (n <= 0) {
            System.out.println(0);
            sc.close();
            return;
        }

        int[] A = new int[n];
        for (int i = 0; i < n; i++) A[i] = sc.nextInt();

        int[] L = new int[n];   // L[j] = LIS length ending at j
        int ans = 0;

        for (int j = 0; j < n; j++) {
            int best = 1;       // at least the element itself
            for (int i = 0; i < j; i++) {
                if (A[i] < A[j]) {
                    int cand = L[i] + 1;
                    if (cand > best) best = cand;
                }
            }
            L[j] = best;
            if (best > ans) ans = best;
        }

        System.out.println(ans);
        sc.close();
    }
}
