package slrautomaton;

import java.util.ArrayList;
import java.util.HashMap;

import grammer.Rule;
//TODO : tostring equals
public class State {
	private int number; 
	private ArrayList<Rule> rules;
	private ArrayList<Link> links;
	private HashMap<Rule, ArrayList<Integer>> dots = new HashMap<Rule, ArrayList<Integer>>();
	public State(int number , ArrayList<Rule> rules , ArrayList<Link> links){
		this.number = number;
		this.rules = rules; 
		this.links = links;
		
	}
	public State (){
		
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public ArrayList<Rule> getRules() {
		return rules;
	}

	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}

	public ArrayList<Link> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<Link> links) {
		this.links = links;
	}
	
	public HashMap<Rule	, ArrayList<Integer>> getDots (){
		return dots;
	}
	
	public void setDots ( HashMap<Rule	, ArrayList<Integer>> dots){
		this.dots = dots;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		
		State other = (State) obj;
		if ( other.dots.size() != this.dots.size())
			return false;
		for ( Rule rule : other.dots.keySet()){
			ArrayList<Integer>othrIndexes = other.dots.get(rule);
			ArrayList<Integer>indexes = this.dots.get(rule);
			if ( indexes == null)
				return false;
			if ( indexes.size() != othrIndexes.size())
				return false;
			for ( int i = 0 ; i < indexes.size() ; i ++)
			{
				if ( !indexes.contains(othrIndexes.get(i)))
					return false;
				
			}
				
			
			
		}
		return true;
	}
	@Override
	public String toString() {
		String str = "S"+this.number + ": {\n";
		for ( Rule rule : dots.keySet()){
			
			ArrayList<Integer> indexes = dots.get(rule);
			for  ( int i = 0 ; i < indexes.size() ; i ++)
			{
				str = str + rule.dotString(indexes.get(i)) + "\n";
			}
			
			
			
		}
		
		str += "}\n";
		
		return str;
		
	}

}
