package DeLP_GDPR.logics.pl.syntax;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import DeLP_GDPR.logics.commons.syntax.Predicate;
import DeLP_GDPR.logics.commons.syntax.interfaces.Atom;
import DeLP_GDPR.logics.commons.syntax.interfaces.Term;
import DeLP_GDPR.logics.pl.semantics.PossibleWorld;

/**
 * This class represents a simple proposition in propositional logic. 
 */
public class Proposition extends PlFormula implements Atom, Comparable<Proposition> {
	
	/**
	 * The name of the proposition
	 */
	private PlPredicate predicate;

	/** Default-Ctor for dynamic instantiation */
	public Proposition() {}
	
	/**
	 * Creates a new proposition of the given name.
	 * @param name the name of the proposition.
	 */
	public Proposition(String name){
		this.predicate = new PlPredicate(name);
	}
	/**
	 * constructor
	 * @param other another proposition
	 */
	public Proposition(Proposition other) {
		this.predicate = new PlPredicate(other.getName());
	}
	
	/**
	 * @return the name of this proposition.
	 */
	@Override
	public String getName(){
		return this.predicate != null ? this.predicate.getName() : "";
	}
	
	@Override
	public PlPredicate getPredicate() {
		return this.predicate;
	}
	
	@Override
	public Set<PlPredicate> getPredicates() {
		Set<PlPredicate> reval = new HashSet<PlPredicate>();
		reval.add(predicate);
		return reval;
	}
	
	@Override
	public String toString(){
		return getName();
	}
	
	@Override
	public PlFormula collapseAssociativeFormulas(){
		return this;
	}
	
	@Override
	public PlSignature getSignature() {
		PlSignature reval = new PlSignature();
		reval.add(this);
		return reval;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((predicate == null) ? 0 : predicate.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proposition other = (Proposition) obj;
		if (predicate == null) {
			if (other.predicate != null)
				return false;
		} else if (!predicate.equals(other.predicate))
			return false;
		return true;
	}
	
	@Override
	public PlFormula toNnf() {
		return this;
	}

	@Override
	public Proposition clone() {
		return new Proposition(this);
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.logics.pl.syntax.PropositionalFormula#trim()
	 */
	public PlFormula trim(){
		return this;
	}
	
	@Override
	public void addArgument(Term<?> arg) {
		throw new UnsupportedOperationException("addArgument not supported by Propositional-Logic");
	}

	@Override
	public List<? extends Term<?>> getArguments() {
		return new ArrayList<Term<?>>();
	}

	@Override
	public boolean isComplete() {
		return true;
	}

	@Override
	public Set<Proposition> getAtoms() {
		Set<Proposition> reval = new HashSet<Proposition>();
		reval.add(this);
		return reval;
	}

	@Override
	public boolean isLiteral() {
		return true;
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.logics.pl.syntax.PropositionalFormula#getLiterals()
	 */
	@Override
	public Set<PlFormula> getLiterals(){
		Set<PlFormula> result = new HashSet<PlFormula>();
		result.add(this);
		return result;
	}
	
	@Override
	public RETURN_SET_PREDICATE setPredicate(Predicate predicate) {
		Predicate old = this.predicate;
		this.predicate = (PlPredicate)predicate;
		return AtomImpl.implSetPredicate(old, this.predicate, new LinkedList<Term<?>>());
	}
	
	@Override
	public int compareTo(Proposition o) {
		return predicate.compareTo(o.predicate);
	}

	/* (non-Javadoc)
	 * @see org.tweetyproject.logics.propositionallogic.syntax.PropositionalFormula#toCnf()
	 */
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
		if(this.equals(p))
			return 1;
		return 0;
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.logics.pl.syntax.PropositionalFormula#replace(DeLP_GDPR.logics.pl.syntax.Proposition, DeLP_GDPR.logics.pl.syntax.PropositionalFormula, int)
	 */
	public PlFormula replace(Proposition p, PlFormula f, int i){
		if(this.equals(p))
			return f;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.logics.pl.syntax.PropositionalFormula#getModels(DeLP_GDPR.logics.pl.syntax.PropositionalSignature)
	 */
	@Override
	public Set<PossibleWorld> getModels(PlSignature sig) {
		Set<PossibleWorld> models = new HashSet<PossibleWorld>();
		PlSignature sig2 = new PlSignature(sig);
		sig2.remove(this);
		for(PossibleWorld w: PossibleWorld.getAllPossibleWorlds(sig2)){
			w.add(this);
			models.add(w);
		}
		return models;
	}	
}
