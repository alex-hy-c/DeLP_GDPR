package DeLP_GDPR.dung.reasoner;


/**
 * This class captures an abstract knowledge base, i.e. some set of
 * formulas in a given knowledge representation language, that can be asked
 * queries.
 */
public interface BeliefBase {
	/**
	 * Returns the signature of the language of this knowledge base.
	 * @return the signature of the language of this knowledge base.
	 */
	public Signature getMinimalSignature();
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString();

}
