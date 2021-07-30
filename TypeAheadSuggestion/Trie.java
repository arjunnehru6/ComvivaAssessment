package com.accolite.assessment.typeAhead;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Trie {

    class Node {
        boolean isWord = false;
        List<Node> children = Arrays.asList(new Node[26]);
		public int noOfHits;
		
    };
   
    Node Root, curr;
    List<TrieFreq> resultBuffer;

    void dfsWithPrefix(Node curr, String word) {
        if (curr.isWord)
            resultBuffer.add(new TrieFreq(word,curr.noOfHits));

        for (char c = 'a'; c <= 'z'; c++)
            if (curr.children.get(c - 'a') != null)
                dfsWithPrefix(curr.children.get(c - 'a'), word + c);
    }
    Trie() {
        Root = new Node();
    }
    
    void insert(String s) {

        curr = Root;
        for (char c : s.toCharArray()) {
            if (curr.children.get(c - 'a') == null)
                curr.children.set(c - 'a', new Node());
            curr = curr.children.get(c - 'a');
        }
        
        curr.isWord = true;
		curr.noOfHits = curr.noOfHits == 0 ? 1 : curr.noOfHits+1;
    }
    
    List<TrieFreq> getWordsStartingWith(String prefix) {
        curr = Root;
        resultBuffer = new ArrayList<>();
        for (char c : prefix.toCharArray()) {
            if (curr.children.get(c - 'a') == null)
                return resultBuffer;
            curr = curr.children.get(c - 'a');
        }
        dfsWithPrefix(curr, prefix);
        return resultBuffer;
    }
    
    public static List<String> getTopSuggetions(List<TrieFreq> list) {
		List<TrieFreq> sortSuggetionsByFrequency = list.stream()
				.sorted(Comparator.comparingInt(TrieFreq::getCount).reversed()).collect(Collectors.toList());
		
		List<String> topSuggetions = sortSuggetionsByFrequency.stream().map(wordFrequency -> wordFrequency.getWord())
				.collect(Collectors.toList());
		return topSuggetions;

	}
    
    static List<String> suggestedWord(List<String> dict,
            String searchWord) {
			Trie trie = new Trie();
			List<TrieFreq> result = new ArrayList<>();
			for (String w : dict)
			trie.insert(w);
			result.addAll(trie.getWordsStartingWith(searchWord));
			
			return getTopSuggetions(result);
			}
    
    public static void main(String[] args) {
    	List<String> dict = new ArrayList<>();
    	dict.add("apple");
    	dict.add("bags");
    	dict.add("baggage");
    	dict.add("banner");
    	dict.add("box");
    	dict.add("cloths");
    	dict.add("mobile");
    	dict.add("mouse");
    	dict.add("moneypot");
    	dict.add("monitor");
    	dict.add("mice");
    	dict.add("hello");
    	dict.add("hi");
    	dict.add("table");
    	TrieFreq[] dictData = new TrieFreq[] { new TrieFreq("laptop", 200),	new TrieFreq("bill", 300), new TrieFreq("tape", 300),
				new TrieFreq("clock", 150) };
    	for(TrieFreq data : dictData) {
    		for(int i=0;i<data.getCount();i++) {
    			dict.add(data.getWord());
    		}		
		}
		String searchWord = "c";
		List<String> suggestion = suggestedWord(dict,searchWord);
		for(String word : suggestion) {
			System.out.println(word);
		}
	}
}
