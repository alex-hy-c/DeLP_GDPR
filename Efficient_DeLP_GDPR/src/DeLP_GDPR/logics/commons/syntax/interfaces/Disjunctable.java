package DeLP_GDPR.logics.commons.syntax.interfaces;



/**
 * Formulas implementing this interface can be connected using
 * OR.
 */
public interface Disjunctable extends SimpleLogicalFormula {
	
	/**
	 * @param f a formula to be combined with OR and this.
	 * @return a disjunction of this and the given formula.
	 */
	public SimpleLogicalFormula combineWithOr(Disjunctable f);
}