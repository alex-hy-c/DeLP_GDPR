package DeLP_GDPR.dung.ldo.syntax;



import java.util.HashSet;

import java.util.Set;

import DeLP_GDPR.dung.syntax.DungSignature;
import DeLP_GDPR.logics.common.LogicalSymbols;
import DeLP_GDPR.logics.pl.syntax.PlPredicate;

/**
 * This class models classical negation of ldo logic.
 * 
 */
public class LdoNegation extends LdoFormula {

	/**
	 * The formula within this negation.
	 */
	private LdoFormula formula;
	
	/**
	 * Creates a new negation with the given formula.
	 * @param formula the formula within the negation.
	 */
	public LdoNegation(LdoFormula formula){
		this.formula = formula;	
	}
	
	/**
	 * Returns the formula within this negation.
	 * @return the formula within this negation.
	 */
	public LdoFormula getFormula(){
		return this.formula;
	}
		
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		if(this.formula instanceof LdoAssociativeFormula || this.formula instanceof LdoNegation)			
			return LogicalSymbols.CLASSICAL_NEGATION() + "(" + this.formula + ")";
		return LogicalSymbols.CLASSICAL_NEGATION() + this.formula;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formula == null) ? 0 : formula.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LdoNegation other = (LdoNegation) obj;
		if (formula == null) {
			if (other.formula != null)
				return false;
		} else if (!formula.equals(other.formula))
			return false;
		return true;
	}

	@Override
	public Set<PlPredicate> getPredicates() {
		return formula.getPredicates();
	}

	@Override
	public LdoFormula clone() {
		return new LdoNegation(formula.clone());
	}

	@Override
	public Set<LdoArgument> getAtoms() {
		return formula.getAtoms();
	}

	@Override
	public boolean isLiteral() {
		return (formula instanceof LdoArgument);
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.logics.pl.syntax.PropositionalFormula#getLiterals()
	 */
	@Override
	public Set<LdoFormula> getLiterals(){
		Set<LdoFormula> result = new HashSet<LdoFormula>();
		if(this.isLiteral())			
			result.add(this);
		return result;
	}
	
	@Override
	public DungSignature getSignature() {
		return formula.getSignature();
	}
}
