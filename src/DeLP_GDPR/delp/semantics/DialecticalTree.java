package DeLP_GDPR.delp.semantics;



import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import DeLP_GDPR.delp.reasoner.DelpReasoner;
import DeLP_GDPR.delp.syntax.*;
import DeLP_GDPR.logics.fol.syntax.*;

/**
 * This class implements a node of a dialectical tree from DeLP.
 *
 */
public class DialecticalTree{

	public enum Mark {
        DEFEATED,
        UNDEFEATED;
        @Override
        public String toString() {
            return name().substring(0,1); // first character as String
        }
    }

	/**
     * The argument in this node
	 */
    private final DelpArgument argument;

	/**
	 * The parent node; <source>null</source> if this node is a root
	 */
	private final DialecticalTree parent;

	/**
	 * The children of this node; size=0 indicates a leaf
	 */
	private final Set<DialecticalTree> children = new HashSet<>();

	/**
	 * constructor; initializes this dialectical tree node as a root with given argument
	 * @param argument a DeLP argument
	 */
	public DialecticalTree(DelpArgument argument){
		this(null, argument);
	}

    // for building the linked tree structure
    private DialecticalTree(DialecticalTree parent, DelpArgument argument){
        if (argument == null)
            throw new IllegalArgumentException("Cannot instantiate dialectical tree with NULL argument");
		this.parent = parent;
		this.argument = argument;
	}

	/**
	 * Computes the set of arguments which are defeaters for the argument in this tree node and returns
	 * the corresponding dialectical tree nodes with these defeaters. For the computation of the defeaters
	 * the whole argumentation line to this tree node is considered. As a side effect the computed tree nodes
	 * are added as children of this node
     *
	 * @param delp a defeasible logic program
	 * @param comparisonCriterion a comparison criterion.
	 * @return the set of defeater nodes of the argument in this node
	 */
	public Set<DialecticalTree> getDefeaters(DefeasibleLogicProgram delp,
                                             ComparisonCriterion comparisonCriterion){
        // test parameters:
        if (delp == null)
            throw new IllegalArgumentException("Cannot compute defeaters for NULL DeLP");
		Set<FolFormula> attackOpportunities = argument.getAttackOpportunities(delp);

        //gather attacks of last argument in the line
        Set<DelpArgument> attacks = new HashSet<>();
        for (FolFormula lit : attackOpportunities) {
        	attacks.addAll(DelpReasoner.getArgumentsWithConclusion(delp, lit));            
        }

		//for each attacker check acceptability
        Set<DelpArgument> defeaters = attacks.stream()
                .filter(attack -> isAcceptable(attack,delp,comparisonCriterion))
                .collect(Collectors.toSet());

		//build dialectical tree nodes
        children.clear();
        children.addAll(defeaters.stream()
                .map(defeater -> new DialecticalTree(this, defeater))
                .collect(Collectors.toSet()));
		return children;
	}

	/**
	 * Checks whether the argumentation line composed of the ancestors of this node and the parameter
	 * <code>argument</code> is acceptable given the parameter <code>delp</code>
	 * @param argument a DeLP argument
	 * @param delp a defeasible logic program
	 * @param comparisonCriterion a comparison criterion.
	 * @return <code>true</code> if the corresponding argumentation line is acceptable
	 */
	public boolean isAcceptable(DelpArgument argument,
                                 DefeasibleLogicProgram delp,
                                 ComparisonCriterion comparisonCriterion){
		List<DelpArgument> argumentationLine = getArgumentationLine().collect(Collectors.toList());

		//Subargument test: return FALSE if any subargument found
        if (argumentationLine.stream().anyMatch(argument::isSubargumentOf))
            return false;

		//Concordance
		Set<DefeasibleRule> rules = new HashSet<>(argument.getSupport());
        // collect every other element in argumentation line
        // beginning from the second last element (one before root):
		for(int i = argumentationLine.size()-2; i >= 0; i -= 2)
			rules.addAll(argumentationLine.get(i).getSupport());
		if(!delp.isConsistent(rules))
			return false;

		//Blocking attack
        if (comparisonCriterion == null)
            comparisonCriterion = new EmptyCriterion();
        DelpArgument disagreementSubargument = argumentationLine.get(argumentationLine.size()-1).getDisagreementSubargument(argument.getConclusion(), delp);
		if(comparisonCriterion.compare(argument, disagreementSubargument, delp) == ComparisonCriterion.Result.IS_WORSE)
			return false;

        //Proper attack
		if(argumentationLine.size()>1){
			DelpArgument arg1 = argumentationLine.get(argumentationLine.size()-1);
			DelpArgument arg2 = argumentationLine.get(argumentationLine.size()-2).getDisagreementSubargument(arg1.getConclusion(), delp);
			if(comparisonCriterion.compare(arg1, arg2, delp) == ComparisonCriterion.Result.NOT_COMPARABLE)
				if(comparisonCriterion.compare(argument, disagreementSubargument, delp) != ComparisonCriterion.Result.IS_BETTER)
					return false;
		}
		return true;
	}

	/**
	 * Returns the argumentation line which is generated by the path from this node to the root (in reverse)
	 * @return a stream of arguments denoting the argumentation line
	 */
    public Stream<DelpArgument> getArgumentationLine(){
        return Stream
                .concat(
                        parent==null?
                                Stream.empty():
                                parent.getArgumentationLine(), // walk up tree hierarchy
                        Stream.of(argument));
    }

	/**
	 * Computes the marking of this node by considering the marking of all child nodes
	 * @return one of DialecticalTree.MARK_DEFEATED and DialecticalTree.MARK_UNDEFEATED
	 */
	public Mark getMarking(){
        for (DialecticalTree dtree : children)
            if(dtree.getMarking().equals(Mark.UNDEFEATED))
                return Mark.DEFEATED;
        return Mark.UNDEFEATED;
	}

	//Misc Methods

//	public String toString(){
//		StringBuilder s = new StringBuilder("["+argument);
//        if (!children.isEmpty())
//            s.append(" - ");
//        s.append(children.stream()
//                .map(Object::toString)
//                .collect(Collectors.joining(", ")));
//		s.append("]");
//		return s.toString();
//	}
	
    public String toString() {
        StringBuilder s = new StringBuilder();
        buildString(s, "", true);
        return s.toString();
    }

    // Helper method to build the string representation
    private void buildString(StringBuilder s, String prefix, boolean isTail) {
        s.append(prefix).append(isTail ? "└── " : "├── ").append(argument).append("[").append(getMarking()).append("]").append("\n");
        ArrayList<DialecticalTree> childrenList = new ArrayList<>(children);
        for (int i = 0; i < childrenList.size() - 1; i++) {
            childrenList.get(i).buildString(s, prefix + (isTail ? "    " : "│   "), false);
        }
        if (childrenList.size() > 0) {
            childrenList.get(childrenList.size() - 1)
                    .buildString(s, prefix + (isTail ? "    " : "│   "), true);
        }
    }
	

}