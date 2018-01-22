package grammer;

import java.util.ArrayList;

public class Rule {
  /**
   * 
   *  created by @sina
   * 
   * 
   * rule : lhs -> rhs
   * rule(lhs,rhs)
   * 
   * 
   */
	
	

	private Symbol lhs; 
	private ArrayList<Symbol> rhs ; 
	
	
	public Rule ( Symbol lhs , ArrayList<Symbol> rhs){
		
		this.lhs = lhs;
		this.rhs = rhs;
		
	}


	public Symbol getLhs() {
		return lhs;
	}


	public void setLhs(Symbol lhs) {
		this.lhs = lhs;
	}


	public ArrayList<Symbol> getRhs() {
		return rhs;
	}


	public void setRhs(ArrayList<Symbol> rhs) {
		this.rhs = rhs;
	}

	
	@Override
		public boolean equals(Object obj) {
			
			if( obj.getClass().equals(this.getClass()) && this.lhs.equals( ((Rule)obj).lhs))
			{
				if ( rhs.size() ==  ((Rule)obj).getRhs().size()  )
				{
					ArrayList<Symbol>	array = ( (Rule)obj).rhs;
					for (int i = 0; i < array.size(); i++) {
						if ( ! rhs.get(i).equals(array.get(i))) return false;
					}
					return true;	
				}
			}
			
			return false;
		
		}
	@Override
		public String toString() {
			String rh = "";
			for ( int i = 0 ; i< rhs.size() ; i++)
				if ( i != rhs.size() - 1)
				rh = rh + rhs.get(i).getValue() +" ";
				else 
					rh = rh + rhs.get(i).getValue();
			
			
			return lhs.getValue() + "->" + rh	;
		}
	public String dotString(int idx){
		
		String rh = "";
		rh = rh + this.lhs + " -> ";
		for ( int i = 0 ; i < rhs.size();  i++){	
			if ( idx == i ){
				rh =  rh +"."+ rhs.get(i) + " ";
			}else 
				rh = rh + rhs.get(i) +" ";
		}
		if ( idx == rhs.size())
			rh += ".";
		
		return rh;
	}

	 @Override
	    public int hashCode()
	    {
	        int hash = 7;
	        hash = 67 * hash + this.lhs.hashCode();
	        int temp  =0; 
	        for ( int i =0 ; i < rhs.size() ; i++)
	        	temp+= rhs.get(i).hashCode();
	        hash = 67 * hash + temp;
	        return hash;
	    }
	
}
