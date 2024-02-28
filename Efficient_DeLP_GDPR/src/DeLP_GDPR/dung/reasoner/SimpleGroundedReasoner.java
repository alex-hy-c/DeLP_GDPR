package DeLP_GDPR.dung.reasoner;




import java.util.*;

import DeLP_GDPR.dung.semantics.*;
import DeLP_GDPR.dung.syntax.DungTheory;


/**
 * This reasoner for Dung theories performs inference on the grounded extension.
 * Computes the (unique) grounded extension, i.e., the least fixpoint of the characteristic function faf.
 *
 */
public class SimpleGroundedReasoner extends AbstractExtensionReasoner {


	/* (non-Javadoc)
	 * @see DeLP_GDPR.dung.reasoner.AbstractExtensionReasoner#getModels(DeLP_GDPR.dung.syntax.DungTheory)
	 */
	@Override
	public Collection<Extension<DungTheory>> getModels(DungTheory bbase) {
		Collection<Extension<DungTheory>> extensions = new HashSet<Extension<DungTheory>>();
		extensions.add(this.getModel(bbase));
		return extensions;
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.dung.reasoner.AbstractExtensionReasoner#getModel(DeLP_GDPR.dung.syntax.DungTheory)
	 */
	@Override
	public Extension<DungTheory> getModel(DungTheory bbase) {
		Extension<DungTheory> ext = new Extension<DungTheory>();
		int size;
		do{
			size = ext.size();			
			ext = ((DungTheory)bbase).faf(ext);			
		}while(size!=ext.size());		
		return ext;
	}


}