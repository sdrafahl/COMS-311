import java.util.ArrayList;
import java.util.Stack;

// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

/**
 * 
 * @author Shane Drafahl
 *
 */
public class BinaryST
{
	private Node root;
	
	private int unique;
	
	private int size;
	
	public BinaryST()
	{
		root = null;
		this.size = 0;
		this.unique = 0;
	}
	
	public BinaryST(String[] s)
	{
		root = null;
		for(int x=0;x<s.length;x++) {
			if(root == null) {
				root = new Node(s[x]);
				size++;
				unique++;
			} else {
				this.add(s[x]);
			}
		}
	}
	
	private int placeNode(Node n, String d) {
		Node node  = new Node(d);
		if(n.data.compareTo(d) == 0) {
			n.quantity++;
			n.size++;
			this.updateHeight(n);
			return 0;
		}
		if(n.data.compareTo(d) > 0) {
			if(n.left == null) {
				n.left = node;
				node.parent = n;
				updateHeight(node.parent);
				return 1;
			} else {
				return placeNode(n.left, d);
			}
		} else {
			if(n.right == null) {
				n.right = node;
				node.parent = n;
				updateHeight(node.parent);
				return 1;
			} else {
				return placeNode(n.right, d);
			}
		}
	}
	
	private void updateHeight(Node n) {
		if(n.left != null && n.right != null) {
			if(n.left.height - n.right.height > 0 ) {
				n.height = n.left.height + 1;
			} else {
				n.height = n.right.height + 1;
			}
			n.size = n.left.size + 1 + n.right.size;
		} else if(n.left == null && n.right != null) {
			n.height = n.right.height + 1;
			n.size = n.right.size + 1;
		} else if(n.left != null && n.right == null) {
			n.height = n.left.height + 1;
			n.size = n.left.size + 1;
		} else {
			n.height = 0;
			n.size = 1;
		}
		if(n.parent != null) {
			updateHeight(n.parent);
		}	
	}
	
	public int distinctSize()
	{
		return this.unique;
	}
	
	public int size()
	{
		return this.size;
	}
	
	public int height()
	{
		if(this.root == null) {
			return 0;
		} else {
			return this.root.height + 1;
		}
	}
	
	public void add(String s)
	{
		if(root == null) {
			root = new Node(s);
			this.unique++;
			this.size++;
			return;
		}
		int result = placeNode(root, s);
		switch(result) {
			case 0 :
				this.size++;
			break;
			
			case 1: 
				this.unique++;
				this.size++;
			break;
		}
	}
	
	public boolean search(String s)
	{
		Node temp = this.root;
		while(true) {
			if(temp == null) {
				return false;
			}
			if(temp.data.equals(s)) {
				return true;
			}
			if(temp.data.compareTo(s) > 0) {
				temp = temp.left;
			} else {
				temp = temp.right;
			}
		}
	}
	
	public int frequency(String s)
	{
		Node temp = this.root;
		while(true) {
			if(temp == null) {
				return 0;
			}
			if(temp.data.equals(s)) {
				return temp.quantity;
			}
			if(temp.data.compareTo(s) > 0) {
				temp = temp.left;
			} else {
				temp = temp.right;
			}
		}
	}
	
	public boolean remove(String s)
	{
		Node temp = this.root;
		while(true) {
			if(temp == null) {
				return false;
			}
			if(temp.data.equals(s)) {
				this.size--;
				if(temp.quantity > 1) {
					temp.quantity--;
					temp.size--;
					this.updateHeight(temp);
				} else {
					removeNode(temp);
					this.unique--;
				}
				return true;
			}
			if(temp.data.compareTo(s) > 0) {
				temp = temp.left;
			} else {
				temp = temp.right;
			}
		}
	}
	
