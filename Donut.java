import java.io.*;
import java.util.Scanner;

public class Donut {

    static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }


    static int partition(int[] a, int left, int right, int pivotIndex) {
        int pivot = a[pivotIndex];
        swap(a, pivotIndex, right);
        int store = left;
        for (int i = left; i < right; i++) {
            if (a[i] <= pivot) {
                swap(a, i, store);
                store++;
            }
        }
        swap(a, store, right);
        return store;
    }

 
    static int quickselect(int[] a, int k) {
        int left = 0, right = a.length - 1;
        while (left <= right) {
            int pivotIndex = left + (right - left) / 2; 
            int m = partition(a, left, right, pivotIndex);
            if (m == k) return a[m];
            else if (k < m) right = m - 1;
            else left = m + 1;
        }
        return -1; 
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();

        int[] xs = new int[n];
        int[] ys = new int[n];
        for (int i = 0; i < n; i++) {
            xs[i] = sc.nextInt();
            ys[i] = sc.nextInt();
        }

        int k = (n - 1) / 2;       
        int xBest = quickselect(xs, k);
        int yBest = quickselect(ys, k);

        long total = 0L;
        for (int i = 0; i < n; i++) {
            total += Math.abs((long)xBest - xs[i]) + Math.abs((long)yBest - ys[i]);
        }

        System.out.println(total);
        sc.close();
    }
}
