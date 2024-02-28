package DeLP_GDPR.dung.reasoner;

import DeLP_GDPR.dung.semantics.Extension;
import DeLP_GDPR.dung.syntax.*;

import java.util.*;

/**
 * Reasoner for naive extensions. naive extensions are maximal conflict-free sets
 */
public class SimpleNaiveReasoner extends AbstractExtensionReasoner {
    public Collection<Extension<DungTheory>> getModels(DungTheory bbase) {
        DungTheory restrictedTheory = new DungTheory((DungTheory)bbase);
        // remove all self-attacking arguments
        for (Argument argument: (DungTheory)bbase) {
            if (restrictedTheory.isAttackedBy(argument, argument)) {
                restrictedTheory.remove(argument);
            }
        }
        return this.getMaximalConflictFreeSets((DungTheory)bbase, restrictedTheory);
    }

    public Extension<DungTheory> getModel(DungTheory bbase) {
        Collection<Extension<DungTheory>> extensions = this.getModels(bbase);
        return extensions.iterator().next();
    }

    /**
     * computes all maximal conflict-free sets of bbase
     * @param bbase an argumentation framework
     * @param candidates a set of arguments
     * @return conflict-free sets in bbase
     */
    public Collection<Extension<DungTheory>> getMaximalConflictFreeSets(DungTheory bbase, Collection<Argument> candidates) {
        Collection<Extension<DungTheory>> cfSubsets = new HashSet<Extension<DungTheory>>();
        if (candidates.size() == 0 || bbase.size() == 0) {
            cfSubsets.add(new Extension<DungTheory>());
        } else {
            for (Argument element: candidates) {
                DungTheory remainingTheory = new DungTheory(bbase);
                remainingTheory.remove(element);
                remainingTheory.removeAll(bbase.getAttacked(element));

                Set<Argument> remainingCandidates = new HashSet<Argument>(candidates);
                remainingCandidates.remove(element);
                remainingCandidates.removeAll(bbase.getAttacked(element));
                remainingCandidates.removeAll(bbase.getAttackers(element));

                Collection<Extension<DungTheory>> subsubsets = this.getMaximalConflictFreeSets(remainingTheory, remainingCandidates);

                for (Extension<DungTheory> subsubset : subsubsets) {
                    //cfSubsets.add(new Extension(subsubset));
                    subsubset.add(element);
                    cfSubsets.add(new Extension<DungTheory>(subsubset));
                }
            }
        }
        return cfSubsets;
    }
}