	private void removeNode(Node n) {
		/* The root is removed */
		if(n.parent == null) {
			
			Node newRoot = n.right;
			Node left = n.left;
			this.root = newRoot;
			Node temp = newRoot;
			while(true) {
				if(temp.left == null) {
					temp.left = left;
					left.parent = temp;
					newRoot.parent = null;
					break;
				}
				temp = temp.left;
			}
			Node toe = n.right;
			while(true) {
				if(toe.left == null && toe.right == null) {
					break;
				}
				if(toe.right != null && (toe.left == null || toe.right.height > toe.left.height)) {
					toe = toe.right;
				} else {
					toe = toe.left;
				}
			}
			this.updateHeight(toe);
			return;
		}
		
		if(n.left == null && n.right == null) {
			if(n.parent.right != null && (n.parent.left == null || n.parent.right.data.equals(n.data))) {
				n.parent.right = null;
			} else {
				n.parent.left = null;
			}
			this.updateHeight(n.parent);
			return;
		}
		
		if(n.right != null && n.parent != null && n.left != null) {
			Node p = n.parent;
			if(p.right.data.equals(n.data)) {
				p.right = n.right;
				n.right.parent = p;
				Node temp = p.right;
				while(true) {
					if(temp.left == null) {
						temp.left = n.left;
						n.left.parent = temp;
						break;
					}
					temp = temp.left;
				}
				Node toe = n.right;
				while(true) {
					if(toe.left == null && toe.right == null) {
						break;
					}
					if(toe.right != null && (toe.left == null || toe.right.height > toe.left.height)) {
						toe = toe.right;
					} else {
						toe = toe.left;
					}
				}
				this.updateHeight(toe);
			} else {
				p.left = n.right;
				p.left.parent = p;
				Node temp = n.right;
				while(true) {
					if(temp.left == null) {
						temp.left = n.left;
						n.left.parent = temp;
						break;
					}
					temp = temp.left;
				}
				Node toe = n.right;
				while(true) {
					if(toe.left == null && toe.right == null) {
						break;
					}
					if(toe.right != null && (toe.left == null || toe.right.height > toe.left.height)) {
						toe = toe.right;
					} else {
						toe = toe.left;
					}
				}
				this.updateHeight(toe);
			}
			return;
		}
		
		if(n.parent != null && n.left == null && n.right != null) {
			n.right.parent = n.parent;
			if(n.parent.right.data.equals(n.data)) {
				n.parent.right = n.right;
			} else {
				n.parent.left = n.left;
			}
			
			Node toe = n.right;
			while(true) {
				if(toe.left == null && toe.right == null) {
					break;
				}
				if(toe.right != null && (toe.left == null || toe.right.height > toe.left.height)) {
					toe = toe.right;
				} else {
					toe = toe.left;
				}
			}
			this.updateHeight(toe);
			return;
		}
		
		if(n.parent != null && n.left != null && n.right == null) {
			n.left.parent = n.parent;
			if(n.parent.right.data.equals(n.data)) {
				n.parent.right = n.left;
			} else {
				n.parent.left = n.left;
			}
			Node toe = n.left;
			while(true) {
				if(toe.left == null && toe.right == null) {
					break;
				}
				if(toe.right != null && (toe.left == null || toe.right.height > toe.left.height)) {
					toe = toe.right;
				} else {
					toe = toe.left;
				}
			}
			this.updateHeight(toe);
			return;
		}
		
	}
	
	public String[] inOrder()
	{
		ArrayList<String> list = new ArrayList<String>();
		this.visitSubTree(root, list);
		return list.toArray(new String[list.size()]);
	}
	
	private void visitSubTree(Node n, ArrayList<String> list) {
		if(n.left != null) {
			visitSubTree(n.left, list);
		}
		for(int x=0;x<n.quantity;x++) {
			list.add(n.data);
		}
		if(n.right != null) {
			visitSubTree(n.right, list);
		}
	}
	
	
	public String[] preOrder()
	{
		ArrayList<String> list = new ArrayList<String>();
		this.visitSubTreePreOrder(this.root, list);
		return list.toArray(new String[list.size()]);
	}
	
	private void visitSubTreePreOrder(Node n, ArrayList<String> list) {
		for(int x=0;x<n.quantity;x++) {
			list.add(n.data);
		}
		if(n.left != null) {
			visitSubTreePreOrder(n.left, list);
		}
		if(n.right != null) {
			visitSubTreePreOrder(n.right, list);
		}
	}
	
	public int rankOf(String s)
	{
		Node temp = root;
		int rank = 0;
		while(temp != null) {
			if(temp.data.compareTo(s) > 0) {
				if(temp.left == null) {
					return -1;
				}
				temp = temp.left;
			} else if(temp.data.compareTo(s) < 0) {
				if(temp.left != null) {
					rank += temp.quantity + temp.left.size;
				} else {
					rank += temp.quantity;
				}
				temp = temp.right;
			} else {
				if(temp.left != null) {
					if(temp.left == null) {
						return -1;
					}
					rank += temp.left.size;
				}
				return rank;
			}
		}
		return -1;
	}
	
	class Node {
		
		String data;
		
		int quantity;
		
		int height;
		
		int size;
		
		Node left;
		
		Node right;
		
		Node parent;
		
		public Node(String data) {
			this.data = data;
			this.quantity = 1;
			size = 1;
			parent = null;
			left = null;
			right = null;
			this.height = 0;
		}
	}
	
}