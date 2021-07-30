package com.accolite.assessment.typeAhead;

public class TrieFreq {
	private String word;
	private int count;
	
	public TrieFreq(String word, int count) {
		this.word = word;
		this.count = count;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
}
