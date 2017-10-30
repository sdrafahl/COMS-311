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
public class WarWithRollHash
{
	private static final double PRIME = 941;

	int k;
	
	String[] s;
	
	int[] hashValues;
	
	public WarWithRollHash(String[] s, int k)
	{
		this.k = k;
		this.s = s;
		this.hashValues = new int[s.length];
		for(int x=0;x<hashValues.length;x++) {
			hashValues[x] = hashString(s[x]);
		}
	}
	
	public ArrayList<String> compute2k()
	{
		ArrayList<String> list = new ArrayList<String>();
		for(int x=0;x<s.length;x++) {
			for(int y=0;y<s.length;y++) {
				String concat = s[x] + s[y];
				if(checkString(concat)) {
					list.add(concat);
				}
			}
		}
		return list;
	}
	
	boolean checkString(String str) {
		int index0 = 1;
		int index2 = k+1;
		int sum = hashString(str.substring(index0, index2));
		String sub = str.substring(index0, index2);
		boolean match = true;
		while(true) {
			match = false;
			ArrayList<String> matches = new ArrayList<String>();
			for(int x=0;x<hashValues.length;x++) {
				if(sum == hashValues[x]) {
					matches.add(s[x]);
				}
			}
			
			if(matches.contains(sub)) {
				match = true;
			}
			
			if(match == false) {
				return false;
			} else if(index2 == str.length() - k + 1) {
				return true;
			} else {
				sum -= sub.charAt(0);
				sum /= PRIME;
				sum += str.charAt(index2) * Math.pow(PRIME, k-1);
				sub = sub.substring(1, sub.length());
				sub += str.charAt(index2);
				index0++;
				index2++;
			}
		}
	}
	
	private int hashString(String s) {
		int sum = 0;
		for(int x=0;x<s.length();x++) {
			char ch = s.charAt(x);
			sum += ch * Math.pow(PRIME,x); 
		}
		return sum;
	}
	
}

