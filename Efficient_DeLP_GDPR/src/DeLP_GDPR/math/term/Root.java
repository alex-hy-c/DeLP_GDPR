package DeLP_GDPR.math.term;


import java.util.ArrayList;

import java.util.List;

import DeLP_GDPR.maths.*;

/**
 * This class represents a the nth root function
 */
public class Root extends FunctionalTerm {

	/** The base of the root */
	private Term base;
	
	/**
	 * Creates a new square root.
	 * @param term the term inside the square root
	 */
	public Root(Term term) {
		this(term,new IntegerConstant(2));
	}
	
	/**
	 * Creates a new root for the given base.
	 * @param term the term inside the square root
	 * @param base the base of the root
	 */
	public Root(Term term, Term base) {
		super(term);
		this.base = base;
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.FunctionalTerm#replaceTerm(DeLP_GDPR.math.term.Term, DeLP_GDPR.math.term.Term)
	 */
	@Override
	public Term replaceTerm(Term toSubstitute, Term substitution) {
		return new Root(this.getTerm().replaceTerm(toSubstitute, substitution),this.base);
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.FunctionalTerm#toString()
	 */
	@Override
	public String toString() {
		return "(" + this.getTerm().toString() + ")**(1.0/" + this.base + ")";
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.FunctionalTerm#value()
	 */
	@Override
	public Constant value() throws IllegalArgumentException {
		return new FloatConstant(Math.pow(this.getTerm().value().doubleValue(), 1.0/this.base.doubleValue()));
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#isContinuous(DeLP_GDPR.math.term.Variable)
	 */
	@Override
	public boolean isContinuous(Variable v) {
		return this.getTerm().isContinuous(v);
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#simplify()
	 */
	@Override
	public Term simplify() {
		return new Root(this.getTerm().simplify(),this.base);
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#derive(DeLP_GDPR.math.term.Variable)
	 */
	@Override
	public Term derive(Variable v) throws NonDifferentiableException {
		throw new RuntimeException("Implement me");
	}

	@Override
	public List<Term> getTerms() {
		ArrayList<Term> result = new ArrayList<Term>();
		result.add(this.base);
		return result;
	}

}
