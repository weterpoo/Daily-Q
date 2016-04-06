//time: ~O(n^2) (depth-first brute solution, memoization for speed)
//space: ~O(n^2) (memoization map probably takes up most of the space (iterates using indeces, no arrays are copied) and don't want to think about how much it uses, so just guessing)

/*
Notes:
- Doesn't fully work, just stopping for the time being and moving on
- Fails test cases 6, 7, 10
- TOs test cases 8, 9, 11
- Passes all others in 0.1 - 0.2 seconds
*/

import java.io.*;
import java.util.*;

public class NoahKrim {
    static final int mod = 1000000007;
        
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int i_t=0; i_t<t; i_t++)    {
            int m = in.nextInt();
            int n = in.nextInt();
            ArrayList<Integer> yCosts = new ArrayList<>();
            for(int i_m = 0; i_m<m-1; i_m++)    {
                yCosts.add(in.nextInt());
            }
            ArrayList<Integer> xCosts = new ArrayList<>();
            for(int i_n = 0; i_n<n-1; i_n++)    {
                xCosts.add(in.nextInt());
            }
            yCosts.sort(new revIntComparator());
            xCosts.sort(new revIntComparator());
            int result = solve(yCosts, xCosts);
            System.out.println(result);
        }
    }
    
    public static int solve(ArrayList<Integer> yCosts, ArrayList<Integer> xCosts) {
        return solve(0, 0, yCosts, xCosts, new HashMap<String, Integer>());
    }
    
    private static int solve(int yIndex, int xIndex, ArrayList<Integer> yCosts, ArrayList<Integer> xCosts, HashMap<String, Integer> memo) {
        boolean yDone = yIndex >= yCosts.size();
        boolean xDone = xIndex >= xCosts.size();
        Integer m;
        if((m = memo.get(yIndex+":"+xIndex)) != null)   {
            // System.out.println("memod: "+yIndex+":"+xIndex);
            return m.intValue();
        }
        if(yDone && xDone)
            return 0;
        else if(yDone)  {
            int result = 0;
            for(int i=xIndex; i<xCosts.size(); i++) {
                result += (xCosts.get(i) * (yIndex+1)); 
                result %= mod;
            }
            memo.put(yIndex+":"+xIndex, result);
            return result;
        }
        else if(xDone)  {
            int result = 0;
            for(int i=yIndex; i<yCosts.size(); i++) {
                result += (yCosts.get(i) * (xIndex+1));
                result %= mod;
            }
            memo.put(yIndex+":"+xIndex, result);
            return result;
        }
        else    {
            int yCost = (yCosts.get(yIndex) * (xIndex+1)) % mod;
            int xCost = (xCosts.get(xIndex) * (yIndex+1)) % mod;
            int yResult = (yCost + solve(yIndex+1, xIndex, yCosts, xCosts, memo)) % mod;
            int xResult = (xCost + solve(yIndex, xIndex+1, yCosts, xCosts, memo)) % mod;
            // System.out.println("y["+yIndex+"]="+yCosts.get(yIndex)+" -> "+yResult);
            // System.out.println("x["+xIndex+"]="+xCosts.get(xIndex)+" -> "+xResult);
            if(yResult < xResult)   {
                // System.out.println("\ty-cut");
                memo.put(yIndex+":"+xIndex, yResult);
                return yResult;
            }
            else    {
                // System.out.println("\tx-cut");
                memo.put(yIndex+":"+xIndex, xResult);
                return xResult;
            }
        }
    }
                           
    static class revIntComparator implements Comparator<Integer>    {
        public int compare(Integer o1, Integer o2)  {
            return o2 - o1;
        }
    }
}