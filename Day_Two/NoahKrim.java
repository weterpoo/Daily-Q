//time: O(n^2)
//space: O(n^2)

/*
Example:
input: 
1			(t = number of test cases)
5			(n = number of elements)
2 3 5 4 1	(arr = array of elements in the bst)

tree:
				2
		1				3
							5
						4


output:
[2, 1, 3, 5, 4]
[2, 3, 1, 5, 4]
[2, 3, 5, 1, 4]
[2, 3, 5, 4, 1]
*/

import java.io.*;
import java.util.*;

class NoahKrim {
	public static void main(String[] args)	{
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		for(int i_t=0; i_t<t; i_t++)	{
			int n = in.nextInt();
			Node root = null;
			if(n > 0)
				root = new Node(in.nextInt());
			for(int i_n = 1; i_n<n; i_n++)	{
				Node d = new Node(in.nextInt());
				root.add(d);
			}
			ArrayList<ArrayList <Integer>> answers = solve(root, new ArrayList<Node>());
			System.out.println("test "+(i_t+1)+"/"+t+":");
			for(int i=0; i<answers.size(); i++)	{
				ArrayList<Integer> list = answers.get(i);
				String out = "[";
				for(int j=0; j<list.size(); j++)	{
					out += list.get(j)+", ";
				}
				if(out.length() >= 2)
					out = out.substring(0, out.length()-2);
				out += "]";
				System.out.println(out);
			}
		}
	}

	private static ArrayList<ArrayList <Integer>> solve(Node root, ArrayList<Node> startingPoints)	{
		ArrayList<ArrayList<Integer>> listOfAnswers = new ArrayList<>();
		if(root.getLeft() == null && root.getRight() == null && startingPoints.isEmpty())	{
			ArrayList<Integer> temp = new ArrayList<>();
			temp.add(root.getValue());
			listOfAnswers.add(temp);
			return listOfAnswers;
		}

		ArrayList<ArrayList<Integer>> otherListOfAnswers;

		if(root.getLeft() != null)	{
			Node n = root.getLeft();
			ArrayList<Node> temp_startingPoints = new ArrayList<>(startingPoints);
			if(root.getRight() != null)	
				temp_startingPoints.add(root.getRight());
			otherListOfAnswers = solve(n, temp_startingPoints);
			for(int j=0; j<otherListOfAnswers.size(); j++)	{
				ArrayList<Integer> list = otherListOfAnswers.get(j);
				list.add(0, root.getValue());
				listOfAnswers.add(list);
			}
		}

		if(root.getRight() != null)	{
			Node n = root.getRight();
			ArrayList<Node> temp_startingPoints = new ArrayList<>(startingPoints);
			if(root.getLeft() != null)	
				temp_startingPoints.add(root.getLeft());
			otherListOfAnswers = solve(n, temp_startingPoints);
			for(int j=0; j<otherListOfAnswers.size(); j++)	{
				ArrayList<Integer> list = otherListOfAnswers.get(j);
				list.add(0, root.getValue());
				listOfAnswers.add(list);
			}
		}
		
		for(int i=0; i<startingPoints.size(); i++)	{
			Node n = startingPoints.get(i);
			ArrayList<Node> temp_startingPoints = new ArrayList<>(startingPoints);
			temp_startingPoints.remove(i);
			if(root.getLeft() != null)	
				temp_startingPoints.add(root.getLeft());
			if(root.getRight() != null)	
				temp_startingPoints.add(root.getRight());
			otherListOfAnswers = solve(n, temp_startingPoints);
			for(int j=0; j<otherListOfAnswers.size(); j++)	{
				ArrayList<Integer> list = otherListOfAnswers.get(j);
				list.add(0, root.getValue());
				listOfAnswers.add(list);
			}
		}

		return listOfAnswers;
	}

	// Node class
	static class Node {
		// Instance vars
		private int value;
		private Node left;
		private Node right;

		// Constructors
		public Node(int value)	{
			this(value, null, null);
		}
		public Node(int value, Node left, Node right)	{
			this.value = value;
			this.left = left;
			this.right = right;
		}

		// Accessors
		public int getValue()	{	return value;	}
		public Node getLeft()	{	return left;	}
		public Node getRight()	{	return right;	}

		// Modifiers
		public void add(Node n)	{
			if(n.value < this.value)	{
				if(left != null)
					left.add(n);
				else
					left = n;
			}
			else 	{
				if(right != null)
					right.add(n);
				else
					right = n;
			}
		}
	}
}