package slrautomaton;

import java.util.Objects;

import grammer.Symbol;

public class Link {
	private State from,to;
	private Symbol on;	
	
	
	public Link(State from, State to , Symbol on ) {
		this.from = from ;
		 this.to = to;
		 this.on = on;
	}
	
	
	
//	TODO : handling Equals
	@Override
	public boolean equals(Object obj) {
		Link temp = (Link) obj;
		if ( obj.getClass().equals(this.getClass()) && this.on.equals(temp.on)  )
		return true;
		
		
		return false;
		
//		return super.equals(obj);
	}



	public State getFrom() {
		return from;
	}



	public void setFrom(State from) {
		this.from = from;
	}



	public State getTo() {
		return to;
	}



	public void setTo(State to) {
		this.to = to;
	}



	public Symbol getOn() {
		return on;
	}



	public void setOn(Symbol on) {
		this.on = on;
	}
	

}
