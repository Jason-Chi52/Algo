import java.util.Scanner;

public class HorizontalCrossing {

    // Class to represent a point
    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    // Count how many times a horizontal line at lineY crosses the polygon
    static int countCrossings(Point[] vertices, double lineY) {
        int n = vertices.length;
        int crossings = 0;
        for (int i = 0; i < n; i++) {
            Point p1 = vertices[i];
            Point p2 = vertices[(i + 1) % n];
            if (p1.y == p2.y) continue; // skip horizontal edges
            int minY = (p1.y < p2.y ? p1.y : p2.y);
            int maxY = (p1.y > p2.y ? p1.y : p2.y);
            if (minY < lineY && lineY < maxY) crossings++;
        }
        return crossings;
    }

    // Comparator: (y1,d1) < (y2,d2) if y1<y2 or (y1==y2 and d1<d2)
    // We want delta -1 before +1 on ties to keep intervals open at endpoints.
    static int cmp(int y1, int d1, int y2, int d2) {
        if (y1 != y2) return (y1 < y2) ? -1 : 1;
        return (d1 < d2) ? -1 : ((d1 > d2) ? 1 : 0);
    }

    // In-place quicksort on parallel arrays evY and evD
    static void sortEvents(int[] evY, int[] evD, int l, int r) {
        while (l < r) {
            int i = l, j = r;
            int m = l + ((r - l) >>> 1);
            int py = evY[m], pd = evD[m];
            while (i <= j) {
                while (cmp(evY[i], evD[i], py, pd) < 0) i++;
                while (cmp(evY[j], evD[j], py, pd) > 0) j--;
                if (i <= j) {
                    int ty = evY[i]; evY[i] = evY[j]; evY[j] = ty;
                    int td = evD[i]; evD[i] = evD[j]; evD[j] = td;
                    i++; j--;
                }
            }
            if (j - l < r - i) {
                if (l < j) sortEvents(evY, evD, l, j);
                l = i;
            } else {
                if (i < r) sortEvents(evY, evD, i, r);
                r = j;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n;
        if (!sc.hasNextInt()) {
            sc.close();
            return;
        }
        n = sc.nextInt();

        Point[] vertices = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            vertices[i] = new Point(x, y);
        }
        sc.close();

        // Build open intervals from non-horizontal edges: (ymin, ymax)
        // Represent each as two events: (ymin, +1) and (ymax, -1)
        int m = 0;
        for (int i = 0; i < n; i++) {
            int j = (i + 1 == n) ? 0 : i + 1;
            if (vertices[i].y != vertices[j].y) m++;
        }

        int[] evY = new int[2 * m];
        int[] evD = new int[2 * m];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int j = (i + 1 == n) ? 0 : i + 1;
            int y1 = vertices[i].y, y2 = vertices[j].y;
            if (y1 == y2) continue; // skip horizontal edges
            if (y1 < y2) {
                evY[k] = y1; evD[k] = +1; k++;
                evY[k] = y2; evD[k] = -1; k++;
            } else {
                evY[k] = y2; evD[k] = +1; k++;
                evY[k] = y1; evD[k] = -1; k++;
            }
        }

        if (k > 1) sortEvents(evY, evD, 0, k - 1);

        int curr = 0, best = 0;
        for (int i = 0; i < k; i++) {
            curr += evD[i];
            if (curr > best) best = curr;
        }

        System.out.println(best);
    }
}
