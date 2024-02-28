package DeLP_GDPR.dung.syntax;

import org.tweetyproject.commons.Formula;

import DeLP_GDPR.dung.ldo.syntax.LdoFormula;

/**
 * This interface captures common methods of arguments and attacks of
 * abstract argumentation theories.
 *
 */
public interface DungEntity extends Formula{
	
	/**
	 * Returns a logical representation of this entity in LDO
	 * (Logic of dialectical outcomes, cf. [Hunter, Thimm, 2015])
	 * @return the logical formula of this entity.
	 */
	public LdoFormula getLdoFormula();
}
