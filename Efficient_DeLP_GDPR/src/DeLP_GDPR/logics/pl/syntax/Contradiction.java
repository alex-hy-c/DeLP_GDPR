package DeLP_GDPR.logics.pl.syntax;




import java.util.HashSet;
import java.util.Set;

import DeLP_GDPR.logics.common.LogicalSymbols;
import DeLP_GDPR.logics.pl.semantics.PossibleWorld;

/**
 * A contradictory formula.
 */
public class Contradiction extends SpecialFormula{
	
	/**
	 * Creates a new contradiction.
	 */
	public Contradiction() {		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return LogicalSymbols.CONTRADICTION();
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Contradiction;
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public Contradiction clone() {
		return new Contradiction();
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.logics.pl.syntax.PropositionalFormula#getModels(DeLP_GDPR.logics.pl.syntax.PropositionalSignature)
	 */
	@Override
	public Set<PossibleWorld> getModels(PlSignature sig) {
		return new HashSet<PossibleWorld>();
	}
}