package demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class Solution {
    static long mod = 1000000007;
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getenv("OUTPUT_PATH")));


        int n = Integer.parseInt(bufferedReader.readLine().trim());
        //String n = bufferedReader.readLine().trim();

        int[] arr = new int[n];
        String[] arrItems = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");
        for (int i = 0; i < n; i++) {
            int arrItem = Integer.parseInt(arrItems[i]);
            arr[i] = arrItem;
        }

        int result = plusMinus(arr);
        System.out.println(result);
        bufferedReader.close();
    }

    static int plusMinus(int[] arr) {
        //TODO ALGO:
        // 1st&last        2^n -1
        // other elements  p+ 2^(n-2-a) -2^a
        Instant start = Instant.now();
        int size = arr.length;

        //long sum = 0;
        long[] factor = new long[size];
        factor[0] = (fast_pow(2, size) - 1)%mod;
        factor[size - 1] = (fast_pow(2, size) - 1)%mod;
//        System.out.println("**"+0);
//        System.out.println("**"+(size-1));
        long sum = (arr[0] * factor[0])%mod;
        sum += (arr[size-1] * factor[size-1])%mod;
        for (int i = 0; i < size / 2; i++) {
            //System.out.println(i+1+"**"+(size-2-i));
            factor[i + 1] = ((factor[i] + fast_pow(2, size - 2 - i))%mod - fast_pow(2, i)%mod + mod)%mod;
            factor[size - 2 - i] = ((factor[size - i - 1] + fast_pow(2, size - 2 - i))%mod - fast_pow(2, i)%mod + mod)%mod;

            if (size>=5 && i!=((size/2)-1)){
                sum += ((arr[i+1] * factor[i+1])%mod+(arr[size-2-i] * factor[size-2-i])%mod)%mod;
            }else if (size>=5 && i == (size/2)-1 && size%2==1) {
                sum += (arr[size-2-i] * factor[size-2-i])%mod;
            }
        }
        if (size<5){
            sum=0;
            for (int i = 0; i < size; i++) {
                sum += (arr[i] * factor[i])%mod;
            }
        }
        Instant finish = Instant.now();
        System.out.println(Duration.between(start, finish).toNanos());
        return (int) (sum%mod);
    }

    static long fast_pow(long base, long n)
    {
        if(n==0)
            return 1;
        if(n==1)
            return (int) base;
        long halfn=fast_pow(base,n/2);
        if(n%2==0)
            return (( halfn * halfn ) % mod);
        else
            return (( ( ( halfn * halfn ) % mod ) * base ) % mod);
    }

}
