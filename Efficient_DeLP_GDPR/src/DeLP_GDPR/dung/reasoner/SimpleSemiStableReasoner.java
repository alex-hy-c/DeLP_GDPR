package DeLP_GDPR.dung.reasoner;



import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import DeLP_GDPR.dung.semantics.ArgumentStatus;
import DeLP_GDPR.dung.semantics.Extension;
import DeLP_GDPR.dung.semantics.Labeling;
import DeLP_GDPR.dung.syntax.ArgumentationFramework;
import DeLP_GDPR.dung.syntax.DungTheory;


/**
 * This reasoner calculates claim based semi stable extensions
 */
public class SimpleSemiStableReasoner extends AbstractExtensionReasoner {
	
	/**
	 * 
	 * @param bbase the claim based thory
	 * @return all extensions of the semantics
	 */
	@Override
	public Collection<Extension<DungTheory>> getModels(DungTheory bbase) {
		// check all complete extensions and remove those sets with non-mininal set of undecided arguments
		Collection<Extension<DungTheory>> exts = new SimpleCompleteReasoner().getModels(bbase);
		Map<Extension<DungTheory>,Extension<DungTheory>> extUndec = new HashMap<Extension<DungTheory>,Extension<DungTheory>>();
		for(Extension<DungTheory> ext: exts)
			extUndec.put(ext, (Extension<DungTheory>) new Labeling(bbase,ext).getArgumentsOfStatus(ArgumentStatus.UNDECIDED));
		boolean b;
		for(Extension<DungTheory> ext: extUndec.keySet()){
			b = false;
			for(Extension<DungTheory> ext2: extUndec.keySet()){
				if(ext != ext2){
					if(extUndec.get(ext).containsAll(extUndec.get(ext2)) && !extUndec.get(ext2).containsAll(extUndec.get(ext))){
						exts.remove(ext);
						b = true;
					}
				}			
				if(b) break;
			}			
		}
		return exts;
	}

	/**
	 * 
	 * @param bbase the claim based thory
	 * @return an extensions of the semantics
	 */
	@Override
	public Extension<DungTheory> getModel(DungTheory bbase) {
		// just return the first one (which is always defined)
		return this.getModels(bbase).iterator().next();
	}
}