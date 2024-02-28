package DeLP_GDPR.dung.reasoner;




import java.util.*;

import DeLP_GDPR.dung.semantics.*;
import DeLP_GDPR.dung.syntax.*;


/**
 * This reasoner for Dung theories performs inference on the complete extensions.
 * Computes the set of all complete extensions, i.e., all admissible sets that contain all their acceptable arguments.
 *
 */
public class SimpleCompleteReasoner extends AbstractExtensionReasoner {

	@Override
	public Collection<Extension<DungTheory>> getModels(DungTheory bbase) {
		Extension<DungTheory> groundedExtension = new SimpleGroundedReasoner().getModel(bbase);
		Set<Argument> remaining = new HashSet<Argument>((DungTheory) bbase);
		remaining.removeAll(groundedExtension);
		return this.getCompleteExtensions((DungTheory) bbase,groundedExtension,remaining);	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.dung.reasoner.AbstractExtensionReasoner#getModel(DeLP_GDPR.dung.syntax.DungTheory)
	 */
	@Override
	public Extension<DungTheory> getModel(DungTheory bbase) {
		// as the grounded extension is also complete, we return that one
		return new SimpleGroundedReasoner().getModel(bbase);
	}
		
	/**
	 * Auxiliary method to compute all complete extensions
	 * @param dungTheory a Dung theory
	 * @param ext some extension
	 * @param remaining arguments that still have to be considered to be part of an extension
	 * @return all complete extensions that are supersets of an argument in <source>arguments</source>
	 */
	private Set<Extension<DungTheory>> getCompleteExtensions(DungTheory dungTheory, Extension<DungTheory> ext, Collection<Argument> remaining){
		Set<Extension<DungTheory>> extensions = new HashSet<Extension<DungTheory>>();
		if(dungTheory.isConflictFree(ext)){
			if(dungTheory.faf(ext).equals(ext))
				extensions.add(ext);
			if(!remaining.isEmpty()){
				Argument arg = remaining.iterator().next();
				Collection<Argument> remaining2 = new HashSet<Argument>(remaining);
				remaining2.remove(arg);
				extensions.addAll(this.getCompleteExtensions(dungTheory,ext, remaining2));
				Extension<DungTheory> ext2 = new Extension<DungTheory>(ext);
				ext2.add(arg);
				extensions.addAll(this.getCompleteExtensions(dungTheory,ext2, remaining2));
			}
		}
		return extensions;		
	}
}