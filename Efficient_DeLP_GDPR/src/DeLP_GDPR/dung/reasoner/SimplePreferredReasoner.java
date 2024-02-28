package DeLP_GDPR.dung.reasoner;




import java.util.*;

import DeLP_GDPR.dung.semantics.*;
import DeLP_GDPR.dung.syntax.*;


/**
 * This reasoner for Dung theories performs inference on the preferred extensions.
 * Computes the set of all preferred extensions, i.e., all maximal admissable sets.
 * It does so by first computing all complete extensions and then check for
 * set maximality.
 *
 */
public class SimplePreferredReasoner extends AbstractExtensionReasoner {	

	/* (non-Javadoc)
	 * @see DeLP_GDPR.dung.reasoner.AbstractExtensionReasoner#getModels(DeLP_GDPR.dung.syntax.DungTheory)
	 */
	@Override
	public Collection<Extension<DungTheory>> getModels(DungTheory bbase) {
		Collection<Extension<DungTheory>> completeExtensions = new SimpleSccCompleteReasoner().getModels(bbase);
		Set<Extension<DungTheory>> result = new HashSet<Extension<DungTheory>>();
		boolean maximal;
		for(Extension<DungTheory> e1: completeExtensions){
			maximal = true;
			for(Extension<DungTheory> e2: completeExtensions)
				if(e1 != e2 && e2.containsAll(e1)){
					maximal = false;
					break;
				}
			if(maximal)
				result.add(e1);			
		}		
		return result;
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.dung.reasoner.AbstractExtensionReasoner#getModel(DeLP_GDPR.dung.syntax.DungTheory)
	 */
	@Override
	public Extension<DungTheory> getModel(DungTheory bbase) {
		// just return the first found preferred extension
		Collection<Extension<DungTheory>> completeExtensions = new SimpleSccCompleteReasoner().getModels(bbase);
		boolean maximal;
		for(Extension<DungTheory> e1: completeExtensions){
			maximal = true;
			for(Extension<DungTheory> e2: completeExtensions)
				if(e1 != e2 && e2.containsAll(e1)){
					maximal = false;
					break;
				}
			if(maximal)
				return e1;			
		}		
		// this should not happen
		throw new RuntimeException("Hmm, did not find a maximal set in a finite number of sets. Should not happen.");
	}
}