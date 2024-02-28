package DeLP_GDPR.math.term;


import java.util.*;



import DeLP_GDPR.maths.*;

/**
 * This class models the absolute value of the inner term.
 */
public class AbsoluteValue extends FunctionalTerm {	
	
	/**
	 * Creates a new absolute value term with the given inner term.
	 * @param term a term
	 */
	public AbsoluteValue(Term term){
		super(term);
	}	
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#getAbsoluteValues()
	 */
	@Override
	public Set<AbsoluteValue> getAbsoluteValues(){
		Set<AbsoluteValue> avs = this.getTerm().getAbsoluteValues();
		avs.add(this);
		return avs;
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#replaceTerm(DeLP_GDPR.math.term.Term, DeLP_GDPR.math.term.Term)
	 */
	@Override
	public Term replaceTerm(Term toSubstitute, Term substitution) {
		if(toSubstitute == this)
			return substitution;
		return new AbsoluteValue(this.getTerm().replaceTerm(toSubstitute, substitution));
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#derive(DeLP_GDPR.math.term.Variable)
	 */
	@Override
	public Term derive(Variable v) throws NonDifferentiableException{
		throw new NonDifferentiableException();
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#simplify()
	 */
	@Override
	public Term simplify(){
		Term t = this.getTerm().simplify();
		if(t instanceof Constant)
			return new FloatConstant(Math.abs(t.doubleValue()));
		return new AbsoluteValue(t);
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#toString()
	 */
	@Override
	public String toString() {
		return "abs(" + this.getTerm().toString() + ")";
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#isContinuous(DeLP_GDPR.math.term.Variable)
	 */
	@Override
	public boolean isContinuous(Variable v){
		return this.getTerm().isContinuous(v);
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#value()
	 */
	@Override
	public Constant value() throws IllegalArgumentException {
		Constant c = this.getTerm().value();
		if(c instanceof IntegerConstant){
			if(((IntegerConstant)c).getValue() < 0)
				return new IntegerConstant(((IntegerConstant)c).getValue()*-1);
			else return c;
		}else if(c instanceof FloatConstant){
			if(((FloatConstant)c).getValue() < 0)
				return new FloatConstant(((FloatConstant)c).getValue()*-1);
			else return c;
		}
		throw new IllegalArgumentException("Unrecognized atomic term type.");
	}

	@Override
	public List<Term> getTerms() {
		return null;
	}
}
