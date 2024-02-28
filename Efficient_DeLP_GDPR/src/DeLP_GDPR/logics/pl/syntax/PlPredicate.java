package DeLP_GDPR.logics.pl.syntax;

import DeLP_GDPR.logics.commons.error.LanguageException.LanguageExceptionReason;
import DeLP_GDPR.logics.commons.syntax.Predicate;
import DeLP_GDPR.logics.commons.syntax.Sort;
import DeLP_GDPR.logics.pl.error.PlException;

/**
 * A specialized predicate for propositional logic that only allows an identifier
 * but has no arguments and therefore has an arity of zero.
 */
public class PlPredicate extends Predicate {
	
	/** Default-Ctor for dynamic instantiation */
	public PlPredicate() {
		this("");
	}
	
	/**
	 * Ctor: Creates a new propositional predicate with the given
	 * name.
	 * @param name	The name of the predicate
	 */
	public PlPredicate(String name) {
		super(name, 0);
	}
	
	@Override
	public void addArgumentType(Sort argType) {
		throw new PlException(LanguageExceptionReason.LER_ILLEGAL_PREDICATE,
				"The predicates must not have any arguments.");
	}
	
	@Override
	public PlPredicate clone() {
		return new PlPredicate(this.getName());
	}
}