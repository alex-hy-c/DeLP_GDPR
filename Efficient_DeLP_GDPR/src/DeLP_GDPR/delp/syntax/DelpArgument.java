package DeLP_GDPR.delp.syntax;


import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.tweetyproject.commons.*;

import DeLP_GDPR.logics.fol.syntax.*;

/**
 * This class models a DeLP argument which comprises of a set of defeasible rules (the support) and a literal (the conclusion).
 */
public class DelpArgument implements Formula {

	/**
	 * The support of this argument
	 */
	private final Set<DefeasibleRule> support;

	/**
	 * The conclusion of this argument (must be a literal)
	 */
	private final FolFormula conclusion;
	private int depth;

	/**
	 * constructor; initializes the conclusion of this argument with the given literal
	 * @param conclusion a literal
	 */
	public DelpArgument(FolFormula conclusion, int depth){
		this(null,conclusion);
		this.depth = depth;
	}
	
	public int getDepth() {
        return depth;
    }

	/**
	 * constructor; initializes this argument with the given parameters
	 * @param support a set of defeasible rules
	 * @param conclusion a literal (must not be NULL)
	 */
	public DelpArgument(Set<DefeasibleRule> support, FolFormula conclusion){
        if (conclusion == null)
            throw new IllegalArgumentException("Cannot instantiate argument with NULL conclusion");
		if(!conclusion.isLiteral())
			throw new IllegalArgumentException("The conclusion of an argument must be a literal.");
        if (support == null)
            this.support = Collections.emptySet();
        else
            this.support = support;
		this.conclusion = conclusion;
	}

	/**
	 * Checks whether this argument is a subargument of the given argument
	 * @param argument a DeLP argument
	 * @return <code>true</code> iff this argument is a subargument of the given argument
	 */
	public boolean isSubargumentOf(DelpArgument argument){
		return argument.getSupport().containsAll(support);
	}

	/**
	 * Checks whether this argument is a strong subargument of the given argument, i.e., if the
	 * support of this argument is a strict subset of the support of the given argument
	 * @param argument a DeLP argument
	 * @return <code>true</code> iff this argument is a strong subargument of the given argument
	 */
	public boolean isStrongSubargumentOf(DelpArgument argument){
		if(!isSubargumentOf(argument)) return false;
		return argument.getSupport().size() > support.size();
	}

	/**
	 * Computes the set of literals that disagree with the conclusion of a subargument of this argument
	 * @param delp a defeasible logic program
	 * @return  the set of literals that disagree with the conclusion of a subargument of this argument
	 */
	public Set<FolFormula> getAttackOpportunities(DefeasibleLogicProgram delp){
		Set<FolFormula> literals = support.stream()
				.map(DelpRule::getConclusion)
				.collect(Collectors.toSet());

		Set<FolFormula> strictClosure = delp.getStrictClosure();
		Set<FolFormula> strictClosureWithAP = delp.getStrictClosure(literals);
		strictClosureWithAP.removeAll(strictClosure);
		literals.addAll(strictClosureWithAP);

        // compute attack opportunities as complements of literals:
		return literals.stream()
				.map(f -> (FolFormula) f.complement())
				.collect(Collectors.toSet());
	}

	/**
	 * Computes the disagreement subargument of this argument for the given literal
	 * @param lit a literal
	 * @param delp a defeasible logic program
	 * @return the disagreement subargument for <code>lit</code> or <code>null</code> if
	 * 	there is no disagreement subargument
	 */
	public DelpArgument getDisagreementSubargument(FolFormula lit, DefeasibleLogicProgram delp){
        for (DelpArgument argument : delp.getArguments()) {
            if (argument.isSubargumentOf(this)) {
                if (delp.disagree(Stream
                        .of(
                                lit,
                                argument.getConclusion())
                        .collect(Collectors.toSet())))
                    return argument;
            }
        }
		return null;
	}

	// Misc Methods

	/**
	 * Returns all defeasible rules of the support of this argument with the given literal as head
	 * @param l a literal
	 * @return a set defeasible rules
	 */
	public Set<DelpRule> getRulesWithHead(FolFormula l){
		return support.stream()
                .filter(rule -> rule.getConclusion().equals(l))
                .collect(Collectors.toSet());
	}

	/**
	 * returns the conclusion of this argument
	 * @return the conclusion of this argument
	 */
	public FolFormula getConclusion() {
		return conclusion;
	}

	/**
	 * returns the support of this argument
	 * @return the support of this argument
	 */
	public Set<DefeasibleRule> getSupport() {
		return support;
	}

	public String toString(){
		return "<{"
				+ support.stream().map(Object::toString).collect(Collectors.joining(","))
				+ "},"
				+ conclusion
				+ ">";
	}

    /**
     * Always null.
     * @return <code>null</code>
     */
	@Override
	public Signature getSignature() {
		return null;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DelpArgument)) return false;

        DelpArgument that = (DelpArgument) o;

        if (!support.equals(that.support)) return false;
        return conclusion.equals(that.conclusion);

    }

    @Override
    public int hashCode() {
        int result = support.hashCode();
        result = 31 * result + conclusion.hashCode();
        return result;
    }
}