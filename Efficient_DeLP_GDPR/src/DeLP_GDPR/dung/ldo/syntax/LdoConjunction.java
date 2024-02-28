package DeLP_GDPR.dung.ldo.syntax;




import java.util.Collection;
import java.util.HashSet;

import DeLP_GDPR.logics.common.LogicalSymbols;

/**
 * This class represents a conjunction in ldo logic.
 * 
 */
public class LdoConjunction extends LdoAssociativeFormula {
		
	/**
	 * Creates a new conjunction with the given inner formulas. 
	 * @param formulas a collection of formulas.
	 */
	public LdoConjunction(Collection<? extends LdoFormula> formulas){
		super(formulas);
	}
	
	/**
	 * Creates a new (empty) conjunction.
	 */
	public LdoConjunction(){
		this(new HashSet<LdoFormula>());
	}
	
	/**
	 * Creates a new conjunction with the two given formulae
	 * @param first a ldo formula.
	 * @param second a ldo formula.
	 */
	public LdoConjunction(LdoFormula first, LdoFormula second){
		this();
		this.add(first);
		this.add(second);
	}	

	@SuppressWarnings("unchecked")
	@Override
	public LdoConjunction createEmptyFormula() {
		return new LdoConjunction();
	}

	@Override
	public String getOperatorSymbol() {
		return LogicalSymbols.CONJUNCTION();
	}

	@Override
	public String getEmptySymbol() {
		return LogicalSymbols.CONTRADICTION();
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.dung.ldo.syntax.LdoFormula#clone()
	 */
	@Override
	public LdoConjunction clone() {
		return new LdoConjunction(support.copyHelper(this));
	}

}
