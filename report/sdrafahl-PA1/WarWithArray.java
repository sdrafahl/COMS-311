// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.util.ArrayList;

/**
 * 
 * @author Shane Drafahl
 *
 */
public class WarWithArray
{
	private String[] s;
	
	private int k;
	
	public WarWithArray(String[] s, int k)
	{
		this.s = s;
		this.k = k;
	}
	
	public ArrayList<String> compute2k() {
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
			for(int v=0;v<this.s.length;v++) {
				if(this.s[v].equals(temp)) {
					return true;
				}
			}
			return false;
			//return this.s.contains(temp);
		}
	}
}

