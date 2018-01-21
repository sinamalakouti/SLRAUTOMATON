package grammer;

public class Symbol {
	
	private Boolean isTerminal; 
	private String value; 
	
	public Symbol (boolean isTerminal, String  value ){
		
		this.isTerminal = isTerminal;
		this.value = value; 
	}

	public Boolean getIsTerminal() {
		return isTerminal;
	}

	public void setIsTerminal(Boolean isTerminal) {
		this.isTerminal = isTerminal;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		
		
		if ( obj.getClass().equals(this.getClass()) &&
				this.value.compareTo(((Symbol) obj ).value) == 0 &&
				this.isTerminal == ((Symbol)obj).isTerminal)
			return true;
		return false;
		
	}
	@Override
	public String toString() {
		
		return this.value;
	}
	
	public Symbol get ( String value){
		if ( this.value.compareTo(value) == 0)
			return this;
		return null;
	}
}
