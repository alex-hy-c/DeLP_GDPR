package DeLP_GDPR.dung.reasoner;




import java.util.Collection;

import java.util.HashSet;
import java.util.Set;

import DeLP_GDPR.dung.semantics.ArgumentStatus;
import DeLP_GDPR.dung.semantics.Extension;
import DeLP_GDPR.dung.semantics.Labeling;
import DeLP_GDPR.dung.syntax.ArgumentationFramework;
import DeLP_GDPR.dung.syntax.DungTheory;

/**
 * This reasoner for Dung theories performs inference on the stage extensions.
 *
 */
public class SimpleStageReasoner extends AbstractExtensionReasoner {

	/* (non-Javadoc)
	 * @see DeLP_GDPR.dung.reasoner.AbstractExtensionReasoner#getModels(DeLP_GDPR.dung.syntax.DungTheory)
	 */
	@Override
	public Collection<Extension<DungTheory>> getModels(DungTheory bbase) {
		// A stage extension is a conflict-free set with minimal undecided arguments
		Collection<Extension<DungTheory>> cfExt = new SimpleConflictFreeReasoner().getModels(bbase);
		Set<Labeling> cfLab = new HashSet<Labeling>();
		for(Extension<DungTheory> e: cfExt)
			cfLab.add(new Labeling((DungTheory) bbase,e));
		Set<Extension<DungTheory>> result = new HashSet<Extension<DungTheory>>();
		boolean stage;
		for(Labeling lab: cfLab){
			stage = true;
			for(Labeling lab2: cfLab){
				if(lab != lab2){
					if(lab.getArgumentsOfStatus(ArgumentStatus.UNDECIDED).containsAll(lab2.getArgumentsOfStatus(ArgumentStatus.UNDECIDED)) &&
							!lab.getArgumentsOfStatus(ArgumentStatus.UNDECIDED).equals(lab2.getArgumentsOfStatus(ArgumentStatus.UNDECIDED)) ){
						stage = false;
						break;
					}
				}
			}
			if(stage){
				result.add((Extension<DungTheory>) lab.getArgumentsOfStatus(ArgumentStatus.IN));
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.dung.reasoner.AbstractExtensionReasoner#getModel(DeLP_GDPR.dung.syntax.DungTheory)
	 */
	@Override
	public Extension<DungTheory> getModel(DungTheory bbase) {
		// just return the first one
		return this.getModels(bbase).iterator().next();
	}
}
