package DeLP_GDPR.logics.pl.syntax;




import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.tweetyproject.commons.util.SetTools;

import DeLP_GDPR.logics.common.LogicalSymbols;
import DeLP_GDPR.logics.pl.semantics.PossibleWorld;

/**
 * This class represents a disjunction in propositional logic.
 */
public class Disjunction extends AssociativePlFormula {

	/**
	 * Creates a new disjunction with the given inner formulas.
	 * 
	 * @param formulas a collection of formulas.
	 */
	public Disjunction(Collection<? extends PlFormula> formulas) {
		super(formulas);
	}
	/**
	 * constructor
	 * @param formulas formulas
	 */
	public Disjunction(PlFormula... formulas) {
		super(new HashSet<>());
		for (PlFormula f : formulas)
			this.add(f);
	}

	/**
	 * Creates a new (empty) disjunction.
	 */
	public Disjunction() {
		this(new HashSet<PlFormula>());
	}

	
	@Override
	public PlFormula collapseAssociativeFormulas() {
		if (this.isEmpty())
			return new Contradiction();
		if (this.size() == 1)
			return this.iterator().next().collapseAssociativeFormulas();
		Disjunction newMe = new Disjunction();
		for (PlFormula f : this) {
			PlFormula newF = f.collapseAssociativeFormulas();
			if (newF instanceof Disjunction)
				newMe.addAll((Disjunction) newF);
			else
				newMe.add(newF);
		}
		return newMe;
	}

	/**
	 * Creates a new disjunction with the two given formulae
	 * 
	 * @param first  a propositional formula.
	 * @param second a propositional formula.
	 */
	public Disjunction(PlFormula first, PlFormula second) {
		this();
		this.add(first);
		this.add(second);
	}


	@Override
	public PlFormula toNnf() {
		Disjunction d = new Disjunction();
		for (PlFormula p : this) 
			d.add(p.toNnf());
		return d;
	}

	@Override
	public PlFormula clone() {
		return new Disjunction(support.copyHelper(this));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Disjunction createEmptyFormula() {
		return new Disjunction();
	}

	@Override
	public String getOperatorSymbol() {
		return LogicalSymbols.DISJUNCTION();
	}

	@Override
	public String getEmptySymbol() {
		return LogicalSymbols.CONTRADICTION();
	}


	@Override
	public Conjunction toCnf() {
		if(this.isClause()) {
			Conjunction c = new Conjunction();
			c.add(this);
			return c;
		}
		Set<Set<PlFormula>> conjs = new HashSet<Set<PlFormula>>();
		for (PlFormula f : this)
			conjs.add(new HashSet<PlFormula>(f.toCnf()));
		Collection<PlFormula> newConjs = new HashSet<PlFormula>();
		SetTools<PlFormula> setTools = new SetTools<PlFormula>();
		for (Set<PlFormula> permut : setTools.permutations(conjs)) {
			Disjunction disj = new Disjunction();
			for (PlFormula f : permut)
				disj.addAll(((Disjunction) f));
			newConjs.add(disj);
		}
		return (Conjunction) new Conjunction(newConjs).trim();
	}

	
	public PlFormula trim() {
		Set<PlFormula> disj = new HashSet<PlFormula>();
		for (PlFormula f : this.support)
			disj.add(f.trim());
		return new Disjunction(disj);
	}

	
	@Override
	public Set<PossibleWorld> getModels(PlSignature sig) {
		Set<PossibleWorld> models = new HashSet<PossibleWorld>();
		Iterator<PlFormula> it = this.support.iterator();
		if (!it.hasNext())
			return PossibleWorld.getAllPossibleWorlds(sig);
		models.addAll(it.next().getModels(sig));
		while (it.hasNext())
			models.addAll(it.next().getModels(sig));
		return models;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see DeLP_GDPR.logics.pl.syntax.PropositionalFormula#isClause()
	 */
	@Override
	public boolean isClause() {
		for (PlFormula f : this.getFormulas())
			if (!f.isLiteral())
				return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * DeLP_GDPR.logics.pl.syntax.PropositionalFormula#replace(org.tweetyproject.
	 * logics.pl.syntax.Proposition,
	 * DeLP_GDPR.logics.pl.syntax.PropositionalFormula, int)
	 */
	public PlFormula replace(Proposition p, PlFormula f, int i) {
		int num = 0;
		Disjunction n = new Disjunction();
		for (PlFormula sub : this.support.getFormulas()) {
			if (num < i && num + sub.numberOfOccurrences(p) >= i) {
				n.add(sub.replace(p, f, i - num));
			} else
				n.add(sub.clone());
			num += sub.numberOfOccurrences(p);
		}
		return n;
	}
}