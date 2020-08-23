/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING ANY
SOURCES OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. Nathan Yang
*/


import java.io.*;
import java.util.*;

public class Autocomplete extends AbstractAutocomplete<List<String>> {



	public Autocomplete(String dict_path, int max) {
		super(dict_path, max);
	}



	public List<String> getCandidates(String prefix) {
		List<String> candidates = new ArrayList<String>();
		getCandidatesRecur(candidates, prefix.trim(), prefix.trim());
		return candidates;
	}



	private void getCandidatesRecur(List<String> candidates, String prefix, String origPrefix) {
		TrieNode<List<String>> node = find(prefix);
		String s = prefix;
		if(node == null) {
			System.out.println("Not in dictionary!");
			return;
		}

		Map<Character, TrieNode<List<String>>> childMap = node.getChildrenMap();
		Iterator<Map.Entry<Character, TrieNode<List<String>>>> itr = childMap.entrySet().iterator(); 
		
		while(itr.hasNext()) 
        { 
            Map.Entry<Character, TrieNode<List<String>>> entry = itr.next();
            TrieNode<List<String>> node1 = node.getChild(entry.getKey());
            s += node1.getKey();
            
            if(node1.isEndState()) {
            	
            	if(candidates.size() == getMax()) {
            		if(compare(s, candidates.get(getMax()-1), origPrefix) < 0) candidates.set(getMax()-1, s);
            	}
            	
            	else candidates.add(s);
            	for(int i=candidates.size()-1; i>0; i--) {
            		if(compare(candidates.get(i), candidates.get(i-1), origPrefix) < 0) {
            			Collections.swap(candidates, i, i-1);
            		}
            	}
            }
           
            getCandidatesRecur(candidates, s, origPrefix);
            s = s.substring(0, s.length()-1);
        }
		return;
	}


	
	private int compare(String s1, String s2, String prefix) {
		TrieNode<List<String>> node = find(prefix);
		
		if(node.hasValue()) {
			List<String> recent = node.getValue();
			
			for(int i=recent.size()-1; i>=0; i--) {
				if(s1.equals(recent.get(i))) return -1;
				if(s2.equals(recent.get(i))) return 1;
			}
		}
		
		if(s1.length() != s2.length()) return s1.length()-s2.length();
		return s1.compareTo(s2);
	}


	
	public void pickCandidate(String prefix, String candidate) {
		TrieNode<List<String>> node = find(prefix);
		
		if(node == null) {
			System.out.println("Not in dictionary!");
			return;
		}
		
		List<String> recent = new ArrayList<String>();
		if(node.hasValue()) recent = node.getValue();
		recent.add(candidate);
		node.setValue(recent);
	}

	

	public static void main(String[] args) {
		Autocomplete ac = new Autocomplete("./dict.txt", 10);
		Scanner scan = new Scanner(System.in);
		String cont = "Y";

		while(cont.equals("Y")) {
			System.out.println(" ");
			System.out.println("Input a prefix:");
			String prefix = scan.nextLine();
			System.out.println(" ");

			System.out.println("Autocomplete List:");
			List<String> candidates = ac.getCandidates(prefix);
			Iterator iter = candidates.iterator();
			while(iter.hasNext()) {
				System.out.println(iter.next());
			}

			System.out.println(" ");
			System.out.println("Select a word from the list:");
			String autocomplete = scan.nextLine();
			ac.pickCandidate(prefix, autocomplete);

			System.out.println(" ");
			System.out.println("Continue? (Y, N)");
			cont = scan.nextLine();
		}
	}



}

