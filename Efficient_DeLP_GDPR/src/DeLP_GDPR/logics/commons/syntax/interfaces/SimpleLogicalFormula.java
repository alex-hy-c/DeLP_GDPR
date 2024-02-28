package DeLP_GDPR.logics.commons.syntax.interfaces;




import java.util.Set;

import org.tweetyproject.commons.Formula;

import DeLP_GDPR.logics.commons.syntax.Predicate;

/**
 * A formula of a logical language
 */
public interface SimpleLogicalFormula extends Formula {
	/**
	 * Processes the set of all atoms which appear in this formula
	 * @return	The set of all atoms
	 */
	Set<? extends Atom> getAtoms();
	
	/** 
	 * Processes the set of all predicates which appear in this 
	 * formula
	 * @return	all predicates that appear in this formula
	 */
	Set<? extends Predicate> getPredicates();
	
	/**
	 * @return The class description of the predicate used by this formula.
	 */
	Class<? extends Predicate> getPredicateCls();
	
	/** @return true if the formula represents a literal in the language or false otherwise */
	boolean isLiteral();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object other);
	
	/**
	 * Creates a deep copy of this formula
	 * @return the cloned formula
	 */
	SimpleLogicalFormula clone();
}
