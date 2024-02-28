package DeLP_GDPR.delp.semantics;

import DeLP_GDPR.delp.syntax.*;

/**
 * This class implements the empty criterion to compare two arguments.
 * Using this criterion no two arguments are comparable.
 *
 */
public final class EmptyCriterion extends ComparisonCriterion {

	@Override
	public Result compare(DelpArgument argument1, DelpArgument argument2, DefeasibleLogicProgram context) {
		return Result.NOT_COMPARABLE;
	}

}