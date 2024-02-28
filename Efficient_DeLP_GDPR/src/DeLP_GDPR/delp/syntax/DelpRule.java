package DeLP_GDPR.delp.syntax;

import DeLP_GDPR.commons.util.rules.Rule;

import DeLP_GDPR.logics.commons.syntax.Constant;
import DeLP_GDPR.logics.commons.syntax.Functor;
import DeLP_GDPR.logics.commons.syntax.Predicate;
import DeLP_GDPR.logics.commons.syntax.RelationalFormula;
import DeLP_GDPR.logics.commons.syntax.Variable;
import DeLP_GDPR.logics.commons.syntax.interfaces.Conjunctable;
import DeLP_GDPR.logics.commons.syntax.interfaces.Disjunctable;
import DeLP_GDPR.logics.commons.syntax.interfaces.Term;
import DeLP_GDPR.logics.fol.syntax.*;
import DeLP_GDPR.math.probability.Probability;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This method is the superclass for both a strict rule and a defeasible rule in defeasible logic programming
 * and captures their common attributes and methods.
 *
 */
public abstract class DelpRule extends RelationalFormula implements Rule<FolFormula, FolFormula>{
	
	/**
	 * The head of the rule (this must be a literal).
	 */
	protected FolFormula head;

	/**
	 * The body of the rule (these must be a literals).
	 */
	protected Set<FolFormula> body;

    abstract String getSymbol();

	/**
	 * Default constructor; initializes head and body of the rule
     * @param head a literal
     * @param body a set of literals
     */
    DelpRule(FolFormula head, Collection<? extends FolFormula> body){
        if(!head.isLiteral())
			throw new IllegalArgumentException("Heads of DeLP rules need to consist of a single literal.");
		for(FolFormula f: body)
			if(!f.isLiteral())
				throw new IllegalArgumentException("Body elements of DeLP rules need to consist of a single literal.");
		this.head = head;
		this.body = new HashSet<>(body);
	}

	/**
	 * Checks whether this rule is applicaple in the given context <code>literals</code>,
	 * @param literals a set of literals
	 * @return <code>true</code> iff this rule is applicaple, i.e., if the body of the rule is a subset
	 * 	of the given set of literals
	 */
	public boolean isApplicable(Collection<? extends FolFormula> literals){
		for(FolFormula f: literals)
			if(!f.isLiteral())
				throw new IllegalArgumentException("Parameter 'literals' is expected to consist of literals.");
		return literals.containsAll(body);
	}

	/**
	 * Checks whether there appear any variables in this rule
	 * @return <code>true</code> iff there appears no variable in this rule
	 */
	public boolean isGround(){
		if(!this.head.isGround()) return false;
		for(FolFormula f: this.body)
			if(!f.isGround())
				return false;
		return true;
	}


	@Override
	public Collection<? extends FolFormula> getPremise() {
		return this.body;
	}


	@Override
	public FolFormula getConclusion() {
		return this.head;
	}

	
	@Override
	public RelationalFormula combineWithAnd(Conjunctable f) {
	    throw new UnsupportedOperationException("Combination using AND not permitted for rules.");
	}

