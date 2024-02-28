package DeLP_GDPR.delp.syntax;

import DeLP_GDPR.logics.commons.syntax.RelationalFormula;
import DeLP_GDPR.logics.commons.syntax.interfaces.Term;
import DeLP_GDPR.logics.fol.syntax.FolFormula;

import java.util.Set;

public class PriorityRule extends DelpRule {

    private DefeasibleRule higherPriorityRule;
    private DefeasibleRule lowerPriorityRule;
    private Set<FolFormula> body;

    public PriorityRule(DefeasibleRule higherPriorityRule, DefeasibleRule lowerPriorityRule, Set<FolFormula> body) {
        super(null, null);
        this.higherPriorityRule = higherPriorityRule;
        this.lowerPriorityRule = lowerPriorityRule;
        this.body = body;
    }

    public DefeasibleRule getHigherPriorityRule() {
        return higherPriorityRule;
    }

    public DefeasibleRule getLowerPriorityRule() {
        return lowerPriorityRule;
    }

    @Override
    String getSymbol() {
        return " > ";
    }

    @Override
    public RelationalFormula substitute(Term<?> v, Term<?> t) throws IllegalArgumentException {
        DefeasibleRule substitutedHigherRule = (DefeasibleRule) higherPriorityRule.substitute(v, t);
        DefeasibleRule substitutedLowerRule = (DefeasibleRule) lowerPriorityRule.substitute(v, t);
        return new PriorityRule(substitutedHigherRule, substitutedLowerRule, body);
    }

    @Override
    public RelationalFormula clone() {
        DefeasibleRule clonedHigherRule = (DefeasibleRule) higherPriorityRule.clone();
        DefeasibleRule clonedLowerRule = (DefeasibleRule) lowerPriorityRule.clone();
        return new PriorityRule(clonedHigherRule, clonedLowerRule, body);
    }

    @Override
    public String toString() {
        return String.format("%s%s%s", higherPriorityRule, getSymbol(), lowerPriorityRule);
    }

    public void setBody(Set<FolFormula> body) {
        this.body = body;
    }

    public void setHigherPriorityRule(DefeasibleRule higherPriorityRule) {
        this.higherPriorityRule = higherPriorityRule;
    }

    public void setLowerPriorityRule(DefeasibleRule lowerPriorityRule) {
        this.lowerPriorityRule = lowerPriorityRule;
    }
}