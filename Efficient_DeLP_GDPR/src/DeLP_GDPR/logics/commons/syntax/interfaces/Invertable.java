package DeLP_GDPR.logics.commons.syntax.interfaces;




/**
 * Formulas implementing this interface have a
 * complement
 */
public interface Invertable extends SimpleLogicalFormula {
	
	/**
	 * @return the complement of this formula.
	 */
	public Invertable complement();
}