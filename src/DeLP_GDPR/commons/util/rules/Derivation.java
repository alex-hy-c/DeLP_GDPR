package DeLP_GDPR.commons.util.rules;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.tweetyproject.commons.Formula;
import org.tweetyproject.commons.util.Triple;

/**
 * This class models a derivation, i.e. a minimal (with respect to set
 * inclusion) sequence of rules [R1, ... ,Rn] such that for any Ri and for any p
 * in the premise of Ri there is an Rj with j &gt; i and the conclusion of Rj
 * equals p.
 * 
 * @param <T> the specific rule class
 */
public class Derivation<T extends Rule<?, ?>> extends ArrayList<T> {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new derivation with the given sequence of rules.
	 * 
	 * @param rules a sequence of rules.
	 */
	public Derivation(List<T> rules) {
		super(rules);
	}

	/**
	 * Returns the conclusion of this derivation.
	 * 
	 * @return the conclusion of this derivation.
	 */
	public Formula getConclusion() {
		RuleSet<T> ruleSet = new RuleSet<T>(this);
		Set<Formula> conclusions = ruleSet.getConclusions();
		Set<Formula> premises = ruleSet.getPremises();
		conclusions.removeAll(premises);
		return conclusions.iterator().next();
	}

	/**
	 * Returns the set of all possible derivations from the set of rules.
	 * 
	 * @param rules a set of rules
	 * @return the set of all possible derivations
	 * @param <S> the type of rules
	 */
	public static <S extends Rule<?, ?>> Set<Derivation<S>> allDerivations(Collection<? extends S> rules) {
		RuleSet<S> theRules = new RuleSet<S>(rules);
		Set<Derivation<S>> allDerivations = new HashSet<Derivation<S>>();
		for (Formula f : theRules.getConclusions())
			allDerivations.addAll(Derivation.allDerivations(rules, f));
		return allDerivations;
	}

	/**
	 * Returns the set of all possible derivations with the given conclusion from
	 * the set of rules.
	 * 
	 * @param <S>        the type of rules
	 * @param rules      a set of rules
	 * @param conclusion the conclusion
	 * @return the set of all possible derivations with the given conclusion
	 */
	public static <S extends Rule<?, ?>> Set<Derivation<S>> allDerivations(Collection<? extends S> rules,
			Formula conclusion) {
		// each element (A,B,C) of the stack below describes a (partial) derivation with
		// A - being the current derivation (a list of rules)
		// B - being the set of all formulas that have to be proven
		// C - being the set of rules that can be used to construct the rest of the
		// derivation
		///System.out.println("---allDerivationsCalled---\n");
		Stack<Triple<List<S>, Set<Formula>, RuleSet<S>>> stack = new Stack<Triple<List<S>, Set<Formula>, RuleSet<S>>>();
		Triple<List<S>, Set<Formula>, RuleSet<S>> initial = new Triple<List<S>, Set<Formula>, RuleSet<S>>();
		initial.setFirst(new ArrayList<S>());
		initial.getFirst();
		initial.setSecond(new HashSet<Formula>());
		initial.getSecond().add(conclusion);
		initial.setThird(new RuleSet<S>(rules));
		stack.add(initial);
		Set<Derivation<S>> derivations = new HashSet<Derivation<S>>();
		int whilecount = 0;
		int derivationsNum = 0;
		while (!stack.isEmpty()) {
			whilecount++;
			//System.out.println(whilecount);
			Triple<List<S>, Set<Formula>, RuleSet<S>> derivation = stack.pop();
//			System.out.println("Current derivation: "+derivation.getFirst());
//			System.out.println("To be proven: "+ derivation.getSecond());
//			System.out.println("Can be used: "+derivation.getThird()+"\n");
			if (derivation.getSecond().isEmpty()) {//If no more needs to be proved, add current derivation as a derivation
				derivationsNum++;
				derivations.add(new Derivation<S>(derivation.getFirst()));
//				System.out.println(derivation.getFirst());
//				System.out.println("derivation number " + derivationsNum + " added.\n\n");
			} 
			else {
				for (Formula f : derivation.getSecond()) {
					//System.out.println("\n\t"+f+"\n");
					for (S r : derivation.getThird().getRulesWithConclusion(f)) {
						//System.out.println("for2");
						Triple<List<S>, Set<Formula>, RuleSet<S>> newDerivation = new Triple<List<S>, Set<Formula>, RuleSet<S>>();
						newDerivation.setFirst(new ArrayList<S>(derivation.getFirst()));
						newDerivation.getFirst().add(r);
						newDerivation.setSecond(new HashSet<Formula>(derivation.getSecond()));
						newDerivation.getSecond().remove(f);
						newDerivation.getSecond().addAll(r.getPremise());
						newDerivation.setThird(new RuleSet<S>(derivation.getThird()));
						newDerivation.getThird().removeAll(derivation.getThird().getRulesWithConclusion(f));
//						System.out.println("\t\tNEW: Current derivation: " + newDerivation.getFirst());
//						System.out.println("\t\tNEW: To be proven: "+ newDerivation.getSecond());
//						System.out.println("\t\tNEW: Can be used: " + newDerivation.getThird()+"\n");
						// Check whether we added an element to the second
						// component that is already derived from a rule in the first component.
						// In that case we have a cycle and the derivation cannot be completed.
						Set<Formula> conclusions = new HashSet<Formula>();
	                    conclusions.addAll(newDerivation.getSecond());
	                    boolean noder = false;
	                    for (S s : newDerivation.getFirst()) {
	                        if (!conclusions.add(s.getConclusion())) {
	                            noder = true;
	                            break;
	                        }
	                    }
	                    if (!noder)
	                        stack.add(newDerivation);
					}
				}
			}
		}
		//System.out.println("Conclusion: "+conclusion+"\nDerivations: "+derivations+"\n\n--------\n\n");
		return derivations;
	}

	/**
	 * Checks whether this derivation is founded, i.e. whether every formula
	 * appearing in the premise of a rule is also the conclusion of a previous rule.
	 * 
	 * @return "true" if this derivation is founded.
	 */
	public boolean isFounded() {
		Set<Formula> toProve = new HashSet<Formula>();
		Iterator<T> rules = this.iterator();
		toProve.addAll(rules.next().getPremise());
		while (rules.hasNext()) {
			T rule = rules.next();
			toProve.remove(rule.getConclusion());
			toProve.addAll(rule.getPremise());
		}
		return toProve.isEmpty();
	}

	/**
	 * Checks whether this derivation is minimal with respect to set inclusion. This
	 * is equivalent to checking whether every conclusion besides the first is used
	 * in a premise and no conclusion appear twice.
	 * 
	 * @return "true" if this derivation is minimal.
	 */
	public boolean isMinimal() {
		RuleSet<T> ruleSet = new RuleSet<T>(this);
		for (Formula f : ruleSet.getConclusions())
			if (ruleSet.getRulesWithConclusion(f).size() > 1)
				return false;
		Set<Formula> conclusions = ruleSet.getConclusions();
		Set<Formula> premises = ruleSet.getPremises();
		conclusions.removeAll(premises);
		return conclusions.size() == 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractList#hashCode()
	 */
	@Override
	public int hashCode() {
		// for comparing a derivation is treated as a set
		Set<T> thisSet = new HashSet<T>(this);
		return thisSet.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractList#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		// for comparing a derivation is treated as a set
		Set<T> thisSet = new HashSet<T>(this);
		return thisSet.equals(new HashSet<T>((Derivation<T>) obj));
	}

}