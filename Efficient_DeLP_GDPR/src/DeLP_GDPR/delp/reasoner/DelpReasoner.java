package DeLP_GDPR.delp.reasoner;

import org.tweetyproject.commons.Reasoner;

import org.tweetyproject.commons.Formula;
import org.tweetyproject.commons.ParserException;

import DeLP_GDPR.commons.util.rules.Derivation;
import DeLP_GDPR.commons.util.rules.Rule;
import DeLP_GDPR.delp.parser.DelpParser;
import DeLP_GDPR.delp.semantics.ComparisonCriterion;
import DeLP_GDPR.delp.semantics.DelpAnswer;
import DeLP_GDPR.delp.semantics.DialecticalTree;
import DeLP_GDPR.delp.semantics.EmptyCriterion;
import DeLP_GDPR.delp.semantics.DelpAnswer.Type;
import DeLP_GDPR.delp.syntax.DefeasibleLogicProgram;
import DeLP_GDPR.delp.syntax.DefeasibleRule;
import DeLP_GDPR.delp.syntax.DelpArgument;
import DeLP_GDPR.delp.syntax.DelpFact;
import DeLP_GDPR.delp.syntax.DelpRule;
import DeLP_GDPR.logics.commons.syntax.Constant;
import DeLP_GDPR.logics.commons.syntax.interfaces.Term;
import DeLP_GDPR.logics.fol.syntax.FolFormula;

import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;
import java.util.Scanner;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This reasoner performs default dialectical reasoning on some given DeLP.
 *
 */
public class DelpReasoner implements Reasoner<DelpAnswer.Type, DefeasibleLogicProgram, FolFormula> {
	private ComparisonCriterion comparisonCriterion = new EmptyCriterion();
	// static HashMap<Formula, Set<Derivation<DelpRule>>> derivations_hashmap = new
	// HashMap<Formula, Set<Derivation<DelpRule>>>();

	// Counter for the number of trees
	private int treeCount = 0;
	// Counter for the number of trees

	// Variable to track the total depth
	private int totalDepth = 0;

	public DelpReasoner(ComparisonCriterion comparisonCriterion) {
		this.comparisonCriterion = comparisonCriterion;
	}

	public ComparisonCriterion getComparisonCriterion() {
		return comparisonCriterion;
	}

	public Set<DelpArgument> getWarrants(DefeasibleLogicProgram delp) {
		DefeasibleLogicProgram groundDelp = delp.ground();
		Set<DelpArgument> all_arguments = groundDelp.ground().getArguments();
		return all_arguments.stream().filter(argument -> isWarrant(groundDelp, argument)).collect(Collectors.toSet());
	}

	private boolean isWarrant(DefeasibleLogicProgram groundDelp, DelpArgument argument) {
		DialecticalTree dtree = new DialecticalTree(argument);
		Deque<DialecticalTree> stack = new ArrayDeque<>();
		stack.add(dtree);
		while (!stack.isEmpty()) {
			DialecticalTree dtree2 = stack.pop();
			// logger.log(Level.INFO, "Marking of node: {0}", currentTree.getMarking());
			stack.addAll(dtree2.getDefeaters(groundDelp, comparisonCriterion));
		}
		return dtree.getMarking().equals(DialecticalTree.Mark.UNDEFEATED);
	}

