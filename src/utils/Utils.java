package utils;

import java.util.ArrayList;

import grammer.Symbol;

public class Utils {
	
	
	
	public static int getKindOfSymbol (String value ){
//		0 -> terminal  1-> nonterminal
		if( getTerminalSymbol(value) != null)
			return 0; 
		return 1;
	}
	
	public static Symbol getTerminalSymbol(String value){
		
		for (int i = 0; i < Constants.terminals.size(); i++) {
			if (Constants.terminals.get(i).getValue().compareTo(value) == 0 )
				return Constants.terminals.get(i);
			
		}
		return null ;
	}

	
	
public static Symbol getNonTerminalSymbol(String value){
		
		for (int i = 0; i < Constants.terminals.size(); i++) {
			if (Constants.nonTerminals.get(i).getValue().compareTo(value) == 0 )
				return Constants.nonTerminals.get(i);
		}
		return null ;
	}

}
