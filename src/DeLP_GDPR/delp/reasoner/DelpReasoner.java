package DeLP_GDPR.delp.reasoner;

import org.tweetyproject.commons.Reasoner;
import org.tweetyproject.commons.Formula;

import DeLP_GDPR.commons.util.rules.Derivation;
import DeLP_GDPR.commons.util.rules.Rule;
import DeLP_GDPR.delp.semantics.ComparisonCriterion;
import DeLP_GDPR.delp.semantics.DelpAnswer;
import DeLP_GDPR.delp.semantics.DialecticalTree;
import DeLP_GDPR.delp.semantics.EmptyCriterion;
import DeLP_GDPR.delp.semantics.DelpAnswer.Type;
import DeLP_GDPR.delp.syntax.DefeasibleLogicProgram;
import DeLP_GDPR.delp.syntax.DefeasibleRule;
import DeLP_GDPR.delp.syntax.DelpArgument;
import DeLP_GDPR.delp.syntax.DelpRule;
import DeLP_GDPR.logics.fol.syntax.FolFormula;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This reasoner performs default dialectical reasoning on some given DeLP.
 *
 */
public class DelpReasoner implements Reasoner<DelpAnswer.Type, DefeasibleLogicProgram, FolFormula> {
	/**
	 * The comparison criterion is initialized with the "empty criterion"
	 */
	private ComparisonCriterion comparisonCriterion = new EmptyCriterion();
	static HashMap<Formula, Set<Derivation<DelpRule>>> derivations_hashmap = new HashMap<Formula, Set<Derivation<DelpRule>>>();

	/**
	 * Creates a new DelpReasoner for the given delp. *
	 * 
	 * @param comparisonCriterion a comparison criterion used for inference
	 */
	public DelpReasoner(ComparisonCriterion comparisonCriterion) {
		this.comparisonCriterion = comparisonCriterion;

	}

	/**
	 * returns the comparison criterion used in this program
	 * 
	 * @return the comparison criterion used in this program
	 */
	public ComparisonCriterion getComparisonCriterion() {
		return comparisonCriterion;
	}

	/**
	 * Computes the subset of the arguments of this program, that are warrants.
	 * 
	 * @param delp a delp
	 * @return a set of <code>DelpArgument</code> that are warrants
	 */
	public Set<DelpArgument> getWarrants(DefeasibleLogicProgram delp) {
		DefeasibleLogicProgram groundDelp = delp.ground();
		Set<DelpArgument> all_arguments = groundDelp.ground().getArguments();
		return all_arguments.stream().filter(argument -> isWarrant(groundDelp, argument)).collect(Collectors.toSet());
	}

	/**
	 * Checks whether the given argument is a warrant regarding a given set of
	 * arguments
	 * 
	 * @param groundDelp a grounded DeLP
	 * @param argument   a DeLP argument
	 * 
	 * @return <source>true</source> iff <source>argument</source> is a warrant
	 *         given <source>arguments</source>.
	 */
	private boolean isWarrant(DefeasibleLogicProgram groundDelp, DelpArgument argument) {
		DialecticalTree dtree = new DialecticalTree(argument);
		Deque<DialecticalTree> stack = new ArrayDeque<>();
		stack.add(dtree);
		while (!stack.isEmpty()) {
			DialecticalTree dtree2 = stack.pop();
			stack.addAll(dtree2.getDefeaters(groundDelp, comparisonCriterion));
		}
		return dtree.getMarking().equals(DialecticalTree.Mark.UNDEFEATED);
	}

