package DeLP_GDPR.logics.pl.error;

import DeLP_GDPR.logics.commons.error.LanguageException;

/**
 * An Exception for the propositional language, it is thrown if a developer
 * tries to create illegal propositional statements like a predicate with an
 * arity greater than zero. 

 */
public class PlException extends LanguageException {
	
	/** kill warning */
	private static final long serialVersionUID = 843894579984076905L;
	/**
	 * 
	 * @param reason LanguageExceptionReason
	 */
	public PlException(LanguageExceptionReason reason) {
		this(reason, "");
	}
	/**
	 * 
	 * @param reason LanguageExceptionReason
	 * @param info info
	 */
	public PlException(LanguageExceptionReason reason, String info) {
		super("Propositional-Logic", reason, info);
	}
}
