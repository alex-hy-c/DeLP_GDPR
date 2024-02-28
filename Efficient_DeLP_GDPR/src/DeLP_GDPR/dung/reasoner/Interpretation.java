package DeLP_GDPR.dung.reasoner;



import java.util.*;

/**
 * An interpretation for some logical language.
 * @param <B> the type of belief bases
 * @param <S> the type of formulas
 */
public interface Interpretation<B extends BeliefBase, S extends Formula> {
	
	/**
	 * Checks whether this interpretation satisfies the given formula.
	 * @param formula a formula .
	 * @return "true" if this interpretation satisfies the given formula.
	 * @throws IllegalArgumentException if the formula does not correspond
	 * 		to the expected language.
	 */
	public boolean satisfies(S formula) throws IllegalArgumentException;
	
	/**
	 * Checks whether this interpretation satisfies all given formulas.
	 * @param formulas a collection of formulas.
	 * @return "true" if this interpretation satisfies all given formulas. 
	 * @throws IllegalArgumentException if at least one formula does not correspond
	 * 		to the expected language.
	 */
	public boolean satisfies(Collection<S> formulas) throws IllegalArgumentException;
	
	/**
	 * Checks whether this interpretation satisfies the given knowledge base.
	 * @param beliefBase a knowledge base.
	 * @return "true" if this interpretation satisfies the given knowledge base.
	 * @throws IllegalArgumentException IllegalArgumentException if the knowledgebase does not correspond
	 * 		to the expected language.
	 */
	public abstract boolean satisfies(B beliefBase) throws IllegalArgumentException;
}
