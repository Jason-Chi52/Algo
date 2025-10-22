
import java.util.Scanner;
import java.io.*;

public class LongestIncreasingSubseqRecursive {
    static int[] A;
    static int n;

    // Length of an increasing subsequence that ends at index j (0-based)
    static int lisEnd(int j) {
        int best = 1; // subsequence that includes only A[j]
        for (int i = 0; i < j; i++) {
            if (A[i] < A[j]) {
                int cand = lisEnd(i) + 1;
                if (cand > best) best = cand;
            }
        }
        return best;
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        n = sc.nextInt();
        A = new int[n];
        for (int i = 0; i < n; i++) A[i] = sc.nextInt();

        int ans = 0;
        for (int j = 0; j < n; j++) {
            int len = lisEnd(j);
            if (len > ans) ans = len;
        }
        System.out.println(ans);
        sc.close();
    }
}
