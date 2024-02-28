package DeLP_GDPR.dung.reasoner;




/**
 * A formula is a basic language construct.
 */
public interface Formula {
	
	/**
	 * Returns the signature of the language of this formula.
	 * @return the signature of the language of this formula.
	 */
	Signature getSignature();
}