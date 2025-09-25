import java.io.*;  

public class FindMaxPairsDouble {

    private static final long SCALE = 1_000_000L;

    private static class FastReader {
        private final InputStream in;
        private final byte[] buf = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastReader(InputStream is) { this.in = is; }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buf);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buf[ptr++];
        }

        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            // skip whitespace
            while ((c = read()) != -1 && c <= ' ') {}
            if (c == -1) return null;
            // read token
            do {
                sb.append((char)c);
                c = read();
            } while (c != -1 && c > ' ');
            return sb.toString();
        }
    }

    // Parse a decimal token into an exact integer number of micro-units (×10^6), no rounding.
    private static long parseScaledMicros(String s) {
        int i = 0, n = s.length();
        int sign = 1;
        if (s.charAt(0) == '+') { i++; }
        else if (s.charAt(0) == '-') { sign = -1; i++; }

        long intPart = 0;
        while (i < n) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {
                intPart = intPart * 10 + (ch - '0');
                i++;
            } else break;
        }

        long frac = 0;
        int fracDigits = 0;
        if (i < n && s.charAt(i) == '.') {
            i++;
            while (i < n) {
                char ch = s.charAt(i);
                if (ch >= '0' && ch <= '9') {
                    if (fracDigits < 6) {           // keep at most 6 digits, no rounding
                        frac = frac * 10 + (ch - '0');
                        fracDigits++;
                    }
                    i++;
                } else break;
            }
            // ignore any trailing non-digit characters (no exponent expected per spec)
        }
        while (fracDigits < 6) { frac *= 10; fracDigits++; } // pad

        long scaled = intPart * SCALE + frac;
        return sign < 0 ? -scaled : scaled;
    }

    private static void mergeSort(long[] a) {
        int n = a.length;
        long[] tmp = new long[n];
        boolean fromAtoTmp = true;

        long[] src = a, dst = tmp;

        for (int width = 1; width < n; width <<= 1) {
            for (int i = 0; i < n; i += (width << 1)) {
                int mid = Math.min(i + width, n);
                int hi  = Math.min(i + (width << 1), n);
                merge(src, dst, i, mid, hi);
            }
            // swap roles instead of copying back each pass
            long[] t = src; src = dst; dst = t;
            fromAtoTmp = !fromAtoTmp;
        }

        // ensure result ends up in a
        if (src != a) {
            System.arraycopy(src, 0, a, 0, n);
        }
    }

    private static void merge(long[] src, long[] dst, int lo, int mid, int hi) {
        int i = lo, j = mid, k = lo;
        while (i < mid && j < hi) {
            if (src[i] <= src[j]) dst[k++] = src[i++];
            else dst[k++] = src[j++];
        }
        while (i < mid) dst[k++] = src[i++];
        while (j < hi) dst[k++] = src[j++];
    }

    public static void main(String[] args) throws Exception {
        FastReader fr = new FastReader(System.in);

        String tokN = fr.next();
        if (tokN == null) return;
        int n = Integer.parseInt(tokN);

        long[] a = new long[n]; // store inputs in micro-units exactly
        for (int i = 0; i < n; i++) {
            a[i] = parseScaledMicros(fr.next());
        }

        long m = (long) n * (n - 1) / 2;
        if (m > Integer.MAX_VALUE) throw new RuntimeException("Too many pairs.");

        long[] sums = new long[(int) m];
        int idx = 0;
        for (int i = 0; i < n - 1; i++) {
            long ai = a[i];
            for (int j = i + 1; j < n; j++) {
                sums[idx++] = ai + a[j]; // exact integer micro-sum, no rounding
            }
        }

        mergeSort(sums);

        int maxCount = 0;
        long bestVal = 0L;

        int i = 0, total = sums.length;
        while (i < total) {
            int j = i + 1;
            while (j < total && sums[j] == sums[i]) j++;
            int runLen = j - i;
            if (runLen > maxCount || (runLen == maxCount && sums[i] < bestVal)) {
                maxCount = runLen;
                bestVal = sums[i];
            }
            i = j;
        }

        double t = bestVal / (double) SCALE;
        if (bestVal == 0L) t = 0.0; 

        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        out.printf("%d %.6f%n", maxCount, t);
        out.flush();
    }
}
