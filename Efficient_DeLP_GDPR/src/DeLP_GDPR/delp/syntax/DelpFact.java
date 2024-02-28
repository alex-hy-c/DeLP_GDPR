package DeLP_GDPR.delp.syntax;

import DeLP_GDPR.logics.commons.syntax.RelationalFormula;

import DeLP_GDPR.logics.commons.syntax.interfaces.Term;
import DeLP_GDPR.logics.fol.syntax.FolFormula;

import java.util.Collections;

/**
 * This class implements a fact in defeasible logic programming which encapsulates a literal.
 *
 */
public final class DelpFact extends DelpRule {
	
	/**
	 * Default constructor; initializes this fact with the given literal
	 * @param literal a literal
	 */
	public DelpFact(FolFormula literal){
		super(literal, Collections.emptySet());
	}

	@Override
	String getSymbol() {
		return "";
	}

	@Override
	public RelationalFormula substitute(Term<?> v, Term<?> t)	throws IllegalArgumentException {
		return new DelpFact(this.getConclusion().substitute(v, t));
	}

	@Override
	public DelpFact clone() {
        // this is OK to call constructor because this class is now final:
		return new DelpFact((FolFormula) getFormula().clone());
	}

}