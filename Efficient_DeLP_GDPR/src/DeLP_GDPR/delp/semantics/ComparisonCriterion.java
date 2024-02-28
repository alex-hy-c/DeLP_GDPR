package DeLP_GDPR.delp.semantics;

import DeLP_GDPR.delp.syntax.*;

/**
 * This class is the superclass for all comparison criteria between two arguments in defeasible logic programming.
 *
 */
public abstract class ComparisonCriterion {

    /**
     * To select a subclass and create an instance throw a static factory method.
     */
    public enum Factory { EMPTY, GEN_SPEC, PRIORITY;
        public static ComparisonCriterion create(String name) {
            switch (Factory.valueOf(name)) {
                case EMPTY:
                    return new EmptyCriterion();
                case GEN_SPEC:
                    return new GeneralizedSpecificity();
                default:
                    throw new IllegalArgumentException("Cannot create comparison criterion from "+name);
            }
        }
    }

    public enum Result { IS_BETTER, NOT_COMPARABLE, IS_WORSE, IS_EQUAL }

	/**
	 * This method returns the relation of <code>argument1</code> to <code>argument2</code>
     * given <code>context</code>.
	 * @param argument1 a DeLP argument
	 * @param argument2 a DeLP argument
	 * @param context a defeasible logic program as context
	 * @return
	 * 	<br>- Result.IS_BETTER iff <code>argument1</code> is better than <code>argument2</code>
	 *  <br>- Result.IS_WORSE iff <code>argument1</code> is worse than <code>argument2</code>
	 *  <br>- Result.IS_EQUAL iff <code>argument1</code> and <code>argument2</code> are in the same equivalence class
	 *  <br>- Result.NOT_COMPARABLE iff <code>argument1</code> and <code>argument2</code> are not comparable
	 */
    public abstract Result compare(DelpArgument argument1,
                                   DelpArgument argument2,
                                   DefeasibleLogicProgram context);
}