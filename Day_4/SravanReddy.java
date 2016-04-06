import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/* Solution: I used was sort inputs and then compare which cut costs the most 
             Greedy element is selecting cut that costs the most in all cases */

//Time Complexity O(N log N) I think

public class Solution {
        
    public static long handleCase(int hor, int ver, Scanner in){

        long cost = 0;
        int horTrack = hor-2;
        int verTrack = ver-2;
        int horCuts = 0;
        int verCuts = 0;
        long[] horArr = new long[hor-1];
        long[] verArr = new long[ver-1];

        
        
        for(int i = 0; i < hor-1; i++){
            horArr[i] = in.nextInt();
        }
        
        for(int j =0; j < ver-1; j++){
            verArr[j] = in.nextInt();
        }
        Arrays.sort(horArr);
        Arrays.sort(verArr);
        
        //While we can do both kinds of cuts
        while(horTrack >= 0 && verTrack >= 0){
           if (horArr[horTrack] >= verArr[verTrack]){
             if(!(horArr[horTrack] == 0)){
               cost += (horArr[horTrack]*(verCuts + 1));
               horCuts++;
               }
             horTrack--;

           } else {
               if(!(verArr[verTrack] == 0)){
                 cost += (verArr[verTrack]* (horCuts +1));
                 verCuts++;
                 }
               verTrack--;
           }
        }
        
        //Only consider horizontal cuts left
        while(horTrack >= 0){
           if(!(horArr[horTrack] == 0)){
                 cost += (horArr[horTrack]*(verCuts + 1));
                 horCuts++;    
            }
               
           horTrack--;
        }
        
        //Only consider vertical cuts left
        while(verTrack >=0){
           if(!(verArr[verTrack] == 0)){
             cost += (verArr[verTrack]* (horCuts +1));
             verCuts++;   
           }
           verTrack--;
         }
        
        return cost;
    }

    public static void main(String[] args) {
        Scanner vReader = new Scanner(System.in);
        int T = vReader.nextInt();
        for(int i =0; i< T; i++){
            System.out.println(handleCase(vReader.nextInt(), vReader.nextInt(), vReader) % ((int)(Math.pow(10,9) + 7.0)));
        }
    }
}