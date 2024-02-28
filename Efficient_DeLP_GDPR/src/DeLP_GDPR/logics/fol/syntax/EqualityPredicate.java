package DeLP_GDPR.logics.fol.syntax;



import java.util.Arrays;

import DeLP_GDPR.logics.commons.syntax.Predicate;
import DeLP_GDPR.logics.commons.syntax.Sort;

/**
 * This class models an equality predicate, meaning 
 * a predicate of arity 2 that maps to the identity relation.
 * Terms of the equality predicate are of the sort "_Any" 
 * which is considered equal to all other sorts.
 *
 */
public class EqualityPredicate extends Predicate {
	/**
	 * constructor
	 */
	public EqualityPredicate() {
		super("==",Arrays.asList(Sort.ANY,Sort.ANY));	
	}
}