	/**
	 * Returns all arguments with the given conclusion from the delp.
	 * 
	 * @param delp some delp.
	 * @param f    a formula
	 * @return all arguments with the given conclusion from the delp.
	 */
	public static Set<DelpArgument> getArgumentsWithConclusion(DefeasibleLogicProgram delp, FolFormula f) {
		Collection<DelpRule> allRules = new HashSet<DelpRule>();
		allRules.addAll(delp);
		Set<Derivation<DelpRule>> allDerivations;
		if (derivations_hashmap.containsKey(f)) {
			allDerivations = derivations_hashmap.get(f);
		} else {
			allDerivations = Derivation.allDerivations(allRules, f);
			derivations_hashmap.put(f, allDerivations);
		}
		Set<DelpArgument> arguments = new HashSet<>();

		for (Derivation<DelpRule> derivation : allDerivations) {
			Set<DefeasibleRule> rules = derivation.stream().filter(rule -> rule instanceof DefeasibleRule)
					.map(rule -> (DefeasibleRule) rule).collect(Collectors.toSet());
			// consistency check: rules have to be consistent with strict knowledge part
			if (delp.isConsistent(rules))
				arguments.add(new DelpArgument(rules, (FolFormula) derivation.getConclusion()));
		}
		// subargument test
		Set<DelpArgument> result = new HashSet<>();
		for (DelpArgument argument1 : arguments) {
			boolean is_minimal = true;
			for (DelpArgument argument2 : arguments) {
				if (argument1.getConclusion().equals(argument2.getConclusion())
						&& argument2.isStrongSubargumentOf(argument1)) {
					is_minimal = false;
					break;
				}
			}
			if (is_minimal)
				result.add(argument1);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tweetyproject.commons.Reasoner#query(org.tweetyproject.commons.
	 * BeliefBase, org.tweetyproject.commons.Formula)
	 */
	@Override
	public Type query(DefeasibleLogicProgram delp, FolFormula f) {
		// check query:
		if (!f.isLiteral())
			throw new IllegalArgumentException("Formula is expected to be a literal: " + f);
		if (!f.isGround())
			throw new IllegalArgumentException("Formula is expected to be ground: " + f);

		DefeasibleLogicProgram groundDelp = delp.ground();
		// int inferenceSteps = 0;
		// get all arguments for f
		boolean warrant = false;
		Set<DelpArgument> args = getArgumentsWithConclusion(groundDelp, f);
		// System.out.println(args);
		for (DelpArgument arg : args) {
			DialecticalTree dtree = new DialecticalTree(arg);
			Deque<DialecticalTree> stack = new ArrayDeque<>();
			stack.add(dtree);
			while (!stack.isEmpty()) {
				DialecticalTree dtree2 = stack.pop();
				stack.addAll(dtree2.getDefeaters(groundDelp, comparisonCriterion));
				// inferenceSteps++;
			}
			System.out.println("Dialectic tree supporting query:\n " + dtree);
			if (dtree.getMarking().equals(DialecticalTree.Mark.UNDEFEATED)) {
				warrant = true;
				break;
			}

		}
		// get all arguments for ~f (if f is not already warranted)
		boolean comp_warrant = false;
		if (!warrant) {
			args = getArgumentsWithConclusion(groundDelp, (FolFormula) f.complement());
			// System.out.println(args);
			for (DelpArgument arg : args) {
				DialecticalTree dtree = new DialecticalTree(arg);
				Deque<DialecticalTree> stack = new ArrayDeque<>();
				stack.add(dtree);
				while (!stack.isEmpty()) {
					DialecticalTree dtree2 = stack.pop();
					stack.addAll(dtree2.getDefeaters(groundDelp, comparisonCriterion));
					// inferenceSteps++;
				}
				System.out.println("Dialectic tree supporting the complement of the query:\n " + dtree);
				if (dtree.getMarking().equals(DialecticalTree.Mark.UNDEFEATED)) {
					comp_warrant = true;
					break;
				}
				// inferenceSteps++;
			}
		}
		if (warrant) {
			// System.out.println("Inference Steps: " + inferenceSteps);
			return Type.YES;
		} else if (comp_warrant) {
			// System.out.println("Inference Steps: " + inferenceSteps);
			return Type.NO;
		} else {
			// System.out.println("Inference Steps: " + inferenceSteps);
			return Type.UNDECIDED;
		}
	}

	public boolean isInstalled() {
		return true;
	}
}
