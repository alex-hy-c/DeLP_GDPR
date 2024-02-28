package DeLP_GDPR.logics.commons.syntax.interfaces;





/**
 * This interface models a classical formula, i.e. a formula that can be connected
 * to other classical formulas using AND and OR and where the complement is
 * well-defined.
 * 
 */
public interface ClassicalFormula extends 
	Disjunctable, 
	Conjunctable, 
	Invertable,
	ProbabilityAware {
}