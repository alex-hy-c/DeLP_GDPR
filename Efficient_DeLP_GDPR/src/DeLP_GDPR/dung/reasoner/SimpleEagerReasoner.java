package DeLP_GDPR.dung.reasoner;

import DeLP_GDPR.dung.semantics.ArgumentStatus;
import DeLP_GDPR.dung.semantics.Extension;
import DeLP_GDPR.dung.semantics.Labeling;
import DeLP_GDPR.dung.syntax.DungTheory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * simple reasoner for eager semantics
 */
public class SimpleEagerReasoner extends AbstractExtensionReasoner {
    @Override
    public Collection<Extension<DungTheory>> getModels(DungTheory bbase) {
        Collection<Extension<DungTheory>> exts = new HashSet<Extension<DungTheory>>();
        exts.add(this.getModel(bbase));
        return exts;
    }

    @Override
    public Extension<DungTheory> getModel(DungTheory bbase) {
        Collection<Extension<DungTheory>> admExt = new SimpleAdmissibleReasoner().getModels(bbase);
        Collection<Extension<DungTheory>> sstExt = new SimpleSemiStableReasoner().getModels(bbase);
        Set<Labeling> potResult = new HashSet<Labeling>();
        boolean potEager;
        for(Extension<DungTheory> ext: admExt){
            Labeling extLab = new Labeling((DungTheory)bbase,ext);
            // ext is eager if
            // 1. for every semi-stable labeling L both in and out are subsets of that sets in L
            potEager = true;
            for(Extension<DungTheory> ext2: sstExt){
                Labeling extLab2 = new Labeling((DungTheory)bbase, ext2);
                if(!extLab2.getArgumentsOfStatus(ArgumentStatus.IN).containsAll(extLab.getArgumentsOfStatus(ArgumentStatus.IN))){
                    potEager = false;
                    break;
                }
                if(!extLab2.getArgumentsOfStatus(ArgumentStatus.OUT).containsAll(extLab.getArgumentsOfStatus(ArgumentStatus.OUT))){
                    potEager = false;
                    break;
                }
            }
            if(potEager)
                potResult.add(extLab);
        }
        // get the one which maximizes in and out
        // Note that there is only one eager extension
        boolean eager;
        for(Labeling lab: potResult){
            eager = true;
            for(Labeling lab2: potResult){
                if(lab != lab2)
                    if(lab2.getArgumentsOfStatus(ArgumentStatus.IN).containsAll(lab.getArgumentsOfStatus(ArgumentStatus.IN)))
                        if(lab2.getArgumentsOfStatus(ArgumentStatus.OUT).containsAll(lab.getArgumentsOfStatus(ArgumentStatus.OUT))){
                            eager = false;
                            break;
                        }
            }
            if(eager)
                return (Extension<DungTheory>) lab.getArgumentsOfStatus(ArgumentStatus.IN);
        }
        // this should not happen as there is always an eager extension;
        throw new RuntimeException("Eager extension seems to be undefined.");
    }
}
