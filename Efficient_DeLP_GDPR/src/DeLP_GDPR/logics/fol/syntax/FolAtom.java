package DeLP_GDPR.logics.fol.syntax;



import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import DeLP_GDPR.logics.commons.syntax.FunctionalTerm;
import DeLP_GDPR.logics.commons.syntax.Functor;
import DeLP_GDPR.logics.commons.syntax.Predicate;
import DeLP_GDPR.logics.commons.syntax.Variable;
import DeLP_GDPR.logics.commons.syntax.interfaces.Atom;
import DeLP_GDPR.logics.commons.syntax.interfaces.Term;

/**
 * An atom in first-order logic, i.e. a predicate and a list of argument terms.
 */
public class FolAtom extends FolFormula implements Atom {

	/**
	 * The predicate of this atom
	 */
	private Predicate predicate;
	
	/**
	 * The arguments of the atom
	 */
	private List<Term<?>> arguments = new ArrayList<Term<?>>();
	
	/** Default-Ctor: Creates new empty FOL-Atom */
	public FolAtom() {
		
	}
	
	/**
	 * Creates a new atom with the given predicate and initializes
	 * an empty argument list.
	 * @param predicate the predicate of the atom.
	 */
	public FolAtom(Predicate predicate){
		this(predicate,new ArrayList<Term<?>>());
	}
	/**
	 * constructs FolAtom
	 * @param predicate predicate
	 * @param terms terms
	 */
	public FolAtom(Predicate predicate, Term<?>... terms) {
		this(predicate, Arrays.asList(terms));
	}
	
	/**
	 * Creates a new atom with the given predicate and list of
	 * terms
	 * @param predicate the predicate of the atom
	 * @param arguments the arguments (terms) of the atom
	 */
	public FolAtom(Predicate predicate, List<? extends Term<?>> arguments){
		this.predicate = predicate;
		for(Term<?> t: arguments)
			this.addArgument(t);		
	}
	/**
	 * 
	 * @param other another Atom
	 */
	public FolAtom(FolAtom other) {
		this.predicate = other.predicate;
		for(Term<?> term : other.getArguments()) {
			this.arguments.add(term.clone());
		}
	}
	
	/**
	 * Appends the given argument to this atom's
	 * arguments and returns itself.
	 * @param term an argument to be added
	 * @throws IllegalArgumentException if the given term does not correspond
	 *   to the expected sort or the argument list is complete.
	 */
	@Override
	public void addArgument(Term<?> term) throws IllegalArgumentException{
		if(this.arguments.size() == this.predicate.getArity())
			throw new IllegalArgumentException("No more arguments expected.");
		if(!this.predicate.getArgumentTypes().get(this.arguments.size()).equals(term.getSort()))
			throw new IllegalArgumentException("The sort \"" + term.getSort() + "\" of the given term does not correspond to the expected sort \"" + this.predicate.getArgumentTypes().get(this.arguments.size()) + "\"." );
		this.arguments.add(term);		
	}
	
	
	@Override
	public FolAtom substitute(Term<?> v, Term<?> t) throws IllegalArgumentException{
		FolAtom atom = new FolAtom(this.predicate);
		for(Term<?> term: this.arguments)
			atom.addArgument(term.substitute(v, t));
		return atom;		
	}
	
	@Override
	public Set<FolAtom> getAtoms(){
		HashSet<FolAtom> atoms = new HashSet<FolAtom>();
		atoms.add(this);
		return atoms;		
	}
	
	/**
	 * Checks whether this atom is complete, i.e. whether
	 * every argument is set.
	 * @return "true" if the atom is complete.
	 */
	@Override
	public boolean isComplete(){
		return this.arguments.size() == this.predicate.getArity();
	}
	
	
	@Override
	public Set<Variable> getUnboundVariables(){
		return this.getTerms(Variable.class);
	}
	
	
	@Override
	public Set<Predicate> getPredicates(){
		Set<Predicate> predicates = new HashSet<Predicate>();
		predicates.add(this.predicate);
		return predicates;
	}
	
	
	@Override
	public Set<Functor> getFunctors(){
		Set<Functor> functors = new HashSet<Functor>();
		for(Term<?> arg: arguments) {
			if(arg instanceof FunctionalTerm) {
				functors.add(((FunctionalTerm)arg).getFunctor());
			}
		}
		return functors;
	}
	

	
	
