package DeLP_GDPR.dung.syntax;




import java.util.Collection;

import org.tweetyproject.commons.BeliefBase;

import DeLP_GDPR.dung.semantics.Extension;


public interface ArgumentationFramework<Arg> extends BeliefBase{
	/**
	 * 
	 * @param c parameter
	 * @return containsAll
	 */
	boolean containsAll(Collection<?> c);
	/**
	 * 
	 * @param a parameter
	 * @param ext parameter
	 * @return isAttacked
	 */
	boolean isAttacked(Arg a, Extension<? extends ArgumentationFramework> ext);
	/**
	 * 
	 * @return returns all the nodes
	 */
	Collection<Arg> getNodes();


}
