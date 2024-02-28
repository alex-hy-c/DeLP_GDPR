package DeLP_GDPR.math.term;

import java.util.ArrayList;

import java.util.List;

import DeLP_GDPR.maths.*;

/**
 * This class represents a term raised to some power.
 * 
 */
public class Power extends FunctionalTerm {

	/** The power. */ 
	private Term power;
	
	/**
	 * Creates a new power term with the given term and power.
	 * @param term the potentiated term.
	 * @param power the power
	 */
	public Power(Term term, Term power) {
		super(term);
		this.power = power;
	}
	
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.FunctionalTerm#replaceTerm(DeLP_GDPR.math.term.Term, DeLP_GDPR.math.term.Term)
	 */
	@Override
	public Term replaceTerm(Term toSubstitute, Term substitution) {
		return new Power(this.getTerm().replaceTerm(toSubstitute, substitution),this.power.replaceTerm(toSubstitute, substitution));
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.FunctionalTerm#toString()
	 */
	@Override
	public String toString() {
		return "(" + this.getTerm().toString() + ")**(" + this.power.toString() + ")";
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.FunctionalTerm#value()
	 */
	@Override
	public Constant value() throws IllegalArgumentException {
		return new FloatConstant(Math.pow(this.getTerm().doubleValue(), this.power.doubleValue()));
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#isContinuous(DeLP_GDPR.math.term.Variable)
	 */
	@Override
	public boolean isContinuous(Variable v) {
		return this.getTerm().isContinuous(v) && this.power.isContinuous(v);
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#simplify()
	 */
	@Override
	public Term simplify() {
		return new Power(this.getTerm().simplify(),this.power.simplify());
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#toQuadraticForm()
	 */
	@Override
	public Sum toQuadraticForm() throws IllegalArgumentException{
		Sum quadratic = new Sum();
		if(this.power.doubleValue() <= 2 && this.getTerm().isLinear() ||
				this.power.doubleValue() <= 1 && this.getTerm().isQuadratic())
			quadratic.addTerm(new Product(this.getTerm(), this.getTerm()));
		else
			throw new IllegalArgumentException("The term '" + this + "' cannot be brought into quadratic form because it is non-linear.");
		return quadratic.toQuadraticForm();
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#derive(DeLP_GDPR.math.term.Variable)
	 */
	@Override
	public Term derive(Variable v) throws NonDifferentiableException {
		Term base = this.getTerm();
		Term exponent = this.power;
		Term base_der = base.derive(v);
		Term exponent_der = exponent.derive(v);		
		if(!exponent.getVariables().contains(v))
			return exponent.mult(new Power(base,exponent.minus(new FloatConstant(1)))).mult(base_der);
		if(!base.getVariables().contains(v))
			return this.mult(new Logarithm(base));		
		return this.mult(exponent_der.mult(new Logarithm(base)).add(exponent.mult(base_der).mult(new Fraction(new FloatConstant(1),base))));
	}
	
	@Override
	public boolean isLinear(){
		if(this.power.doubleValue() > 1)
			return false;
		return true;
	}

	@Override
	public List<Term> getTerms() {
		ArrayList<Term> result = new ArrayList<Term>();
		result.add(this.getTerm());
		result.add(this.power);
		return result;
	}

}
