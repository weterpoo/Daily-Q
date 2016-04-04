import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.reflect.Array;
import java.math.*;
import java.util.regex.*;
public class SravanReddy {

	public static boolean grid_challenge(int num){
		Scanner vReader = new Scanner(System.in);
		int [][] myGrid = new int[num][num];
		for (int i = 0; i < num; i++){
			String temp = vReader.nextLine();
			for(int j = 0; j < num; j++){
				myGrid[i][j] = (int)temp.charAt(j); 
				
			}
		}
		
		for(int i = 0; i < num; i++){
			Arrays.sort(myGrid[i]);
		}
		
		for(int i = 0; i < num; i++){
			int temp = myGrid[0][i];
			for(int j = 1; j < num; j++){
				if(myGrid[j][i] > temp)
					return false;
				else
					temp = myGrid[j][i];
			}
		}
		
		return true;
		
	}
	
	public static void main(String[] args){
	  	Scanner vReader = new Scanner(System.in);
	  	
	  	int numTimes = vReader.nextInt();
	  	
	  	while(numTimes > 0){
	  	  if(grid_challenge(vReader.nextInt())){
	  		  System.out.println("YES");
	  	  
	  	  } else {
	  		  System.out.println("NO");
	  	  }
	  	}
	  	
	  	vReader.close();

	}

}