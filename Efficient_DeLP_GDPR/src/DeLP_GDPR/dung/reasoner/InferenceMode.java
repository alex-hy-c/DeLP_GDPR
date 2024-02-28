package DeLP_GDPR.dung.reasoner;



/**
 * Enum constants for the two classical inference models of skeptical inference (assess
 * a formula as true iff it is contained in every model) and credulous inference
 * (assess a formula as true iff it is contained in some model).
 */
public enum InferenceMode {
	/**
	 * Skeptical inference assesses a formula as true iff it is contained in <strong>every</strong> model
	 */
	SKEPTICAL,
	/**
	 * Credulous inference assesses a formula as true iff it is contained in <strong>some</strong> model
	 */
	CREDULOUS
}
