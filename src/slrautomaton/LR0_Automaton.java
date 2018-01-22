package slrautomaton;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


import grammer.Rule;
import grammer.Symbol;
import utils.Utils;

public class LR0_Automaton {
	
	public ArrayList<State> states = new ArrayList<State>();
	HashMap<String, Symbol> symbols = new HashMap<String, Symbol>();
	HashMap<String, ArrayList<Rule>> rules = new HashMap<String, ArrayList<Rule> >();

	Symbol startSymbol;
	public void readFile (String pathname) throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File(pathname));
		
		String line = scan.nextLine();
		String[]  tokens = line.split(" ");
		
		for ( int i = 0 ; i < tokens.length ; i++){
			Symbol s = new Symbol(true, tokens[i]);
			symbols.put(tokens[i], s);
		}
		
		 line = scan.nextLine();
		  tokens = line.split(" ");
		
		  
		  for ( int i = 0 ; i < tokens.length ; i++){
			  
				Symbol s = new Symbol(false, tokens[i]);
				if ( i==0 )
					startSymbol = s;
				symbols.put(tokens[i], s);
			}

		
			int counter = 1; 
		
		while (scan.hasNext()){
//			System.out.println();
//			System.out.println(counter);
			counter ++; 
			 line = scan.nextLine();
			 tokens = line.split(" ");
			boolean islhs = true;
			Rule rule = new Rule(null, new ArrayList<Symbol>());
			for (int i = 0; i < tokens.length; i++) {
//				System.out.print("t:  " + tokens[i]+"  ");
				if ( tokens[i].compareTo("->") == 0)
				{
					islhs = false;
					continue;
				}
				
				if ( islhs){
					
					Symbol symbol = symbols.get(tokens[i]);
					System.out.println(tokens[i]);
					if (symbol != null)
						rule.setLhs(symbol);
					else
					{
					
						System.err.println("Wrong Symbol at token \t" + tokens[i]);
						System.exit(0);
					}
					
				}else{
					
					Symbol symbol = symbols.get(tokens[i]);
					if (symbol != null){
						ArrayList<Symbol> rhs =  rule.getRhs();
						rhs.add(symbol);
						rule.setRhs(rhs);
					}
					else
					{
						System.err.println("Wrong Symbol at token \t" + tokens[i]);
						System.exit(0);
					}
					
					
				}
				
				
			}
			if ( rules.get(rule.getLhs().getValue()) == null){
				ArrayList<Rule> rs = new ArrayList<Rule>();
				rs.add(rule);
				rules.put(rule.getLhs().getValue(), rs);
			}else
			{
				ArrayList<Rule> rs = rules.get(rule.getLhs().getValue());
				rs.add(rule);
				rules.put(rule.getLhs().getValue(), rs);
			}
			
			
		}
