//time: O(N * Nlog(N)) because of the sort
//space: O(nlogn) because of quicksort

import java.io.*;
import java.util.*;

public class NoahKrim {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int i_t=0; i_t<t; i_t++)    {
            int n = in.nextInt();
            char[][] grid = new char[n][];
            for(int i_n=0; i_n<n; i_n++)  {
                char[] arr = in.next().toCharArray();
                Arrays.sort(arr);
                grid[i_n] = arr;
            }
            boolean success = true;
            for(int j=0; j<n; j++)    {
                for(int i=0; i<n-1; i++)  {
                    if(grid[i][j] > grid[i+1][j])   {
                        success = false;
                        break;
                    }
                }
            }
            System.out.println(success ? "YES" : "NO");
        }
    }
}