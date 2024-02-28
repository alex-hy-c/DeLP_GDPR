package DeLP_GDPR.dung.reasoner;




import java.util.*;

import DeLP_GDPR.dung.semantics.*;
import DeLP_GDPR.dung.syntax.*;

/**
 * This reasoner for Dung theories performs inference on the stable extensions.
 * Computes the set of all stable extensions, i.e., all conflict-free sets that attack each other argument.
 * For that, it uses the SimpleSccCompleteReasoner to first compute all complete extensions, and
 * then filters out the non-stable ones.
 * @author Matthias Thimm
 *
 */
public class SimpleStableReasoner extends AbstractExtensionReasoner {


	/* (non-Javadoc)
	 * @see DeLP_GDPR.dung.reasoner.AbstractExtensionReasoner#getModels(DeLP_GDPR.dung.syntax.DungTheory)
	 */
	@Override
	public Collection<Extension<DungTheory>> getModels(DungTheory bbase) {
		Collection<Extension<DungTheory>> completeExtensions = new SimpleSccCompleteReasoner().getModels(bbase);
		Set<Extension<DungTheory>> result = new HashSet<Extension<DungTheory>>();
		for(Extension<DungTheory> e: completeExtensions)
			if(((DungTheory)bbase).isAttackingAllOtherArguments(e))
				result.add(e);
		return result;	
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.dung.reasoner.AbstractExtensionReasoner#getModel(DeLP_GDPR.dung.syntax.DungTheory)
	 */
	@Override
	public Extension<DungTheory> getModel(DungTheory bbase) {
		// returns the first found stable extension
		Collection<Extension<DungTheory>> completeExtensions = new SimpleSccCompleteReasoner().getModels(bbase);
		for(Extension<DungTheory> e: completeExtensions)
			if(((DungTheory)bbase).isAttackingAllOtherArguments(e))
				return e;
		return null;	
	}		
}
