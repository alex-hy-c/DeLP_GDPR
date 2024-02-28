package DeLP_GDPR.logics.pl.syntax;




import java.util.HashSet;
import java.util.Set;

import org.tweetyproject.commons.util.Pair;

import DeLP_GDPR.logics.common.LogicalSymbols;
import DeLP_GDPR.logics.pl.semantics.PossibleWorld;

/**
 * 
 * This class models the implication of propositional logic.
 */
public class Implication extends PlFormula {

	/**
	 * The pair of formulas that are part of the implication.
	 */
	private Pair<PlFormula, PlFormula> formulas;

	/**
	 * Creates a new implication a=&gt;b with the two given formulas
	 * 
	 * @param a a propositional formula.
	 * @param b a propositional formula.
	 */
	public Implication(PlFormula a, PlFormula b) {
		this.formulas = new Pair<PlFormula, PlFormula>(a, b);
	}

	/**
	 * Creates a new implication with the given pair of formulas
	 * 
	 * @param formulas a pair of formulas
	 */
	public Implication(Pair<PlFormula, PlFormula> formulas) {
		this.formulas = formulas;
	}

	/**
	 * Returns the formulas of the implication.
	 * 
	 * @return the formulas that are part of the implication
	 */
	public Pair<PlFormula, PlFormula> getFormulas() {
		return formulas;
	}

	/**
	 * Sets the formulas of the implication.
	 * 
	 * @param formulas the formulas
	 */
	public void setFormulas(Pair<PlFormula, PlFormula> formulas) {
		this.formulas = formulas;
	}
	
	/**
	 * Sets the formulas of the implication.
	 * @param left some formula
	 * @param right  some formula
	 */
	public void setFormulas(PlFormula left, PlFormula right) {
		this.formulas = new Pair<PlFormula, PlFormula>(left, right);
	}
	
	/**
	 * Sets the left side formula of the implication
	 * left =&gt; right.
	 * 
	 * @param left formula
	 */
	public void setFirstFormula(PlFormula left) {
		this.formulas.setFirst(left);
	}
	
	/**
	 * Sets the right side formula of the implication
	 * left =&gt; right
	 * 
	 * @param right formula.
	 */
	public void setSecondFormula(PlFormula right) {
		this.formulas.setSecond(right);
	}
	
	/**
	 * 	 * Get the left side formula of the implication
	 * left =&gt; right.
	 * 
	 * @return left formula
	 */
	public PlFormula getFirstFormula() {
		return this.formulas.getFirst();
	}
	
	/**
	 * Get the right side formula of the implication
	 * left =&gt; right
	 * 
	 * @return right formula.
	 */
	public PlFormula getSecondFormula() {
		return this.formulas.getSecond();
	}

	@Override
	public Set<Proposition> getAtoms() {
		Set<Proposition> result = new HashSet<Proposition>();
		result.addAll(formulas.getFirst().getAtoms());
		result.addAll(formulas.getSecond().getAtoms());
		return result;
	}

	@Override
	public Set<PlFormula> getLiterals() {
		Set<PlFormula> result = new HashSet<PlFormula>();
		result.addAll(formulas.getFirst().getLiterals());
		result.addAll(formulas.getSecond().getLiterals());
		return result;
	}

	@Override
	public PlFormula collapseAssociativeFormulas() {
		PlFormula first = this.formulas.getFirst().collapseAssociativeFormulas();
		PlFormula second = this.formulas.getSecond().collapseAssociativeFormulas();
		return new Implication(first, second);
	}

	@Override
	public Set<PlPredicate> getPredicates() {
		Set<PlPredicate> predicates = new HashSet<PlPredicate>();
		predicates.addAll(this.formulas.getFirst().getPredicates());
		predicates.addAll(this.formulas.getSecond().getPredicates());
		return predicates;
	}

	@Override
	public PlFormula trim() {
		PlFormula f1 = formulas.getFirst().trim();
		PlFormula f2 = formulas.getSecond().trim();
		if (f1.equals(f2))
			return new Tautology();
		return new Implication(f1, f2);
	}

	@Override
	public PlFormula toNnf() {
		// (A=>B) <=> (!A || B)
		Disjunction d = new Disjunction(new Negation(this.formulas.getFirst()), this.formulas.getSecond()); 
		return d.toNnf();
	}

	@Override
	public Conjunction toCnf() {
		// (A=>B) <=> (!A || B)
		Disjunction d = new Disjunction(new Negation(this.formulas.getFirst()), this.formulas.getSecond()); 
		return d.toCnf();
	}

	@Override
	public Set<PossibleWorld> getModels(PlSignature sig) {
		Disjunction d = new Disjunction(new Negation(this.formulas.getFirst()), this.formulas.getSecond()); // (A=>B)
		return d.getModels();
	}

	@Override
	public int numberOfOccurrences(Proposition p) {
		int result = 0;
		result += formulas.getFirst().numberOfOccurrences(p);
		result += formulas.getSecond().numberOfOccurrences(p);
		return result;
	}

	@Override
	public PlFormula replace(Proposition p, PlFormula f, int i) {
		Implication n = this.clone();
		PlFormula left = formulas.getFirst();
		if (left.numberOfOccurrences(p) >= i) {
			n.setFirstFormula(left.replace(p, f, i)); 
		} else {
			int num = left.numberOfOccurrences(p);
			PlFormula right = formulas.getSecond();
			if (num + right.numberOfOccurrences(p) >= i) 
				n.setSecondFormula(right.replace(p, f, i-num));
		}
		return n;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formulas == null) ? 0 : formulas.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Implication other = (Implication) obj;
		if (formulas == null) {
			if (other.formulas != null)
				return false;
		} else if (!formulas.equals(other.formulas))
			return false;
		return true;
	}

	@Override
	public Implication clone() {
		return new Implication(this.formulas);
	}
	
	@Override
	public String toString() {
		return "(" + this.formulas.getFirst().toString() + LogicalSymbols.IMPLICATION() + this.formulas.getSecond().toString() + ")";
	}
	
	@Override
	public PlSignature getSignature() {
		PlSignature sig = this.formulas.getFirst().getSignature();
		sig.addSignature(this.formulas.getSecond().getSignature());
		return sig;
	}

}
