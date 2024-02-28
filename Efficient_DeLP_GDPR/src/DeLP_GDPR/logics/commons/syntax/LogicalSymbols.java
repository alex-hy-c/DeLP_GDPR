package DeLP_GDPR.logics.commons.syntax;

/**
 * This class provides some String constants for logical symbols and allows to
 * modify them at runtime. This functionality is currently used to switch between
 * different constants for classical negation and contradiction.
 */
public class LogicalSymbols {
	private static String classical_negation = "!";
	private static String contradiction = "-";
	
	public static void setClassicalNegationSymbol(String sym) {
		LogicalSymbols.classical_negation = sym;
	}
	
	public static void setContradictionSymbol(String sym) {
		LogicalSymbols.contradiction = sym;
	}
		
	public static String CLASSICAL_NEGATION() {
		return classical_negation;
	}
	public static String DISJUNCTION() {
		return "||";
	}
	public static String CONJUNCTION() {
		return "&&";
	}
	public static String FORALLQUANTIFIER() {
		return "forall";
	}
	public static String EXISTSQUANTIFIER() {
		return "exists";
	}
	public static String IMPLICATION() {
		return "=>";
	}
	public static String EQUIVALENCE() {
		return "<=>";
	}
	public static String TAUTOLOGY() {
		return "+";
	}
	public static String CONTRADICTION() {
		return contradiction;
	}
	public static String PARENTHESES_LEFT() {
		return "(";
	}
	public static String PARENTHESES_RIGHT() {
		return ")";
	}
	public static String NECESSITY() {
		return "[]";
	}
	public static String POSSIBILITY() {
		return "<>";
	}
	public static String EQUALITY() {
		return "==";
	}
	public static String INEQUALITY() {
		return "/==";
	}
	public static String EXCLUSIVEDISJUNCTION() {
		return "^^";
	}
}