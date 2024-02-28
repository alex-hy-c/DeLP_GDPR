package DeLP_GDPR.delp.syntax;

import DeLP_GDPR.logics.commons.syntax.RelationalFormula;
import DeLP_GDPR.logics.commons.syntax.interfaces.Term;
import DeLP_GDPR.logics.fol.syntax.FolFormula;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class models a strict rule in defeasible logic programming.
 *
 */
public class StrictRule extends DelpRule {

	/**
	 * Default constructor; initializes head and body of the strict rule
	 * @param head a literal
	 * @param body a set of literals
	 */
	public StrictRule(FolFormula head, Set<FolFormula> body){
		super(head,body);
	}

	@Override
	String getSymbol() {
		return " <- ";
	}

    @Override
    public RelationalFormula substitute(Term<?> v, Term<?> t) throws IllegalArgumentException {
        return new StrictRule(
                getConclusion().substitute(v,t),
                body.stream()
                        .map(f -> f.substitute(v,t))
                        .collect(Collectors.toSet()));
    }

    @Override
    public RelationalFormula clone() {
		throw new UnsupportedOperationException("IMPLEMENT ME");
	}
}
