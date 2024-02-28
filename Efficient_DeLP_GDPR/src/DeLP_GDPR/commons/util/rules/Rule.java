package DeLP_GDPR.commons.util.rules;


import java.util.*;

import org.tweetyproject.commons.*;

/**
 * This interface models a general rule, i.e. a structure with some
 * premise (a set of formulas) and some conclusion (a single formula).
 * @param <C> the type of formulas for the conclusion
 * @param <P> the type of formulas for the premises
 */
public interface Rule<C extends Formula, P extends Formula> extends Formula {

	/**
	 * isFact
	 * @return  whether the rule is a fact
	 */
	public boolean isFact();
/**
 * isConstraint
 * @return whether the rule is a constraint
 */
	public boolean isConstraint();
	
	/**
	 * Set the conclusion of this rule.
	 * @param conclusion some formula
	 */
	public void setConclusion(C conclusion);
	
	/**
	 * Add the given premise to this rule.
	 * @param premise some formula
	 */
	public void addPremise(P premise);
	
	/**
	 * Add the given premises to this rule.
	 * @param premises some formulas
	 */
	public void addPremises(Collection<? extends P> premises);
	
	@Override
	public Signature getSignature();
	
	/**
	 * Returns the premise of this rule.
	 * @return the premise of this rule.
	 */
	public Collection<? extends P> getPremise();
	
	/**
	 * Returns the conclusion of this rule.
	 * @return the conclusion of this rule.
	 */
	public C getConclusion();
		
}