	public static Set<DelpArgument> getArgumentsWithConclusion(DefeasibleLogicProgram delp, FolFormula f) {
		Collection<DelpRule> allRules = new HashSet<DelpRule>();
		allRules.addAll(delp);
		Set<Derivation<DelpRule>> allDerivations;
		allDerivations = Derivation.allDerivations(allRules, f);
		Set<DelpArgument> arguments = new HashSet<>();

		for (Derivation<DelpRule> derivation : allDerivations) {
			Set<DefeasibleRule> rules = derivation.stream().filter(rule -> rule instanceof DefeasibleRule)
					.map(rule -> (DefeasibleRule) rule).collect(Collectors.toSet());
			if (delp.isConsistent(rules))
				arguments.add(new DelpArgument(rules, (FolFormula) derivation.getConclusion()));
		}

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

	@Override
	public DelpAnswer.Type query(DefeasibleLogicProgram delp, FolFormula queryFormula) {
		ArrayList<String> factList = new ArrayList<String>();
		DelpParser parser = new DelpParser();
		// Ground the program and prepare for evaluation
		DefeasibleLogicProgram groundedDelp = delp.ground();
		Set<DelpArgument> conflictingArguments = new HashSet<>();

		// Original evaluation without considering priorities
		boolean warrant = evaluateArguments(groundedDelp, getArgumentsWithConclusion(groundedDelp, queryFormula),
				conflictingArguments);
		boolean compWarrant = evaluateArguments(groundedDelp,
				getArgumentsWithConclusion(groundedDelp, (FolFormula) queryFormula.complement()), conflictingArguments);

		if (!warrant && !compWarrant && !conflictingArguments.isEmpty()) {
			printConflictingArguments(conflictingArguments);
			HashMap<DelpArgument, Integer> priorities = askForPriorities(conflictingArguments);
			Set<DelpArgument> highPriorityArguments = filterByHighestPriority(priorities);
			// Assuming only one highest priority argument is considered
			DelpArgument highestPriorityArgument = highPriorityArguments.iterator().next();
			// Correctly pass the highest priority argument and the priorities map to
			// reevaluateWithPriority
			reevaluateWithPriority(groundedDelp, highestPriorityArgument, queryFormula, priorities);
		}
		if (!warrant && !compWarrant && conflictingArguments.isEmpty()) {
			Set<String> missingFacts = identifyMissingFacts(groundedDelp, queryFormula);
			if (!missingFacts.isEmpty()) {
				System.out.println("Possible missing facts identified:");
				AtomicInteger counter = new AtomicInteger(1);
				missingFacts.forEach(fact -> System.out.println(counter.getAndIncrement() + ". " + fact));
				System.out.println("Enter the number for the fact you wish to add or type 'done' to finish:");
				Scanner scanner = new Scanner(System.in);
				String userInput;
				while (!(userInput = scanner.nextLine().trim()).equalsIgnoreCase("done")) {
					try {
						int factIndex = Integer.parseInt(userInput) - 1;
						if (factIndex >= 0 && factIndex < missingFacts.size()) {
							String selectedFact = new ArrayList<>(missingFacts).get(factIndex);
							factList.add(selectedFact);
							// logic to add fact here
							System.out.println("Fact added: " + selectedFact);
						} else {
							System.out.println("Invalid fact number. Please try again.");
						}
					} catch (NumberFormatException e) {
						System.out.println("Invalid input. Please enter a number or type 'done'.");
					}
					System.out.println("Enter another fact number or type 'done' to finish:");
				}
				String delpString = delp.toString();
				delpString = delpString.replace("!", "~");
				for (String fact : factList) {
					delpString += fact + ".\n";
				}
				try {
					delp = parser.parseBeliefBase(delpString);
				} catch (ParserException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return reevaluateLogic(delp, queryFormula);
			} else {
				System.out.println("No missing facts identified.");
			}
		}

		// Decision logic
		if (warrant && !compWarrant) {
			return DelpAnswer.Type.YES;
		} else if (!warrant && compWarrant) {
			return DelpAnswer.Type.NO;
		} else {
			return DelpAnswer.Type.UNDECIDED;
		}
	}

	// Re-evaluate logic based on updated knowledge base
	private DelpAnswer.Type reevaluateLogic(DefeasibleLogicProgram delp, FolFormula queryFormula) {
		// Ground the program and prepare for evaluation
		DefeasibleLogicProgram groundedDelp = delp.ground();
		Set<DelpArgument> conflictingArguments = new HashSet<>();
		// Original evaluation without considering priorities
		boolean warrant = evaluateArguments(groundedDelp, getArgumentsWithConclusion(groundedDelp, queryFormula),
				conflictingArguments);
		boolean compWarrant = evaluateArguments(groundedDelp,
				getArgumentsWithConclusion(groundedDelp, (FolFormula) queryFormula.complement()), conflictingArguments);

		if (!warrant && !compWarrant && !conflictingArguments.isEmpty()) {
			printConflictingArguments(conflictingArguments);
			HashMap<DelpArgument, Integer> priorities = askForPriorities(conflictingArguments);
			Set<DelpArgument> highPriorityArguments = filterByHighestPriority(priorities);
			// Assuming only one highest priority argument is considered
			DelpArgument highestPriorityArgument = highPriorityArguments.iterator().next();
			// Correctly pass the highest priority argument and the priorities map to
			// reevaluateWithPriority
			reevaluateWithPriority(groundedDelp, highestPriorityArgument, queryFormula, priorities);
		}

		// Handle the results of re-evaluation
		if (warrant && !compWarrant) {
			System.out.println("Query result: YES");
			return DelpAnswer.Type.YES;
		} else if (!warrant && compWarrant) {
			System.out.println("Query result: NO");
			return DelpAnswer.Type.NO;
		} else {
			System.out.println("Query result: UNDECIDED");
			return DelpAnswer.Type.UNDECIDED;
		}
	}

	private Set<DelpArgument> filterByHighestPriority(HashMap<DelpArgument, Integer> priorities) {
		// Find the minimum priority value
		int minPriority = Collections.min(priorities.values());
		System.out.println("Minimum Priority Value: " + minPriority); // Log the minimum priority value

		// Filter arguments to include only those with the highest priority
		Set<DelpArgument> highPriorityArguments = priorities.entrySet().stream()
				.filter(entry -> entry.getValue() == minPriority)
				.peek(entry -> System.out
						.println("Filtering Argument: " + entry.getKey() + " with Priority: " + entry.getValue())) // Log
																													// each
																													// argument
																													// being
																													// considered
				.map(Map.Entry::getKey).collect(Collectors.toSet());

		// Log the filtered arguments for verification
		System.out.println("Filtered High Priority Arguments: ");
		highPriorityArguments.forEach(arg -> System.out.println(arg));

		return highPriorityArguments;
	}

	private boolean reevaluateWithPriority(DefeasibleLogicProgram delp, DelpArgument highestPriorityArgument,
			FolFormula formula, HashMap<DelpArgument, Integer> priorities) {
		System.out.println("Reevaluating with the highest priority argument for formula: " + formula);

		// Directly check if the argument supports or opposes the query
		boolean directlySupports = highestPriorityArgument.getConclusion().equals(formula);
		boolean directlyOpposes = highestPriorityArgument.getConclusion().equals(formula.complement());

		// Log the direct relation of the argument
		if (directlySupports) {
			System.out.println("Result: YES");
		} else if (directlyOpposes) {
			System.out.println("Result: NO");
		}

		// System.out.println("The reevaluation with the highest priority argument
		// remains inconclusive.");
		return false;
	}

	private HashMap<DelpArgument, Integer> askForPriorities(Set<DelpArgument> conflictingArguments) {
		HashMap<DelpArgument, Integer> priorities = new HashMap<>();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Assign priorities to the following arguments (lower number means higher priority):");

		int counter = 1;
		for (DelpArgument argument : conflictingArguments) {
			System.out.println("Argument " + counter + ": " + argument);
			System.out.print("Priority: ");
			int priority = scanner.nextInt();
			priorities.put(argument, priority);
			counter++;
		}

		scanner.close(); // Be cautious about closing the scanner.
		return priorities;
	}

	private void printConflictingArguments(Set<DelpArgument> conflictingArguments) {
		System.out.println("Conflicting Arguments:");
		if (conflictingArguments.isEmpty()) {
			System.out.println("No conflicting arguments were found.");
		} else {
			int argumentCounter = 1; // Initialize the counter for numbering arguments
			for (DelpArgument arg : conflictingArguments) {
				System.out.println("Argument " + argumentCounter + ": "); // Use the counter in the output
				// System.out.println("Support (Set of Defeasible Rules):");
				arg.getSupport().forEach(rule -> {
					System.out.println("\tRule: " + rule);
					// If DefeasibleRule class has a specific toString implementation, it will be
					// used here.
				});
				System.out.println("Conclusion: " + arg.getConclusion());

				System.out.println("---");
				argumentCounter++; // Increment the counter for the next argument
			}
		}
	}

	private boolean evaluateArguments(DefeasibleLogicProgram delp, Set<DelpArgument> arguments,
			Set<DelpArgument> conflictingArguments) {
		boolean foundSupport = false;
		for (DelpArgument argument : arguments) {
			DialecticalTree tree = new DialecticalTree(argument);
			if (evaluateTree(tree, delp, conflictingArguments)) {
				foundSupport = true; // Argument supports the query
			}
		}
		return foundSupport;
	}

	private boolean evaluateTree(DialecticalTree tree, DefeasibleLogicProgram delp,
			Set<DelpArgument> conflictingArguments) {
		// the delp is the grounded version

		DefeasibleLogicProgram groundedDelp = delp.ground();
		Deque<DialecticalTree> stack = new ArrayDeque<>();
		stack.push(tree);

		// int nodeCounter = 0; // Initialize the counter

		while (!stack.isEmpty()) {
			DialecticalTree currentTree = stack.pop();
			// nodeCounter++; // Increment the counter as we're about to process a new node
			System.out.println("Processing node: " + currentTree.getArgument().toString()); // Use the counter in the
																							// output
			Set<DialecticalTree> defeaters = currentTree.getDefeaters(groundedDelp, comparisonCriterion);
			for (DialecticalTree defeater : defeaters) {
				System.out.println("Defeater found: " + defeater.getArgument().toString());
				if (defeater.getMarking() == DialecticalTree.Mark.UNDEFEATED) {
					conflictingArguments.add(defeater.getArgument());
					// System.out.println("argument: " + defeater.getArgument().toString());
				} else {
					stack.push(defeater);
				}
			}
		}
		return conflictingArguments.isEmpty();

	}

	private Set<String> identifyMissingFacts(DefeasibleLogicProgram delp, FolFormula queryFormula) {
		Set<FolFormula> allFacts = new HashSet<>();
		Set<String> missingFacts = new LinkedHashSet<>(); // Preserve insertion order and avoid duplicates
		populateAllFacts(delp, allFacts);

		// Logic to identify missing facts...
		Set<FolFormula> bodyLiterals = extractBodyLiterals(delp, queryFormula);
		bodyLiterals.forEach(literal -> {
			if (!containsFactOrComplement(allFacts, literal)) {
				missingFacts.add(literal.toString());
			}
		});

		// Instead of printing, just return the set of missing facts.
		return missingFacts;
	}

	private Set<String> filterOutFactsWithUniqueLiterals(Set<String> missingFacts, Set<FolFormula> allFacts) {
		// Convert allFacts to a more searchable structure
		Map<List<String>, Set<String>> allFactsMap = new HashMap<>();
		allFacts.forEach(fact -> {
			String[] parts = fact.toString().split("\\(", 2);
			String predicate = parts[0];
			String arguments = parts.length > 1 ? parts[1] : "";
			List<String> argsList = Arrays.asList(arguments.split(","));
			allFactsMap.computeIfAbsent(argsList, k -> new HashSet<>()).add(predicate);
		});

		// Filter missing facts based on unique predicate with same arguments
		return missingFacts.stream().filter(missingFact -> {
			String[] parts = missingFact.split("\\(", 2);
			String missingPredicate = parts[0].replaceAll("!", ""); // Remove negation for comparison
			String arguments = parts.length > 1 ? parts[1] : "";
			List<String> argsList = Arrays.asList(arguments.split(","));

			Set<String> predicatesWithSameArgs = allFactsMap.getOrDefault(argsList, Collections.emptySet());
			return !predicatesWithSameArgs.contains(missingPredicate) && !predicatesWithSameArgs.isEmpty();
		}).collect(Collectors.toSet());
	}

	private boolean containsFactOrComplement(Set<FolFormula> allFacts, FolFormula fact) {
		// Check if the set contains the fact or its complement
		String factStr = fact.toString();
		String complementStr = getComplementLiteral(factStr);
		return allFacts.stream().anyMatch(f -> f.toString().equals(factStr) || f.toString().equals(complementStr));
	}

	private String getComplementLiteral(String literalStr) {
		// Adjust this method based on how your logical negation is represented
		if (literalStr.startsWith("!")) {
			return literalStr.substring(1);
		} else {
			return "!" + literalStr;
		}
	}

	private Set<FolFormula> extractBodyLiterals(DefeasibleLogicProgram delp) {
		Set<FolFormula> bodyLiterals = new HashSet<>();
		delp.forEach(rule -> bodyLiterals.addAll(rule.getPremise()));
		return bodyLiterals;
	}

	private boolean isRelevant(FolFormula conclusion, FolFormula queryFormula) {
		// Direct equality check
		boolean isEqual = conclusion.toString().equals(queryFormula.toString());

		// Check for complement
		String conclusionStr = conclusion.toString();
		String queryStr = queryFormula.toString();
		boolean isComplement = (conclusionStr.startsWith("!") && conclusionStr.substring(1).equals(queryStr))
				|| (queryStr.startsWith("!") && queryStr.substring(1).equals(conclusionStr));

		return isEqual || isComplement;
	}

	private Set<FolFormula> extractBodyLiterals(DefeasibleLogicProgram delp, FolFormula queryFormula) {
		Set<FolFormula> bodyLiterals = new HashSet<>();
		delp.forEach(rule -> {
			if (rule.getConclusion().toString().equals(queryFormula.toString())
					|| isRelevant(rule.getConclusion(), queryFormula)) {
				bodyLiterals.addAll(rule.getPremise());
			}
		});
		return bodyLiterals;
	}

	private void populateAllFacts(DefeasibleLogicProgram delp, Set<FolFormula> allFacts) {
		// Directly iterate over the elements of the DefeasibleLogicProgram
		delp.forEach(rule -> {
			// Check if the rule qualifies as a fact based on its body being empty
			if (rule.isFact()) {
				allFacts.add(rule.getConclusion());
			}
		});
	}

	public boolean isInstalled() {
		return true;
	}

	public int getTreeCount() {
		return treeCount;
	}
}