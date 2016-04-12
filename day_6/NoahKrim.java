import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.util.regex.*;

public class NoahKrim {
	public static void main(String[] args)	{
		Scanner in;
		Path fout = null;
		if(args.length == 1)	{
			Pattern p = Pattern.compile("^random\\((\\d+),(\\d+)\\)$");
			Matcher m = p.matcher(args[0]);
			if(m.matches())	
				in = new Scanner(makeRandomInput(m.group(1), m.group(2)));
			else
				in = new Scanner(Paths.get(args[0]));
			System.out.println("Reading from "+args[0]);
			System.out.println("Writing to system.out");
		}
		else if(args.length == 2)	{
			Pattern p = Pattern.compile("^random\\((\\d+),(\\d+)\\)$");
			Matcher m = p.matcher(args[0]);
			if(m.matches())	
				in = new Scanner(makeRandomInput(m.group(1), m.group(2)));
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
		NumsMap map = new NumsMap(N);
		for(int i=0; i<N; i++)	{
			int n = Integer.parseInt(in.nextLine());
			map.add(n);
		}
		ArrayList<Integer> missing = map.getMissing();
		if(missing.isEmpty())	{
			System.out.println("None missing");
		}
		else	{
			Strint out = "Missing: ";
			for(int temp : missing)	{
				out += temp + ", ";
			}
			out = out.substring(0,out.length()-2);
			System.out.println(out);
		}
	}

	private static Path makeRandomInput(int N, int numMissing) throws Exception	{
		Path p = Paths.get("random.in");
		ArrayList<Integer> possibleNums = new ArrayList<Integer>();
		for(int i=1; i<=N; i++)	{
			possibleNums.add(i);
		}
		int numNums = N - numMissing;
		String[] arr = new String[numNums];
		for(int i=0; i<numNums; i++)	{
			int index = (int)(Math.random()*possibleNums.size());
			int n  = possibleNums.get(index);
			arr[i] = Integer.toString(n));
			possibleNums.remove(index);
		}
		Files.write(p, Arrays.asList(arr), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		return p;
	}

	static class NumsMap	{
		private int N;
		private HashMap<Integer, NumNode> map;
		private NumNode generic;

		public NumsMap(int N)	{
			this.N = N;
			map = new HashMap<>();
			generic = new NumNode(0);
		}

		public void add(int n)	{
			NumNode node = new NumNode(n);
			NumNode a, b;
			if((a = map.get(n+1)) != null)	{
				node.setAbove(a);
				if(a.isComplete())	{
					map.remove(a.getVal());
					a.swapToGeneric(generic);
				}
			}
			if((b = map.get(n-1)) != null)	{
				node.setBelow(b);
				if(b.isComplete())	{
					map.remove(b.getVal());
					b.swapToGeneric(generic);
				}
			}
			if(node.isComplete())	{
				map.remove(n);
				n.swapToGeneric(generic);
			}
		}

		public ArrayList<Integer> getMissing()	{
			ArrayList<Integer> missing = new ArrayList<>();
			Collection<NumNode> vals = map.values();
			Collections.sort(vals);
			int prev = 1;
			for(Iterator<NumNode> it = vals.iterator(); it.hasNext();)	{
				NumNode n = it.next();
				NumNode v = n.getVal();
				if(n.getBelow() == null)	{
					for(int i=prev; i<v; i++)	{
						missing.add(i);
					}
				}
				prev = v;
			}
			for(int i=prev; i<=N; i++)	{
				missing.add(i);
			}
			return missing;
		}
	}

	static class NumNode implements Comparable<NumNode>	{
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
		public void swapToGeneric(NumNode generic)	{
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