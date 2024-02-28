package DeLP_GDPR.math.term;



import java.util.*;

/**
 * Instances of this class represent a functional term on some inner term.
 */
public abstract class FunctionalTerm extends Term {

	/**
	 * The inner term of this operation.
	 */
	private Term term;
	
	/**
	 * Creates a new functional term with the given inner term.
	 * @param term a term
	 */
	public FunctionalTerm(Term term){
		this.term = term;
	}
	
	/**
	 * Returns the inner term.
	 * @return the inner term.
	 */
	public Term getTerm(){
		return this.term;
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#collapseAssociativeOperations()
	 */
	@Override
	public void collapseAssociativeOperations() {
		this.term.collapseAssociativeOperations();
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#expandAssociativeOperations()
	 */
	@Override
	public void expandAssociativeOperations(){
		this.term.expandAssociativeOperations();
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#getMinimums()
	 */
	@Override
	public Set<Minimum> getMinimums() {
		return this.term.getMinimums();
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#getMaximums()
	 */
	@Override
	public Set<Maximum> getMaximums() {
		return this.term.getMaximums();
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#getProducts()
	 */
	@Override
	public Set<Product> getProducts() {
		return this.term.getProducts();
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#getSum()
	 */
	@Override
	public Set<Sum> getSums() {
		return this.term.getSums();
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#getVariables()
	 */
	@Override
	public Set<Variable> getVariables() {
		return this.term.getVariables();
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#getAbsoluteValues()
	 */
	@Override
	public Set<AbsoluteValue> getAbsoluteValues(){
		return this.term.getAbsoluteValues();		
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#isInteger()
	 */
	@Override
	public boolean isInteger() {
		return this.term.isInteger();
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#toLinearForm()
	 */
	@Override
	public Sum toLinearForm() throws IllegalArgumentException{
		throw new IllegalArgumentException("The term '" + this + "' cannot be brought into linear form because it is non-linear.");
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#toQuadraticForm()
	 */
	@Override
	public Sum toQuadraticForm() throws IllegalArgumentException{
		throw new IllegalArgumentException("The term '" + this + "' cannot be brought into quadratic form because it is non-linear.");
	}
		
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#replaceTerm(DeLP_GDPR.math.term.Term, DeLP_GDPR.math.term.Term)
	 */
	@Override
	public abstract Term replaceTerm(Term toSubstitute, Term substitution);
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#toString()
	 */
	@Override
	public abstract String toString();
	
	@Override
	public boolean isLinear() {
		return false;
	}


	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#value()
	 */
	@Override
	public abstract Constant value() throws IllegalArgumentException;

}
