package DeLP_GDPR.dung.reasoner;




import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.tweetyproject.commons.util.SetTools;

import DeLP_GDPR.dung.semantics.Extension;
import DeLP_GDPR.dung.syntax.Argument;
import DeLP_GDPR.dung.syntax.DungTheory;

/**
 * This reasoner for Dung theories performs inference on the conflict-free extensions.
 *
 */
public class SimpleConflictFreeReasoner extends AbstractExtensionReasoner {

	
	@Override
	public Collection<Extension<DungTheory>> getModels(DungTheory bbase) {
		Set<Extension<DungTheory>> extensions = new HashSet<Extension<DungTheory>>();
		// Check all subsets
		for(Set<Argument> ext: new SetTools<Argument>().subsets((DungTheory)bbase))
			if(((DungTheory)bbase).isConflictFree(new Extension<DungTheory>(ext)))
				extensions.add(new Extension<DungTheory>(ext));
		return extensions;
	}

	
	@Override
	public Extension<DungTheory> getModel(DungTheory bbase) {
		// as the empty set is always conflict-free we return that one.
		return new Extension<DungTheory>();
	}
}
