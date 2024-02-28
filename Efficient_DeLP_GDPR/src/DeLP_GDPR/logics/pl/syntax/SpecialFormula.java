package DeLP_GDPR.logics.pl.syntax;




import java.util.HashSet;
import java.util.Set;

/**
 * This class captures the common functionalities of the special
 * formulas tautology and contradiction.
 */
public abstract class SpecialFormula extends PlFormula {

	
	@Override
	public PlFormula collapseAssociativeFormulas(){
		return this;
	}
	
	@Override
	public Set<PlPredicate> getPredicates() {
		return new HashSet<PlPredicate>();
	}
	
	
	@Override
	public PlFormula toNnf() {
		return this;
	}
	
	@Override
	public Set<Proposition> getAtoms() {
		return new HashSet<Proposition>();
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.logics.pl.syntax.PropositionalFormula#getLiterals()
	 */
	@Override
	public Set<PlFormula> getLiterals(){
		return new HashSet<PlFormula>();
	}
	
	
	@Override
	public Conjunction toCnf() {
		Conjunction conj = new Conjunction();
		Disjunction disj = new Disjunction();
		disj.add(this);
		conj.add(disj);
		return conj;
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.logics.pl.syntax.PropositionalFormula#numberOfOccurrences(DeLP_GDPR.logics.pl.syntax.Proposition)
	 */
	public int numberOfOccurrences(Proposition p){
		return 0;
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.logics.pl.syntax.PropositionalFormula#replace(DeLP_GDPR.logics.pl.syntax.Proposition, DeLP_GDPR.logics.pl.syntax.PropositionalFormula, int)
	 */
	public PlFormula replace(Proposition p, PlFormula f, int i){
		return this;
	}	
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.logics.pl.syntax.PropositionalFormula#trim()
	 */
	public PlFormula trim(){
		return this;
	}
}