import java.io.*;

public class WeightedBySumInversions {

    // -------- Fast input (java.io only) --------
    static final class FastInput {
        private final InputStream in;
        private final byte[] buf = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastInput(InputStream is) { this.in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buf);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buf[ptr++];
        }
        int nextInt() throws IOException {
            int c, sgn = 1, val = 0;
            do { c = read(); } while (c <= 32);
            if (c == '-') { sgn = -1; c = read(); }
            while (c > 32) {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sgn;
        }
    }

    static int n;
    static int[] a;        // input array (also used as merge target)
    static int[] tmp;      // temp copy during merges
    static long[] suf;     // suffix sums over left half during each merge
    static long answer;    // weighted inversion total

    public static void main(String[] args) throws Exception {
        FastInput in = new FastInput(System.in);

        try {
            n = in.nextInt();           // first line: n
        } catch (Exception e) {
            return;                     // no input
        }

        a = new int[n];
        for (int i = 0; i < n; i++) {   // second line: b1 ... bn
            a[i] = in.nextInt();
        }

        tmp = new int[n];
        suf = new long[n];
        answer = 0L;

        mergeCount(0, n - 1);

        // Output exactly one line with the weighted count
        System.out.println(answer);
    }

    // Counts weighted inversions and sorts a[L..R] in-place (merge sort style)
    static void mergeCount(int L, int R) {
        if (L >= R) return;
        int M = (L + R) >>> 1;

        mergeCount(L, M);
        mergeCount(M + 1, R);

        // Copy a[L..R] to tmp[L..R]
        for (int i = L; i <= R; i++) tmp[i] = a[i];

        // Build suffix sums over the left half: suf[i] = sum(tmp[i..M])
        if (L <= M) {
            suf[M] = tmp[M];
            for (int i = M - 1; i >= L; i--) suf[i] = suf[i + 1] + tmp[i];
        }

        int i = L;       // pointer in left half [L..M]
        int j = M + 1;   // pointer in right half [M+1..R]
        int k = L;       // write pointer back to a[L..R]

        while (i <= M && j <= R) {
            if (tmp[i] <= tmp[j]) {
                a[k++] = tmp[i++];
            } else {
                // tmp[j] < tmp[i] → inversions with all left elements i..M
                int cnt = M - i + 1;
                long sumLeftSuffix = suf[i];  // sum of tmp[i..M]
                long r = tmp[j];
                // Add sum(b_lefts) + cnt * r
                answer += sumLeftSuffix + r * cnt;
                a[k++] = tmp[j++];
            }
        }
        while (i <= M) a[k++] = tmp[i++];
        while (j <= R) a[k++] = tmp[j++];
    }
}
