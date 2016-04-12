//time: ~ depending on how many are missing
	//best: O(n) 
	//worst: O(nlog(n)) - (due to sort at the end of remaining nodes, ~2*(N - numMissing) depending)
//space: O(n)

/*
Notes:
	- The simples solution would be to just construct an array from 1-N and pull out the numbers
		as you encounter them, but I wanted to do something more interesting
	- This constructs a node for each number as it is read, and connects it to it's numerical
		siblings (node(n) connects to node(n-1) and node(n+1)).  If a node has both its upper
		and lower connection, then it is replaced by a generic node reference and removed from
		the map.  So when all numbers are read in, the map only contains nodes that are on the
		boundaries of either the number set (1 or N) or boundaries lying against a subste of
		missing numbers.  So the missing numbers are found by going through these leftover
		nodes, in order by their value, and if there's a null gap between any two of them then the
		numbers that should be in there are added to the missing number set, which is then returned.

Compile:
	javac NoahKrim.java

Format:
   	/-------------------------------------------------------------------------------\
   	|	Input Source	|	Output Source	|	Command								|
	|-------------------|-------------------|---------------------------------------|
	|	System.in 		|	System.out 		|	java NoahKrim						|
	|	Input File 		|	System.out 		| 	java NoahKrim test.in 				|
	|	Random Input 	|	System.out 		| 	java NoahKrim random:N:M 			|
	|	Random Input 	| 	Output File 	| 	java NoahKrim random:N:M test.out 	|
	\-------------------------------------------------------------------------------/
	- For random input, random:N:M produces a random set from 1 - N with M missing numbers
*/

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.util.regex.*;

public class NoahKrim {
	public static void main(String[] args) throws Exception	{
		Scanner in;
		Path fout = null;
		if(args.length == 1)	{
			Pattern p = Pattern.compile("^random:(\\d+):(\\d+)$");
			Matcher m = p.matcher(args[0]);
			if(m.matches())	
				in = new Scanner(makeRandomInput(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))));
			else
				in = new Scanner(Paths.get(args[0]));
			System.out.println("Reading from "+args[0]);
			System.out.println("Writing to system.out");
		}
		else if(args.length == 2)	{
			Pattern p = Pattern.compile("^random\\((\\d+),(\\d+)\\)$");
			Matcher m = p.matcher(args[0]);
			if(m.matches())	
				in = new Scanner(makeRandomInput(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))));
			else
				in = new Scanner(Paths.get(args[0]));
			fout = Paths.get(args[1]);
			System.out.println("Reading from "+args[0]);
			System.out.println("Writing to "+args[1]);
		}
		else	{
			in = new Scanner(System.in);
			System.out.println("Reading from system.in");
			System.out.println("Writing to system.out");
		}

		int N = Integer.parseInt(in.nextLine());
		if(N <= 1)	{
			System.out.println("N must be larger than 1");
			System.exit(1);
		}
		NumsMap map = new NumsMap(N);
		int n = Integer.parseInt(in.nextLine());
		do	{	
			map.add(n);
			n = Integer.parseInt(in.nextLine());
		} while(n > 0);
		ArrayList<Integer> missing = map.getMissing();
		if(missing.isEmpty())	{
			System.out.println("None missing");
		}
		else	{
			String out = "Missing: ";
			for(int temp : missing)	{
				out += temp + ", ";
			}
			out = out.substring(0,out.length()-2);
			System.out.println(out);
		}
	}

	private static Path makeRandomInput(int N, int numMissing) throws Exception	{
		if(N < 1 || numMissing < 0 || numMissing >= N)	{
			System.out.println("Invalid random parameters");
			System.exit(1);
		}
		Path p = Paths.get("random.in");
		ArrayList<Integer> possibleNums = new ArrayList<Integer>();
		for(int i=1; i<=N; i++)	{
			possibleNums.add(i);
		}
		int numNums = N - numMissing;
		String[] arr = new String[numNums+2];
		arr[0] = Integer.toString(N);
		arr[numNums+1] = Integer.toString(0);
		for(int i=1; i<=numNums; i++)	{
			int index = (int)(Math.random()*possibleNums.size());
			int n  = possibleNums.get(index);
			arr[i] = Integer.toString(n);
			possibleNums.remove(index);
		}
		System.out.println("\nRandom input generator ("+N+", "+numMissing+"):");
		if(numMissing == 0)
			System.out.println("No numbers are missing");
		else	{
			String out = "Missing will be: ";
			for(int temp : possibleNums)	{
				out += temp + ", ";
			}
			out = out.substring(0,out.length()-2)+"\n";
			System.out.println(out);
		} 
		Files.write(p, Arrays.asList(arr), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		return p;
	}

	static class NumsMap	{
		private int N;
		private HashMap<Integer, NumNode> map;

		public NumsMap(int N)	{
			this.N = N;
			map = new HashMap<>();		}

		public void add(int n)	{
			// System.out.println("adding: "+n);
			NumNode node = new NumNode(n);
			NumNode a, b;
			if((a = map.get(n+1)) != null)	{
				// System.out.println("found above: "+a.getVal());
				node.setAbove(a);
				a.setBelow(node);
				if(a.isComplete())	{
					map.remove(a.getVal());
					a.swapToGeneric();
				}
			}
			if((b = map.get(n-1)) != null)	{
				// System.out.println("found below: "+b.getVal());
				node.setBelow(b);
				b.setAbove(node);
				if(b.isComplete())	{
					map.remove(b.getVal());
					b.swapToGeneric();
				}
			}
			if(node.isComplete())	{
				node.swapToGeneric();
			}
			else	{
				map.put(n, node);
			}
		}

		public ArrayList<Integer> getMissing()	{
			ArrayList<Integer> missing = new ArrayList<>();
			Collection<NumNode> vals = map.values();
			NumNode[] arr = new NumNode[vals.size()];
			arr = vals.toArray(arr);
			Arrays.sort(arr);
			int prev = 0;
			for(int i_arr=0; i_arr<arr.length; i_arr++)	{
				NumNode n = arr[i_arr];
				int v = n.getVal();
				if(n.getBelow() == null)	{
					for(int i=prev+1; i<v; i++)	{
						missing.add(i);
					}
				}
				prev = v;
			}
			for(int i=prev+1; i<=N; i++)	{
				missing.add(i);
			}
			return missing;
		}
	}

	static class NumNode implements Comparable<NumNode>	{
		private static final NumNode generic = new NumNode(0);

		private int val;
		private NumNode below;
		private NumNode above;

		public NumNode(int val)	{
			this(val, null, null);
		}
		public NumNode(int val, NumNode below, NumNode above)	{
			this.val = val;
			this.below = below;
			this.above = above;
		}

		public int getVal()			{	return val;			}
		public NumNode getBelow()	{	return below;		}
		public NumNode getAbove()	{	return above;		}
		public boolean isComplete()	{	return below != null && above != null;	}

		public void setVal(int val)			{	this.val = val;		}
		public void setBelow(NumNode below)	{	
			this.below = below;	
		}
		public void setAbove(NumNode above)	{	
			this.above = above;	
		}
		public void swapToGeneric()	{
			if(this.below != null)
				this.below.setAbove(generic);
			if(this.above != null)
				this.above.setBelow(generic);
		}

		public int hashCode()	{
			return this.val;
		}

		public int compareTo(NumNode o)	{
			return this.val - o.val;
		}

		public boolean equals(NumNode o)	{
			return this.compareTo(o) == 0;
		}
	}
}