	@Override
	public RelationalFormula combineWithOr(Disjunctable f) {
	    throw new UnsupportedOperationException("Combination using OR not permitted for rules.");
	}

	
	@Override
	public RelationalFormula complement() {
		throw new UnsupportedOperationException("Complement not permitted for rules.");
	}

	
	@Override
	public Set<Predicate> getPredicates() {
		Set<Predicate> predicates = new HashSet<>();
		predicates.addAll(this.head.getPredicates());
		for(FolFormula f: this.body)
			predicates.addAll(f.getPredicates());
		return predicates;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Set<FolAtom> getAtoms() {
		Set<FolAtom> atoms = new HashSet<>();
		atoms.addAll((Collection<FolAtom>) this.head.getAtoms());
		for(FolFormula f: this.body)
			atoms.addAll((Collection<FolAtom>) f.getAtoms());
		return atoms;
	}

	@Override
	public boolean isLiteral() {
		return false;
	}

	@Override
	public Set<Term<?>> getTerms() {
		Set<Term<?>> reval = new HashSet<>();
		reval.addAll(head.getTerms());
		for(FolFormula b : body) {
			reval.addAll(b.getTerms());
		}
		return reval;
	}

	@Override
	public <C extends Term<?>> Set<C> getTerms(Class<C> cls) {
		Set<C> reval = new HashSet<>();
		reval.addAll(head.getTerms(cls));
		for(FolFormula b : body) {
			reval.addAll(b.getTerms(cls));
		}
		return reval;
	}

	@Override
	public Set<Variable> getQuantifierVariables() {
		Set<Variable> reval = new HashSet<>();
		reval.addAll(head.getQuantifierVariables());
		for(FolFormula b : body) {
			reval.addAll(b.getQuantifierVariables());
		}
		return reval;
	}
	
	@Override
	public boolean isFact() {
		return body.isEmpty();
	}

	@Override
	public boolean isConstraint() {
		return false;
	}

	@Override
	public void setConclusion(FolFormula conclusion) {
		if(!conclusion.isLiteral()) {
			throw new IllegalArgumentException("Heads of DeLP rules need to consist of a single literal.");
		}
		head = conclusion;
	}

	@Override
	public void addPremise(FolFormula premise) {
		if(!premise.isLiteral()) {
			throw new IllegalArgumentException("Body elements of DeLP rules need to consist of a single literal.");
		}
		body.add(premise);
	}

	@Override
	public void addPremises(Collection<? extends FolFormula> premises) {
        premises.forEach(this::addPremise);
	}
	
	
	@Override
	public boolean containsQuantifier() {
		return !getQuantifierVariables().isEmpty();
	}

	
	@Override
	public abstract RelationalFormula substitute(Term<?> v, Term<?> t) throws IllegalArgumentException;

	
	@Override
	public Set<Variable> getUnboundVariables() {
		Set<Variable> vars = new HashSet<>();
		vars.addAll(this.head.getUnboundVariables());
		for(FolFormula f: this.body)
			vars.addAll(f.getUnboundVariables());
		return vars;
	}

	
	@Override
	public boolean isClosed() {
		if(!this.head.isClosed()) 
			return false;
		for(FolFormula f: this.body)
			if(!f.isClosed())
				return false;
		return true;
	}

	
	@Override
	public boolean isClosed(Set<Variable> boundVariables) {
		if(!this.head.isClosed(boundVariables)) 
			return false;
		for(FolFormula f: this.body)
			if(!f.isClosed(boundVariables))
				return false;
		return true;
	}

	
	@Override
	public boolean isWellBound() {
		return true;
	}

	
	@Override
	public boolean isWellBound(Set<Variable> boundVariables) {
		return true;
	}
	
	@Override
	public Set<Functor> getFunctors() {
		Set<Functor> functors = new HashSet<>();
		functors.addAll(this.head.getFunctors());
		for(FolFormula f: this.body)
			functors.addAll(f.getFunctors());
		return functors;
	}

	
	@Override
	public Probability getUniformProbability(){
		throw new UnsupportedOperationException("IMPLEMENT ME");
	}

	@Override
	public String toString(){
		StringBuilder str = new StringBuilder(head.toString());
		if (!body.isEmpty())
			str.append(getSymbol());
		str.append(body.stream()
				.map(Object::toString)
				.collect(Collectors.joining(",")));
		str.append(".");
		return str.toString();
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DelpRule delpRule = (DelpRule) o;

        if (!head.equals(delpRule.head)) return false;
        return body.equals(delpRule.body);
    }

    @Override
    public int hashCode() {
        int result = head.hashCode();
        result = 31 * result + body.hashCode();
        return result;
    }
    
    @Override
	public FolSignature getSignature() {
		FolSignature sig = new FolSignature();
		sig.addAll(this.getTerms(Constant.class));
		sig.addAll(this.getFunctors());
		sig.addAll(this.getPredicates());
		return sig;
	}
    
}