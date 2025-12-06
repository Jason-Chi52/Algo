public class ModExp {

    static long modExp(long base, long exp, long mod) {
        long result = 1 % mod;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) result = (result * base) % mod;
            base = (base * base) % mod;
            exp >>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        long a = 1234567L;
        long b = 23456789L;
        long n = 3333337L;
        System.out.println(modExp(a, b, n));  // -> 1138812
    }
}
