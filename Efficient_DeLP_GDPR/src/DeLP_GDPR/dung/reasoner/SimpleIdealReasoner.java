package DeLP_GDPR.dung.reasoner;



import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import DeLP_GDPR.dung.semantics.ArgumentStatus;
import DeLP_GDPR.dung.semantics.Extension;
import DeLP_GDPR.dung.semantics.Labeling;
import DeLP_GDPR.dung.syntax.ArgumentationFramework;
import DeLP_GDPR.dung.syntax.DungTheory;


public class SimpleIdealReasoner extends AbstractExtensionReasoner {


	/* (non-Javadoc)
	 * @see DeLP_GDPR.dung.reasoner.AbstractExtensionReasoner#getModels(DeLP_GDPR.dung.syntax.DungTheory)
	 */
	@Override
	public Collection<Extension<DungTheory>> getModels(DungTheory bbase) {
		Collection<Extension<DungTheory>> exts = new HashSet<Extension<DungTheory>>();
		exts.add(this.getModel(bbase));
		return exts;
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.dung.reasoner.AbstractExtensionReasoner#getModel(DeLP_GDPR.dung.syntax.DungTheory)
	 */
	@Override
	public Extension<DungTheory> getModel(DungTheory bbase) {
		Collection<Extension<DungTheory>> admExt = new SimpleAdmissibleReasoner().getModels(bbase);
		Collection<Extension<DungTheory>> prefExt = new SimplePreferredReasoner().getModels(bbase);
		Set<Labeling> potResult = new HashSet<Labeling>();
		boolean potIdeal; 
		for(Extension<DungTheory> ext: admExt){
			Labeling extLab = new Labeling(bbase,ext);
			// ext is ideal if
			// 1. for every preferred labeling L both in and out are subsets of that sets in L
			potIdeal = true;
			for(Extension<DungTheory> ext2: prefExt){
				Labeling extLab2 = new Labeling((DungTheory)bbase, ext2);
				if(!extLab2.getArgumentsOfStatus(ArgumentStatus.IN).containsAll(extLab.getArgumentsOfStatus(ArgumentStatus.IN))){
					potIdeal = false;
					break;
				}
				if(!extLab2.getArgumentsOfStatus(ArgumentStatus.OUT).containsAll(extLab.getArgumentsOfStatus(ArgumentStatus.OUT))){
					potIdeal = false;
					break;
				}
			}
			if(potIdeal)				
				potResult.add(extLab);			
		}		
		// get the one which maximizes in and out
		// Note that there is only one ideal extension
		boolean ideal;
		for(Labeling lab: potResult){
			ideal = true;
			for(Labeling lab2: potResult){
				if(lab != lab2)
					if(lab2.getArgumentsOfStatus(ArgumentStatus.IN).containsAll(lab.getArgumentsOfStatus(ArgumentStatus.IN)))
						if(lab2.getArgumentsOfStatus(ArgumentStatus.OUT).containsAll(lab.getArgumentsOfStatus(ArgumentStatus.OUT))){
							ideal = false;
							break;
						}
			}
			if(ideal)
				return (Extension<DungTheory>) lab.getArgumentsOfStatus(ArgumentStatus.IN);			
		}		
		// this should not happen as there is always an ideal extension;
		throw new RuntimeException("Ideal extension seems to be undefined.");
	}
}