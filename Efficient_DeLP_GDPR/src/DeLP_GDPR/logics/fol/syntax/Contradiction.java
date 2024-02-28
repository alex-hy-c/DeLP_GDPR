package DeLP_GDPR.logics.fol.syntax;

import DeLP_GDPR.logics.common.LogicalSymbols;

/**
 * A contradictory formula.
 */
public class Contradiction extends SpecialFormula{
	
	/**
	 * Creates a new contradiction.
	 */
	public Contradiction() {
		
	}

	
	@Override
	public String toString() {
		return LogicalSymbols.CONTRADICTION();
	}
	
	
	@Override
	public int hashCode(){
		return 3;
	}
	
	
	@Override
	public boolean equals(Object obj){
		if (obj == null)
			return false;
		if (this == obj)
			return true;		
		if (getClass() != obj.getClass())
			return false;		
		return true;
	}

	@Override
	public Contradiction clone() {
		return new Contradiction();
	}
}
