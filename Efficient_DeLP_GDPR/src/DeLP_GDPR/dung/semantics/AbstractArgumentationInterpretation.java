package DeLP_GDPR.dung.semantics;

import org.tweetyproject.commons.AbstractInterpretation;

import DeLP_GDPR.dung.syntax.*;

/**
 * This abstract class acts as a common ancestor for interpretations to
 * abstract argumentation frameworks.
 */
public abstract class AbstractArgumentationInterpretation <T extends ArgumentationFramework<Argument>> extends AbstractInterpretation<T,Argument> {


	@Override
	public boolean satisfies(Argument formula) throws IllegalArgumentException {
		return this.getArgumentsOfStatus(ArgumentStatus.IN).contains(formula);
	}

	
	@Override
	public boolean satisfies(T beliefBase) throws IllegalArgumentException {
		throw new IllegalArgumentException("Satisfaction of belief bases by extensions is undefined.");
	}


	
	/**
	 * Returns all arguments that have the given status in this interpretation.
	 * @param status the status of the arguments to be returned.
	 * @return the set of arguments with the given status.
	 */
	public abstract Extension getArgumentsOfStatus(ArgumentStatus status);
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public abstract String toString();
}
