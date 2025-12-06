import java.util.*;

public class CarmichaelNumbers {
    
    /**
     * Check if n is prime using trial division
     */
    public static boolean isPrime(long n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        
        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Check if n is a Carmichael number using Korselt's Criterion
     */
    public static boolean isCarmichael(long n) {
        // Carmichael numbers must be odd, composite, and > 2
        if (n < 3 || isPrime(n) || n % 2 == 0) {
            return false;
        }
        
        List<Long> factors = new ArrayList<>();
        long temp = n;
        
        // Factorize n and check if it's square-free
        for (long i = 2; i * i <= temp; i++) {
            if (temp % i == 0) {
                factors.add(i);
                int count = 0;
                while (temp % i == 0) {
                    count++;
                    temp /= i;
                }
                // If any prime factor appears more than once, not square-free
                if (count > 1) {
                    return false;
                }
            }
        }
        
        if (temp > 1) {
            factors.add(temp);
        }
        
        // Check that for each prime factor p, (p-1) divides (n-1)
        for (long p : factors) {
            if ((n - 1) % (p - 1) != 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Find the last three Carmichael numbers less than limit
     */
    public static List<Long> findLastThreeCarmichael(long limit) {
        List<Long> carmichaels = new ArrayList<>();
        // Only check odd numbers (Carmichael numbers are always odd)
        for (long n = 3; n < limit; n += 2) {
            if (isCarmichael(n)) {
                carmichaels.add(n);
            }
        }
        
        // Return the last three elements
        int size = carmichaels.size();
        if (size <= 3) {
            return carmichaels;
        } else {
            return carmichaels.subList(size - 3, size);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Finding last three Carmichael numbers less than 10^6 and 10^7...");
        
        List<Long> result1 = findLastThreeCarmichael(1_000_000);
        System.out.println("Last three Carmichael numbers less than 10^6: " + result1);
        
        List<Long> result2 = findLastThreeCarmichael(10_000_000);
        System.out.println("Last three Carmichael numbers less than 10^7: " + result2);
        
        // Verify a couple of examples
        System.out.println("\nVerification:");
        for (long n : result1) {
            System.out.println(n + " is Carmichael: " + isCarmichael(n));
        }
        for (long n : result2) {
            System.out.println(n + " is Carmichael: " + isCarmichael(n));
        }
    }
}