package DeLP_GDPR.dung.ldo.syntax;




import java.util.Collection;
import java.util.HashSet;

import DeLP_GDPR.logics.common.LogicalSymbols;


public class LdoDisjunction extends LdoAssociativeFormula {
	
	/**
	 * Creates a new disjunction with the given inner formulas. 
	 * @param formulas a collection of formulas.
	 */
	public LdoDisjunction(Collection<? extends LdoFormula> formulas){
		super(formulas);
	}
	
	/**
	 * Creates a new (empty) disjunction.
	 */
	public LdoDisjunction(){
		this(new HashSet<LdoFormula>());
	}
	
	/**
	 * Creates a new disjunction with the two given formulae
	 * @param first a propositional formula.
	 * @param second a propositional formula.
	 */
	public LdoDisjunction(LdoFormula first, LdoFormula second){
		this();
		this.add(first);
		this.add(second);
	}
	 
	@Override
	public LdoFormula clone() {
		return new LdoDisjunction(support.copyHelper(this));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LdoDisjunction createEmptyFormula() {
		return new LdoDisjunction();
	}

	@Override
	public String getOperatorSymbol() {
		return LogicalSymbols.DISJUNCTION();
	}

	@Override
	public String getEmptySymbol() {
		return LogicalSymbols.TAUTOLOGY();
	}
}