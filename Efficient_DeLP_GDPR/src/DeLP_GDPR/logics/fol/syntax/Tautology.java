package DeLP_GDPR.logics.fol.syntax;

import DeLP_GDPR.logics.common.LogicalSymbols;

/**
 * A tautological formula.
 */
public class Tautology extends SpecialFormula {

	/**
	 * Creates a new tautology.
	 */
	public Tautology() {		
	}

	
	@Override
	public String toString() {
		return LogicalSymbols.TAUTOLOGY();
	}
	
	
	@Override
	public int hashCode(){
		return 5;
	}
	
	
	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;		
		if (obj == null || getClass() != obj.getClass())
			return false;		
		return true;
	}

	@Override
	public Tautology clone() {
		return new Tautology();
	}
}
