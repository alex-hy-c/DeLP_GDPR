package DeLP_GDPR.logics.fol.syntax;



import java.util.HashSet;
import java.util.Set;

import DeLP_GDPR.logics.commons.syntax.Functor;
import DeLP_GDPR.logics.commons.syntax.Predicate;
import DeLP_GDPR.logics.commons.syntax.Variable;
import DeLP_GDPR.logics.commons.syntax.interfaces.Term;

/**
 * This class captures the common functionalities of the special
 * formulas tautology and contradiction.
 */
public abstract class SpecialFormula extends FolFormula {
	
	
	@Override
	public boolean containsQuantifier() {
		return false;
	}

	@Override
	public Set<Term<?>> getTerms() {
		return new HashSet<Term<?>>();
	}

	@Override
	public <C extends Term<?>> Set<C> getTerms(Class<C> cls) {
		return new HashSet<C>();
	}
	
	
	@Override
	public Set<FolAtom> getAtoms() {
		return new HashSet<FolAtom>();
	}

	
	@Override
	public Set<Predicate> getPredicates() {
		return new HashSet<Predicate>();
	}

	
	@Override
	public boolean isClosed() {
		return true;
	}

	
	@Override
	public boolean isClosed(Set<Variable> boundVariables) {
		return true;
	}

	
	@Override
	public boolean isWellBound() {
		return true;
	}

	
	@Override
	public boolean isWellBound(Set<Variable> boundVariables) {
		return true;
	}
	
	
	@Override
	public boolean isLiteral(){
		//TODO is this correct?
		return true;
	}

	
	@Override
	public FolFormula substitute(Term<?> v, Term<?> t){
		return this;
	}
	
	
	@Override
	public Set<Variable> getUnboundVariables(){
		return this.getTerms(Variable.class);
	}

	
	@Override
	public Set<Functor> getFunctors() {
		return new HashSet<Functor>();
	}
	
	/**
	 * 
	 * @return formulas
	 */
	public Set<FolFormula> getQuantifiedFormulas(){
		return new HashSet<FolFormula>();
	}


	/**
	 * 
	 * @return disjunction
	 */
	public Set<Disjunction> getDisjunctions(){
		return new HashSet<Disjunction>();
	}
	

	/**
	 * 
	 * @return conjunctions
	 */
	public Set<Conjunction> getConjunctions(){
		return new HashSet<Conjunction>();
	}

	
	@Override
	public boolean isDnf(){
		return true;
	}
	
	
	@Override
	public FolFormula toNnf() {
	  return this;
	}

	
	@Override
	public FolFormula collapseAssociativeFormulas() {
	  return this;
	}
}