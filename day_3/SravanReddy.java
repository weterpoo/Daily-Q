import java.io.*;
import java.util.*;

public class Solution {
    
   public void reset(ArrayList<HashSet> myList){
   
   }
   public static void main(String[] args) throws Exception{
        boolean found;
        int foundAt;
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = bfr.readLine().split(" ");
        int N = Integer.parseInt(temp[0]);
        int I = Integer.parseInt(temp[1]);
        ArrayList<HashSet> setList = new ArrayList<HashSet>();

        for(int i = 0; i < I; i++){
            found = false;
            foundAt = 0;
            temp = bfr.readLine().split(" ");
            int a = Integer.parseInt(temp[0]);
            int b = Integer.parseInt(temp[1]);
            for(int j = 0; j < setList.size(); j++){
                if(setList.get(j).contains(a) || setList.get(j).contains(b)){
                  if(!found){
                    setList.get(j).add(a);
                    setList.get(j).add(b);
                    found = true;
                    foundAt = j;
                  }
                  else {
                   setList.get(foundAt).addAll(setList.get(j));
                   setList.get(j).clear();
                  }         
               }
            }
            
            for(int j = 0; j < setList.size(); j++){
                if (setList.get(j).isEmpty()){
                    setList.remove(setList.get(j));
                }
            }
             if(!found){
                    setList.add(new HashSet());
                    setList.get(setList.size()-1).add(a);
                    setList.get(setList.size()-1).add(b);

                }
            
          // Store a and b in an appropriate data structure of your choice
            
           
        }
       

        long combinations = N*(N-1)/2;
        for(int j = 0; j < setList.size(); j++){
            combinations -= (setList.get(j).size()*(setList.get(j).size() -1))/2;
        }
       

        // Compute the final answer - the number of combinations
       
      System.out.println(combinations);

    }
}