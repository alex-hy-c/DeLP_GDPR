package DeLP_GDPR.logics.fol.syntax;




import java.util.Arrays;

import DeLP_GDPR.logics.commons.syntax.Predicate;
import DeLP_GDPR.logics.commons.syntax.Sort;

/**
 * This class models an inequality predicate, meaning 
 * a predicate of arity 2 that maps to the complement of the identity relation.
 * Terms of the inequality predicate are of the sort "_Any" 
 * which is considered equal to all other sorts.
 *
 */
public class InequalityPredicate extends Predicate {
	/**
	 * constructor
	 */
	public InequalityPredicate() {
		super("/==",Arrays.asList(Sort.ANY,Sort.ANY));	
	}

}