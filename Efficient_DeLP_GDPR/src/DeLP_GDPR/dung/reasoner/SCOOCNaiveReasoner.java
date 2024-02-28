package DeLP_GDPR.dung.reasoner;

import DeLP_GDPR.dung.semantics.Extension;
import DeLP_GDPR.dung.syntax.Argument;
import DeLP_GDPR.dung.syntax.ArgumentationFramework;
import DeLP_GDPR.dung.syntax.DungTheory;
import DeLP_GDPR.graphs.DefaultGraph;

import java.util.*;

/**
 * reasoner for SCOOC-naive semantics. A naive extension E is strongly complete outside odd cycles (SCOOC)
 * iff every argument, which is not in an odd cycle, is either in E or attacked by E.
 *
 * definition see:
 * Cramer, van der Torre: SCF2 – an Argumentation Semantics for Rational Human Judgments on Argument Acceptability:Technical Report 2019
 */
public class SCOOCNaiveReasoner extends AbstractExtensionReasoner {
    @Override
    public Collection<Extension<DungTheory>> getModels(DungTheory bbase) {
        Set<Stack<Argument>> cycles = DefaultGraph.getCyclesIncludingSelfLoops((DungTheory) bbase);
        Collection<Argument> cycleArguments = new HashSet<>();
        // store all arguments which are part of an odd cycle in a collection for efficiency reasons
        for (Stack<Argument> cycle: cycles) {
            if (cycle.size() % 2 == 0) {
                cycleArguments.addAll(cycle);
            }
        }
        // we only have to consider naive extensions
        Collection<Extension<DungTheory>> naiveExtensions = new SimpleNaiveReasoner().getModels(bbase);
        Collection<Extension<DungTheory>> extensions = new HashSet<>();
        for (Extension<DungTheory> ext: naiveExtensions) {
            boolean scooc = true;
            Collection<Argument> remainingArguments = new HashSet<>((DungTheory) bbase);
            remainingArguments.removeAll(ext);
            // if there is any argument, not attacked by ext and not part of an odd cycle, ext is not scooc
            for (Argument a: remainingArguments) {
                if (((DungTheory) bbase).isAttacked(a, ext)) {
                    continue;
                }
                Collection<Argument> args = ((DungTheory) bbase).getAttackers(a);
                args.add(a);
                args.retainAll(cycleArguments);
                if (args.isEmpty()) {
                    scooc = false;
                    break;
                }
            }
            if (scooc) {
                extensions.add(ext);
            }
        }
        return extensions;
    }

    @Override
    public Extension<DungTheory> getModel(DungTheory bbase) {
        Collection<Extension<DungTheory>> extensions = this.getModels(bbase);
        return extensions.iterator().next();
    }
    
	/**
	 * the solver is natively installed and is therefore always installed
	 */
	@Override
	public boolean isInstalled() {
		return true;
	}
}
