//time: O(n) 
//space: O(n)

/*
Notes:
- Lmao didn't read that it was in the "greedy" category until like 3 hours into it, rekt
*/

import java.io.*;
import java.util.*;

public class NoahKrim {
    static final long mod = (int)Math.pow(10,9)+7;
        
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int i_t=0; i_t<t; i_t++)    {
            int m = in.nextInt();
            int n = in.nextInt();
            ArrayList<Long> yCosts_list = new ArrayList<>();
            for(int i_m=0; i_m<m-1; i_m++)    {
                yCosts_list.add(in.nextLong());
            }
            ArrayList<Long> xCosts_list = new ArrayList<>();
            for(int i_n = 0; i_n<n-1; i_n++)    {
                xCosts_list.add(in.nextLong());
            }
            yCosts_list.sort(Collections.reverseOrder());
            xCosts_list.sort(Collections.reverseOrder());
            long[] yCosts = new long[yCosts_list.size()];
            for(int i_y=0; i_y<yCosts_list.size(); i_y++)    {
                yCosts[i_y] = yCosts_list.get(i_y);
            }
            long[] xCosts = new long[xCosts_list.size()];
            for(int i_x=0; i_x<xCosts_list.size(); i_x++)  {
                xCosts[i_x] = xCosts_list.get(i_x);
            }
            
            long totalCost = 0;
            int i_y, i_x;
            for(i_y=0, i_x=0; i_y<yCosts.length && i_x<xCosts.length;)  {               
                if(yCosts[i_y] > xCosts[i_x])    {
                    long yCut = (yCosts[i_y] * (i_x+1)) % mod;
                    totalCost += yCut;
                    totalCost %= mod;
                    i_y++;
                }
                else    {
                    long xCut = (xCosts[i_x] * (i_y+1)) % mod;
                    totalCost += xCut;
                    totalCost %= mod;
                    i_x++;
                }
            }
            for(; i_y<yCosts.length; i_y++) {
                long yCut = (yCosts[i_y] * (i_x+1)) % mod;
                totalCost += yCut;
                totalCost %= mod;
            }
            for(; i_x<xCosts.length; i_x++) {
                long xCut = (xCosts[i_x] * (i_y+1)) % mod;
                totalCost += xCut;
                totalCost %= mod;
            }
            
            totalCost %= mod;
            System.out.println(totalCost);
        }
    }
}