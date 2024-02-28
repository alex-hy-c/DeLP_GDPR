package DeLP_GDPR.logics.fol.syntax;




import java.util.*;

import org.tweetyproject.commons.*;


/**
 * This class models a first-order knowledge base, i.e. a set of formulas
 * in first-order logic.
 *
 */
public class FolBeliefSet extends BeliefSet<FolFormula,FolSignature>{
	
	/**
	 * Creates a new and empty first-order knowledge base.
	 */
	public FolBeliefSet(){
		super();
	}
	
	/**
	 * Creates a new first-order knowledge base with the given set of formulas.
	 * @param formulas the formulas
	 */
	public FolBeliefSet(Collection<FolFormula> formulas){
		super(formulas);
	}
	
	@Override
	public FolSignature getMinimalSignature() {
		FolSignature sig = new FolSignature();
		sig.addAll(this);
		return sig;
	}

	@Override
	protected FolSignature instantiateSignature() {
		return new FolSignature();
	}
}
