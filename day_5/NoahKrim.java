//time: O(nlog(n))
//space: O(nlog(n))


import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class NoahKrim {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int i=0;i<t;i++)    {
            int n = in.nextInt();
            int k = in.nextInt();
            int[] nums = new int[n];
            for(int j=0;j<n;j++)    
                nums[j] = in.nextInt();
            int[] prevs = new int[k];
            Arrays.sort(nums);
            System.out.println(knapsack(nums, k, 0, prevs));
        }
    }
    
    private static int knapsack(int[] nums, int k, int w, int[] prevs)    {
        int sum = 0;
        int maxSum = 0;
        for(int i=0;i<nums.length;i++)  {
            int x = nums[i];
            if(x <= w)  {
                int val = x + prevs[w-x];
                if(val > maxSum)
                    maxSum = val;
            }
        }
        if(w < k)   {
            prevs[w] = maxSum;
            return knapsack(nums, k, w+1, prevs);
        }
        else    {
            return maxSum;
        }
    }
}