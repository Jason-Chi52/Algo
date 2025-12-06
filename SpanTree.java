import java.util.Scanner;

public class SpanTree {


    static class DSU {
        int[] parent;
        byte[] rank;
        DSU(int n) {
            parent = new int[n + 1];
            rank = new byte[n + 1];
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }
        int find(int x) {
            int p = parent[x];
            if (p != x) parent[x] = find(p);
            return parent[x];
        }
        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            if (rank[ra] < rank[rb]) {
                parent[ra] = rb;
            } else if (rank[rb] < rank[ra]) {
                parent[rb] = ra;
            } else {
                parent[rb] = ra;
                rank[ra]++;
            }
            return true;
        }
    }

    static int[] U, V;
    static double[] W;


    static void swap(int i, int j) {
        int tu = U[i]; U[i] = U[j]; U[j] = tu;
        int tv = V[i]; V[i] = V[j]; V[j] = tv;
        double tw = W[i]; W[i] = W[j]; W[j] = tw;
    }

    static void quicksort(int l, int r) {
        while (l < r) {
            int i = l, j = r;
            double pivot = W[l + ((r - l) >> 1)];
            while (i <= j) {
                while (W[i] < pivot) i++;
                while (W[j] > pivot) j--;
                if (i <= j) {
                    swap(i, j);
                    i++; j--;
                }
            }

            if (j - l < r - i) {
                if (l < j) quicksort(l, j);
                l = i;
            } else {
                if (i < r) quicksort(i, r);
                r = j;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNext()) {
            System.out.println("-1");
            return;
        }

        int n = sc.nextInt();
        int m = sc.nextInt();


        int[] x = new int[m];
        int[] y = new int[m];
        double[] w = new double[m];
        int[] t = new int[m];

        for (int i = 0; i < m; i++) {
            x[i] = sc.nextInt();
            y[i] = sc.nextInt();
            w[i] = sc.nextDouble();
            t[i] = sc.nextInt();
        }

        DSU dsu = new DSU(n);
        double cost = 0.0;
        int picked = 0;

        for (int i = 0; i < m; i++) {
            if (t[i] == 1) {
                int u = x[i], v = y[i];
                if (!dsu.union(u, v)) {
                 
                    System.out.println("-1");
                    return;
                }
                cost += w[i];
                picked++;
            }
        }


        int nfCount = 0;
        for (int i = 0; i < m; i++) if (t[i] == 0) nfCount++;

        U = new int[nfCount];
        V = new int[nfCount];
        W = new double[nfCount];

        int idx = 0;
        for (int i = 0; i < m; i++) {
            if (t[i] == 0) {
                U[idx] = x[i];
                V[idx] = y[i];
                W[idx] = w[i];
                idx++;
            }
        }

        if (nfCount > 1) quicksort(0, nfCount - 1);


        for (int i = 0; i < nfCount && picked < n - 1; i++) {
            int u = U[i], v = V[i];
            if (dsu.union(u, v)) {
                cost += W[i];
                picked++;
            }
        }

        if (n == 0 || n == 1) {

            System.out.println("0");
            return;
        }

        if (picked == n - 1) {

            System.out.println(cost);
        } else {
            System.out.println("-1");
        }
    }
}
