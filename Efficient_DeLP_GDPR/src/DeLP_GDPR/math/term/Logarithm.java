package DeLP_GDPR.math.term;


import java.util.List;


import DeLP_GDPR.maths.*;

/**
 * Instances of this class represent application of the logarithm function on some term.
 */
public class Logarithm extends FunctionalTerm {

	/**
	 * Creates a new logarithm term for the give inner term. 
	 * @param term a term
	 */
	public Logarithm(Term term) {
		super(term);
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.FunctionalTerm#replaceTerm(DeLP_GDPR.math.term.Term, DeLP_GDPR.math.term.Term)
	 */
	@Override
	public Term replaceTerm(Term toSubstitute, Term substitution) {
		if(toSubstitute == this)
			return substitution;
		return new Logarithm(this.getTerm().replaceTerm(toSubstitute, substitution));
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.FunctionalTerm#toString()
	 */
	@Override
	public String toString() {
		return "log(" + this.getTerm() + ")";
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#derive(DeLP_GDPR.math.term.Variable)
	 */
	@Override
	public Term derive(Variable v) throws NonDifferentiableException{
		if(!this.getVariables().contains(v)) return new IntegerConstant(0);
		return this.getTerm().derive(v).mult(new Fraction(new IntegerConstant(1),this.getTerm()));
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#simplify()
	 */
	@Override
	public Term simplify(){
		Term t = this.getTerm().simplify();
		if(t instanceof Constant)
			return new FloatConstant(Math.log(t.doubleValue()));
		return new Logarithm(t);
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#isContinuous(DeLP_GDPR.math.term.Variable)
	 */
	@Override
	public boolean isContinuous(Variable v){
		return this.getTerm().isContinuous(v);		
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.FunctionalTerm#value()
	 */
	@Override
	public Constant value() throws IllegalArgumentException {
		Constant c = this.getTerm().value();
		if(c instanceof IntegerConstant){
			if(((IntegerConstant)c).getValue() <= 0)				
				return new FloatConstant(Float.NEGATIVE_INFINITY);
			else return new FloatConstant((float)(Math.log(((IntegerConstant)c).getValue())));
		}else if(c instanceof FloatConstant){
			if(((FloatConstant)c).getValue() <= 0)				
				return new FloatConstant(Float.NEGATIVE_INFINITY);
			else return new FloatConstant((float)(Math.log(((FloatConstant)c).getValue())));
		}
		throw new IllegalArgumentException("Unrecognized atomic term type.");
	}

	@Override
	public List<Term> getTerms() {
		// TODO Auto-generated method stub
		return null;
	}

}
