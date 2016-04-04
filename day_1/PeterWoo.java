//time: O(N * Nlog(N)) because of the sort
//space: O(nlogn) because of quicksort

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int i = 0; i < cases; i++) {
            int N = sc.nextInt();
            char[][] G = new char[N][N];
            for(int j = 0; j < N; j++) {
                String line = sc.next();
                for(int k = 0; k < N; k++) {
                    G[j][k] = line.charAt(k);
                }
                Arrays.sort(G[j]);
            }
            
            
            boolean ans = true;
            for(int j = 0; j < N - 1; j++) {
                for(int k = 0; k < N; k++) {
                    if(G[j][k] > G[j+1][k]) { ans = false; }
                }
            }
            System.out.println(ans ? "YES" : "NO");
        }
    }
}
