// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.util.ArrayList;


public class WarWithBST
{
	Node root;
	
	String[] s;
	
	int k;
	
	public WarWithBST(String[] s, int k)
	{
		this.s = s;
		this.k = k;
		this.root = new Node(s[0]);
		for(int x=1;x<s.length;x++) {
			Node temp = root;
			Node newNode = new Node(s[x]);
			while(true) {
				if(temp.data.compareTo(newNode.data) < 0) {
					newNode.parent = temp;
					if(temp.right == null) {
						temp.right = newNode;
						break;
					} else {
						temp = temp.right;
					}
				} else {
					if(temp.left == null) {
						temp.left = newNode;
						break;
					} else {
						temp = temp.left;
					}
				}
			}
		}
	}
	
	private boolean contains(String str) { // BST method
		if(root == null) {
			return false;
		} else {
			Node r = root;
			while(true) {
				if(r.data.equals(str)) {
					return true;
				} else {
					if(r.data.compareTo(str) < 0) {
						if(r.right == null) {
							return false;
						} else {
							r = r.right;
						}
					} else {
						if(r.left == null) {
							return false;
						} else {
							r = r.left;
						}
					}
				}
			}
		}
	}
	
	public ArrayList<String> compute2k()
	{
		ArrayList<String> list = new ArrayList<String>();
		for(int x=0;x<s.length;x++) {
			for(int y=0;y<s.length;y++) {
				String concat = s[x] + s[y];
				if(testString(concat)) {
					list.add(s[x] + s[y]);
				}
			}
		}
		return list;
	}
	
	private boolean testString(String s) {
		for(int x=1;x<s.length() - k;x++) {
			if(!isSubSet(s, x)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isSubSet(String s, int x) {
		if(x + k >= s.length()) {
			return false;
		} else {
			String temp = "";
			for(int y=x;y < x + k;y++) {
				temp += s.charAt(y);
			}
			return contains(temp);
		}
	}
	
	class Node {
		
		String data;
		
		Node left;
		
		Node right;
		
		Node parent;
		
		public Node(String st) {
			this.data = st;
		}
	}
}