	@Override
	public boolean isClosed(){
		return this.getTerms(Variable.class).isEmpty();
	}
	
	
	@Override
	public boolean isClosed(Set<Variable> boundVariables){
		return boundVariables.containsAll(this.getTerms(Variable.class));
	}
	
	
	@Override
	public boolean isWellBound(){
		return true;
	}
	
	
	@Override
	public boolean isWellBound(Set<Variable> boundVariables){
		return true;
	}
	
	
	@Override
	public boolean containsQuantifier(){
		return false;
	}
	
	/**
	 * Returns the predicate of this atom
	 * @return the predicate of this atom
	 */
	@Override
	public Predicate getPredicate(){
		return this.predicate;
	}
	
	/**
	 * @return the arguments of this atom.
	 */
	@Override
	public List<Term<?>> getArguments(){
		return Collections.unmodifiableList(this.arguments);
	}
	
	
	
	@Override
	public boolean isDnf(){
		return true;
	}
	

	@Override
	public boolean isLiteral(){
		return true;
	}
	
	
	@Override
	public String toString(){
		if(!this.isWellFormed()) throw new IllegalArgumentException("FolFormula not well-formed.");
		if (this.getPredicate() instanceof EqualityPredicate) 
			return "(" + this.arguments.get(0) + "==" + this.arguments.get(1) + ")"; //Prints equality predicate as a==b instead of ==(a,b)
		if (this.getPredicate() instanceof InequalityPredicate) 
			return "(" + this.arguments.get(0) + "/==" + this.arguments.get(1) + ")"; 
		
		String output = this.predicate.getName();
		if(this.arguments.size() == 0) return output;
		output += "(";
		output += this.arguments.get(0);
		for(int i = 1; i < arguments.size(); i++)
			output += ","+arguments.get(i);
		output += ")";
		return output;
	}
	
	
	@Override
	public FolFormula toNnf() {
	  return this;
	}
	

	@Override
	public FolFormula collapseAssociativeFormulas() {
    return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((arguments == null) ? 0 : arguments.hashCode());
		result = prime * result
				+ ((predicate == null) ? 0 : predicate.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;		
		if (obj == null || getClass() != obj.getClass())
			return false;
		FolAtom other = (FolAtom) obj;
		if(!other.isWellFormed()) throw new IllegalArgumentException("FolFormula not well-formed.");
		if (arguments == null) {
			if (other.arguments != null)
				return false;
		} else if (!arguments.equals(other.arguments))
			return false;
		if (predicate == null) {
			if (other.predicate != null)
				return false;
		} else if (!predicate.equals(other.predicate))
			return false;
		return true;
	}

	@Override
	public Set<Term<?>> getTerms() {
		Set<Term<?>> reval = new HashSet<Term<?>>();
		reval.addAll(arguments);
		return reval;
	}

	@Override
	public <C extends Term<?>> Set<C> getTerms(Class<C> cls) {
		Set<C> reval = new HashSet<C>();
		for(Term<?> arg : arguments) {
			if(arg.getClass().equals(cls)) {
				@SuppressWarnings("unchecked")
				C castArg = (C)arg;
				reval.add(castArg);
			}
			// recursively add terms for all inner functional terms
			reval.addAll(arg.getTerms(cls));
		}
		return reval;
	}

	@Override
	public FolAtom clone() {
		return new FolAtom(this);
	}

	@Override
	public RETURN_SET_PREDICATE setPredicate(Predicate newer) {
		Predicate old = this.predicate;
		this.predicate = newer;
		return AtomImpl.implSetPredicate(old, this.predicate, arguments);
	}

	@Override
	public String getName() {
		return getPredicate().getName();
	}
}