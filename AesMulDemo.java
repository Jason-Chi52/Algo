public class AesMulDemo {

    private static int gfMulAes(int a, int b) {
        int res = 0;
        for (int i = 0; i < 8; i++) {
            if ((b & 1) == 1) res ^= a;
            boolean carry = (a & 0x80) != 0;
            a = (a << 1) & 0xFF;
            if (carry) a ^= 0x1B;
            b >>>= 1;
        }
        return res & 0xFF;
    }

    public static void main(String[] args) {
        int x = 0x1E;
        int y = 0x37;

        int p1 = gfMulAes(x, y);
        int p2 = gfMulAes(y, x); 

        System.out.printf("0x%02X * 0x%02X = 0x%02X%n", x, y, p1);
        System.out.printf("0x%02X * 0x%02X = 0x%02X%n", y, x, p2);
    }
}
