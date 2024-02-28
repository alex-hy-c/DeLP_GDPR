


package DeLP_GDPR.math.term;

import java.util.List;


import DeLP_GDPR.maths.*;


public class Exp extends FunctionalTerm {
	
	/**
	 * Creates a new exponential term with the given term.
	 * @param term the potentiated term.
	 */
	public Exp(Term term) {
		super(term);
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.FunctionalTerm#replaceTerm(DeLP_GDPR.math.term.Term, DeLP_GDPR.math.term.Term)
	 */
	@Override
	public Term replaceTerm(Term toSubstitute, Term substitution) {
		return new Exp(this.getTerm().replaceTerm(toSubstitute, substitution));
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.FunctionalTerm#toString()
	 */
	@Override
	public String toString() {
		return "exp(" + this.getTerm() + ")";
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.FunctionalTerm#value()
	 */
	@Override
	public Constant value() throws IllegalArgumentException {
		return new FloatConstant(Math.exp(this.getTerm().doubleValue()));
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#derive(DeLP_GDPR.math.term.Variable)
	 */
	@Override
	public Term derive(Variable v) throws NonDifferentiableException {
		Product t = new Product();
		t.addTerm(this.getTerm().derive(v));
		t.addTerm(this);
		return t;
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
		return new Exp(this.getTerm().simplify());
	}

	@Override
	public List<Term> getTerms() {

		return null;
	}

}
