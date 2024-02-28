package DeLP_GDPR.logics.commons.syntax.interfaces;

import DeLP_GDPR.math.probability.Probability;


public interface ProbabilityAware {
	
	/** @return this formula's probability in the uniform distribution. */
	public Probability getUniformProbability();
}