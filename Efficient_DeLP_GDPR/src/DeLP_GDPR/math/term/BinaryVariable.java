package DeLP_GDPR.math.term;




/**
 * This class models a binary variable as a mathematical term.
 */
public class BinaryVariable extends Variable{
	
	/**
	 * Creates a new binary variable with the given name.
	 * @param name the name of the variable
	 */
	public BinaryVariable(String name) {
		super(name,0,1);
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.math.term.Term#isInteger()
	 */
	@Override
	public boolean isInteger() {
		return true;
	}
}