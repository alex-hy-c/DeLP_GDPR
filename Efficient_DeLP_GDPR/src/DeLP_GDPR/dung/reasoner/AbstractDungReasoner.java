package DeLP_GDPR.dung.reasoner;

import org.tweetyproject.commons.QualitativeReasoner;

import DeLP_GDPR.dung.syntax.Argument;
import DeLP_GDPR.dung.syntax.DungTheory;

/**
 * Ancestor class for all AAF reasoners.

 *
 */
public abstract class AbstractDungReasoner implements QualitativeReasoner<DungTheory,Argument>{

	
	@Override
	public abstract Boolean query(DungTheory beliefbase, Argument formula);

}
