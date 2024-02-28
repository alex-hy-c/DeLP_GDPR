package DeLP_GDPR.math.term;




import java.util.*;

/**
 * This class models an abstract constant, e.g. a float or an integer.
 * @author Matthias Thimm
 */
public abstract class Constant extends Term{
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#value()
	 */
	@Override
	public Constant value(){
		return this;
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#getVariables()
	 */
	@Override
	public Set<Variable> getVariables(){
		return new HashSet<Variable>();
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#getProducts()
	 */
	@Override
	public Set<Product> getProducts(){
		return new HashSet<Product>();
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#getSums()
	 */
	@Override
	public Set<Sum> getSums(){
		return new HashSet<Sum>();
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#getProducts()
	 */
	@Override
	public Set<Minimum> getMinimums(){
		return new HashSet<Minimum>();
	}
	

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#getMaximums()
	 */
	@Override
	public Set<Maximum> getMaximums(){
		return new HashSet<Maximum>();
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#getAbsoluteValues()
	 */
	@Override
	public Set<AbsoluteValue> getAbsoluteValues(){
		return new HashSet<AbsoluteValue>();
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#collapseAssociativeOperations()
	 */
	@Override
	public void collapseAssociativeOperations(){
		// do nothing
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#expandAssociativeOperations()
	 */
	@Override
	public void expandAssociativeOperations(){
		// do nothing
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#simplify()
	 */
	@Override
	public Term simplify(){
		return this;
	}
	
	@Override
	public List<Term> getTerms(){
		ArrayList<Term> result = new ArrayList<Term>();
		result.add(this);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#toLinearForm()
	 */
	@Override
	public Sum toLinearForm() throws IllegalArgumentException{
		Sum sum = new Sum();
		Product p = new Product();
		p.addTerm(this);
		sum.addTerm(p);
		return sum;
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#toQuadraticForm()
	 */
	@Override
	public Sum toQuadraticForm() throws IllegalArgumentException{
		Sum sum = new Sum();
		Product p = new Product();
		p.addTerm(this);
		sum.addTerm(p);
		return sum;
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#derive(DeLP_GDPR.math.term.Variable)
	 */
	@Override
	public Term derive(Variable v){		
		return new IntegerConstant(0);
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#isContinuous(DeLP_GDPR.math.term.Variable)
	 */
	@Override
	public boolean isContinuous(Variable v){
		return true;
	}
	
	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#replaceTerm(DeLP_GDPR.math.term.Term, DeLP_GDPR.math.term.Term)
	 */
	@Override
	public Term replaceTerm(Term toSubstitute, Term substitution){
		if(toSubstitute == this)
			return substitution;
		return this;
	}
}