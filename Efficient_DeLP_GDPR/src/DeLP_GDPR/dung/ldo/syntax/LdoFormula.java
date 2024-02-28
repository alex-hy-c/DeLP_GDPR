package DeLP_GDPR.dung.ldo.syntax;




import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import DeLP_GDPR.dung.ldo.semantics.LdoInterpretation;
import DeLP_GDPR.dung.semantics.Semantics;
import DeLP_GDPR.dung.syntax.Argument;
import DeLP_GDPR.dung.syntax.DungSignature;
import DeLP_GDPR.dung.syntax.DungTheory;
import DeLP_GDPR.graphs.Graph;
import DeLP_GDPR.logics.commons.syntax.interfaces.ClassicalFormula;
import DeLP_GDPR.logics.commons.syntax.interfaces.Conjunctable;
import DeLP_GDPR.logics.commons.syntax.interfaces.Disjunctable;
import DeLP_GDPR.logics.pl.syntax.PlPredicate;
import DeLP_GDPR.math.probability.Probability;

/**
 * This abstract class specifies the general methods of all Ldo-formulas
 * (LDO - Logic of dialectical outcomes, cf. [Hunter, Thimm, 2015])
 */
public abstract class LdoFormula implements ClassicalFormula{

	
	@Override
	public abstract Set<LdoArgument> getAtoms();

	
	@Override
	public LdoConjunction combineWithAnd(Conjunctable f){
		if(!(f instanceof LdoFormula))
			throw new IllegalArgumentException("The given formula " + f + " is not a ldo formula.");
		return new LdoConjunction(this,(LdoFormula)f);
	}
	
	
	@Override
	public LdoDisjunction combineWithOr(Disjunctable f){
		if(!(f instanceof LdoFormula))
			throw new IllegalArgumentException("The given formula " + f + " is not a ldo formula.");
		return new LdoDisjunction(this,(LdoFormula)f);
	}
	
	
	@Override
	public abstract Set<PlPredicate> getPredicates();
	
	/**
	 * Returns all literals, i.e. all formulas of the form "a" or "!a"
	 * where "a" is a proposition, that appear in this formula.
	 * @return all literals appearing in this formula.
	 */
	public abstract Set<LdoFormula> getLiterals();

	/**
	 * Returns the dividers for this formula, i.e. all sub-theories of the given
	 * theory such that this formula is satisfied by this sub-theory.
	 * @param theory some argumentation framework
	 * @param semantics some semantics
	 * @return the set of dividers of this formula
	 */
	public Collection<DungTheory> getDividers(DungTheory theory, Semantics semantics){
		Collection<DungTheory> result = new HashSet<DungTheory>();
		for( Graph<Argument> g: theory.getSubgraphs()){
			DungTheory sub = new DungTheory(g);
			LdoInterpretation i = new LdoInterpretation(sub,semantics);
			if(i.satisfies(this))
				result.add(sub);			
		}		
		return result;
	}
	
	
	@Override
	public Probability getUniformProbability(){
		throw new UnsupportedOperationException("Not supported.");
	}
  
	@Override
	public ClassicalFormula complement(){
		if(this instanceof LdoNegation)
			return ((LdoNegation)this).getFormula();
		return new LdoNegation(this);
	}

	@Override
	public boolean isLiteral() {
		return false;
	}
	
	@Override
	public Class<PlPredicate> getPredicateCls() {
		return PlPredicate.class;
	}
	
	@Override
	public DungSignature getSignature() {
		return new DungSignature();
	}
	
	@Override
	public abstract boolean equals(Object other);
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract LdoFormula clone();
	
}