//		System.out.println(rules);
		
	}
	
	
	public void InitAutomaton(){
		State state0 = new State();
		state0.setNumber(0);
		
		ArrayList<Link> links  = new ArrayList<Link>();
		ArrayList<Rule> newRules = this.rules.get(startSymbol.getValue());
		
		for ( int i = 0 ;  i < newRules.size() ; i++)
		{
			state0 = addRulesToState(state0, newRules.get(i), 0);
			
		}
		states.add(state0);
		
		ArrayList<State> remainingStates = new ArrayList<State>();
		remainingStates.add(state0);
		while (remainingStates.size() > 0){
			System.out.println("remaining is \t " + remainingStates.size());
			System.out.println("actual is \t" + states.size());
			State currentState = remainingStates.get(0);
			remainingStates.remove(0);
//	TODO :  get all transaction states 
			HashMap<Symbol, ArrayList<Rule>> nextTokens = getNextToken(currentState);
			
			for(Symbol nextSymbol : nextTokens.keySet()){
				State newState = new State();
				newState.setNumber(states.size());
//				if (states.size() == 12)
//					System.exit(0);
//				ArrayList<Integer> tempIndexes = new ArrayList<Integer>();	
				
				for ( int i = 0 ; i < nextTokens.get(nextSymbol).size() ; i++){
					
					Rule tempRule = nextTokens.get(nextSymbol).get(i);
					for ( int j = 0 ; j< currentState.getDots().get(tempRule).size() ; j++){
						if (currentState.getDots().get(tempRule).get(j) != tempRule.getRhs().size()){
							
							if ( tempRule.getRhs().get(currentState.getDots().get(tempRule).get(j)).equals(nextSymbol)){
//								tempIndexes.add(currentState.getDots().get(tempRule).get(j) + 1);
								newState = addRulesToState(newState, tempRule, currentState.getDots().get(tempRule).get(j) + 1);
							}
								
						}
						
					}
					
					
				}
//				check whether the new state is real
				if ( states.contains(newState) ){
					State tmpState = states.get(states.indexOf(newState));
					Link link = new Link(currentState,tmpState , nextSymbol);
					currentState.getLinks().add(link);
					
					
				}else {
					
					Link link = new Link(currentState, newState, nextSymbol);
					currentState.getLinks().add(link);
					remainingStates.add(newState);
					states.add(newState);

					
					
					
				}
				
				
				
			}
			
			
//		TODO  : 	check whether this state is already exists 
			
			
		}
		
//		sysout states
		
		
	}
	private HashMap<Symbol, ArrayList<Rule>> getNextToken(State state){
		HashMap<Symbol, ArrayList<Rule>> firstTokens = new HashMap<Symbol, ArrayList<Rule>>();
		for ( Rule rule : state.getDots().keySet()){
			
//			finding index of next tokens
			ArrayList<Integer> nxtIndexes =state.getDots().get(rule);
			for ( int i = 0 ; i < nxtIndexes.size() ; i ++){
				if ( nxtIndexes.get(i) != rule.getRhs().size())
					if (firstTokens.get( rule.getRhs().get(nxtIndexes.get(i))) == null){
						ArrayList<Rule> temp = new ArrayList<Rule>();
						temp.add(rule);
						firstTokens.put(rule.getRhs().get(nxtIndexes.get(i)),temp );
					}else {
						
						ArrayList<Rule> temp = firstTokens.get(rule.getRhs().get(nxtIndexes.get(i)));
						if (! temp.contains( rule))
							temp.add(rule);
						firstTokens.put(rule.getRhs().get(nxtIndexes.get(i)),temp );
							
					}
			}
			
			
			
			
		}
		
		return firstTokens;
	}
	
	private State  addRulesToState (State state  , Rule rule , int index ){
	if ( rule.getRhs().size() != index){
		if ( state.getDots().get(rule) == null){
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(index);
			state.getDots().put(rule, list );
			
			Symbol nxtSymbol = rule.getRhs().get(index);
			if ( nxtSymbol.getIsTerminal() == false){
				ArrayList<Rule> newRules = this.rules.get(nxtSymbol.getValue());
				for (int i =0 ; i < newRules.size() ; i ++){
					state = addRulesToState(state, newRules.get(i), 0);
				}
				return state; 
			}

		}else {
			
			ArrayList<Integer> 	list = state.getDots().get(rule);
			if ( ! list.contains(index))
			{
				list.add(index);
				state.getDots().put(rule, list);
				Symbol nxtSymbol = rule.getRhs().get(index);
//				if it is non terminal 
				if (nxtSymbol.getIsTerminal() == false){
					ArrayList<Rule> newRules = this.rules.get(nxtSymbol.getValue());
					for (int i =0 ; i < newRules.size() ; i ++){
					state = addRulesToState(state, newRules.get(i), 0);
					}
					
				}
			}else return state;
			
			
			
		}
	}else {
		
		if ( state.getDots().get(rule) == null){
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(index);
			state.getDots().put(rule, list);
			
		}else {
			
			ArrayList<Integer> list = state.getDots().get(rule);
			list.add(index);
			state.getDots().put(rule, list);
		}
		
	}
		
		return state;
		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
	LR0_Automaton automata = new LR0_Automaton();
	automata.readFile("/Users/sina/Documents/workspace/SLRAUTOMATON/input1.txt");
	
	automata.InitAutomaton();
	for (int i=0 ; i< automata.states.size() ; i ++){
		System.out.println(automata.states.get(i));
	}
	System.out.println("exit");
	}
}
