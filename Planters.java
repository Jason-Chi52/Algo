import java.io.*;
import java.util.Scanner;

public class Planters {

    //  Quicksort (for long[]) 
    static void swap(long[] a, int i, int j) { long t = a[i]; a[i] = a[j]; a[j] = t; }
    static int partition(long[] a, int l, int r) {
        long pivot = a[(l + r) >>> 1];
        int i = l, j = r;
        while (i <= j) {
            while (a[i] < pivot) i++;
            while (a[j] > pivot) j--;
            if (i <= j) { swap(a, i, j); i++; j--; }
        }
        return i;
    }
    static void quicksort(long[] a, int l, int r) {
        if (l >= r) return;
        int idx = partition(a, l, r);
        if (l < idx - 1) quicksort(a, l, idx - 1);
        if (idx < r) quicksort(a, idx, r);
    }

    // lowerBound & uniqueCompact
    static int lowerBound(long[] arr, int m, long x) {
        int lo = 0, hi = m;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] < x) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
    static int uniqueCompact(long[] src, int n, long[] dst) {
        if (n == 0) return 0;
        int m = 0;
        dst[m++] = src[0];
        for (int i = 1; i < n; i++) {
            if (src[i] != src[i - 1]) dst[m++] = src[i];
        }
        return m;
    }

    // Segment Tree 
    static final class SegTree {
        int N;
        int[] tree;

        SegTree(int n) {
            N = n;
            tree = new int[4 * N + 5];
        }
        void build(int idx, int l, int r, int[] base) {
            if (l == r) { tree[idx] = base[l]; return; }
            int mid = (l + r) >>> 1;
            build(idx << 1, l, mid, base);
            build(idx << 1 | 1, mid + 1, r, base);
            tree[idx] = tree[idx << 1] + tree[idx << 1 | 1];
        }
        void add(int pos, int delta) { add(1, 0, N - 1, pos, delta); }
        void add(int idx, int l, int r, int pos, int delta) {
            if (l == r) { tree[idx] += delta; return; }
            int mid = (l + r) >>> 1;
            if (pos <= mid) add(idx << 1, l, mid, pos, delta);
            else add(idx << 1 | 1, mid + 1, r, pos, delta);
            tree[idx] = tree[idx << 1] + tree[idx << 1 | 1];
        }
        int sum(int ql, int qr) {
            if (ql > qr) return 0;
            return sum(1, 0, N - 1, ql, qr);
        }
        int sum(int idx, int l, int r, int ql, int qr) {
            if (ql <= l && r <= qr) return tree[idx];
            int mid = (l + r) >>> 1;
            int res = 0;
            if (ql <= mid) res += sum(idx << 1, l, mid, ql, qr);
            if (qr > mid) res += sum(idx << 1 | 1, mid + 1, r, ql, qr);
            return res;
        }
        int firstPositiveAfter(int k) {
            if (k >= N - 1) return -1;
            if (sum(k + 1, N - 1) == 0) return -1;
            return firstPositiveGE(1, 0, N - 1, k + 1);
        }
        int firstPositiveGE(int idx, int l, int r, int ql) {
            if (l == r) return l;
            int mid = (l + r) >>> 1;
            int leftSum = 0;
            if (ql <= mid) leftSum = sum(idx << 1, l, mid, ql, mid);
            if (ql <= mid && leftSum > 0) return firstPositiveGE(idx << 1, l, mid, ql);
            return firstPositiveGE(idx << 1 | 1, mid + 1, r, Math.max(ql, mid + 1));
        }
    }

    // Core function
    static String canReplant(int p, int r, long[] S, long[] T) {
        int n = p + r;
        long[] A = new long[n];
        int idx = 0;
        for (int i = 0; i < p; i++) A[idx++] = S[i];
        for (int j = 0; j < r; j++) A[idx++] = T[j];
        quicksort(A, 0, n - 1);
        long[] U = new long[n];
        int m = uniqueCompact(A, n, U);

        int[] cnt = new int[m];
        for (int j = 0; j < r; j++) {
            int k = lowerBound(U, m, T[j]);
            cnt[k]++;
        }
        if (p > 1) quicksort(S, 0, p - 1);

        SegTree seg = new SegTree(m);
        seg.build(1, 0, m - 1, cnt);

        for (int i = p - 1; i >= 0; i--) {
            int k = lowerBound(U, m, S[i]);
            int jpos = seg.firstPositiveAfter(k);
            if (jpos == -1) return "NO";
            seg.add(jpos, -1);
            seg.add(k, +1);
        }
        return "YES";
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int p = sc.nextInt();
        int r = sc.nextInt();
        long[] S = new long[p];
        long[] T = new long[r];
        for (int i = 0; i < p; i++) S[i] = sc.nextLong();
        for (int j = 0; j < r; j++) T[j] = sc.nextLong();
        System.out.println(canReplant(p, r, S, T));
        sc.close();
    }
}
