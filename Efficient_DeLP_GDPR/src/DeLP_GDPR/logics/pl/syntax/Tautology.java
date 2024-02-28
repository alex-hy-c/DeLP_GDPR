package DeLP_GDPR.logics.pl.syntax;




import java.util.Set;

import DeLP_GDPR.logics.common.LogicalSymbols;
import DeLP_GDPR.logics.pl.semantics.PossibleWorld;


public class Tautology extends SpecialFormula {

	/**
	 * Creates a new tautology.
	 */
	public Tautology() {
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return LogicalSymbols.TAUTOLOGY();
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Tautology;
	}

	@Override
	public int hashCode() {
		return 13;
	}

	@Override
	public Tautology clone() {
		return new Tautology();
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.logics.pl.syntax.PropositionalFormula#getModels(DeLP_GDPR.logics.pl.syntax.PropositionalSignature)
	 */
	@Override
	public Set<PossibleWorld> getModels(PlSignature sig) {
		return PossibleWorld.getAllPossibleWorlds(sig);
	}
}
