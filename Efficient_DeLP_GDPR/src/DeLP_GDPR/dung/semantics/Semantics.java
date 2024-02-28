package DeLP_GDPR.dung.semantics;



/**
 * This enum lists all semantics.
 */
public enum Semantics {
	/** CF */
	CF ("conflict-free semantics", "CF"),
	/** ADM */
	ADM ("admissible semantics", "ADM"),
	/** WAD */
	WAD ("weakly-admissible semantics", "WAD"),
	/** CO */
	CO ("complete semantics", "CO"),
	/** GR */
	GR ("grounded semantics", "GR"),
	/** PR */
	PR ("preferred semantics", "PR"),
	/** ST */
	ST ("stable semantics", "ST"),
	/** STG */
	STG ("stage semantics", "STG"),
	/** STG2 */
	STG2 ("stage2 semantics", "STG2"),
	/** SST */
	SST ("semi-stable semantics", "SST"),
	/** ID */
	ID ("ideal semantics", "ID"),
	/** EA */
	EA ("eager semantics", "EA"),
	/** CF2 */
	CF2 ("CF2 semantics", "CF2"),
	/** SCF2 */
	SCF2 ("SCF2 semantics", "SCF2"),
	/** N */
	N ("Naive semantics", "N"),
	/** diverse */
	diverse ("diverse semantics", "div");
	/**
	 * all semantics
	 */
	public static final Semantics 
		/** GROUNDED SEMANTICS*/
		GROUNDED_SEMANTICS = GR,
		/** STABLE_SEMANTICS */	
		STABLE_SEMANTICS = ST,
		/** PREFERRED_SEMANTICS */
		PREFERRED_SEMANTICS = PR,
		/** COMPLETE_SEMANTICS */
		COMPLETE_SEMANTICS = CO,
		/** ADMISSIBLE_SEMANTICS */
		ADMISSIBLE_SEMANTICS = ADM,
		/** WEAKLY_ADMISSIBLE_SEMANTICS */
		WEAKLY_ADMISSIBLE_SEMANTICS = WAD,
		/** CONFLICTFREE_SEMANTICS */
		CONFLICTFREE_SEMANTICS = CF,
		/** SEMISTABLE_SEMANTICS */
		SEMISTABLE_SEMANTICS = SST,
		/** IDEAL_SEMANTICS */
		IDEAL_SEMANTICS = ID,
		/** EAGER_SEMANTICS */
		EAGER_SEMANTICS = EA,
		/** STAGE_SEMANTICS */
		STAGE_SEMANTICS = STG,
		/** STAGE2_SEMANTICS */
		STAGE2_SEMANTICS = STG2,
		/** CF2_SEMANTICS */
		CF2_SEMANTICS = CF2,
		/** SCF2_SEMANTICS */
		SCF2_SEMANTICS = SCF2,
		/** NAIVE_SEMANTICS */
		NAIVE_SEMANTICS = N;
		
	/** The description of the semantics. */
	private String description;
	/** The abbreviation of the semantics. */
	private String abbreviation;
	
	/**
	 * Creates a new semantics.
	 * @param description some description
	 * @param abbreviation an abbreviation
	 */
	private Semantics(String description, String abbreviation){
		this.description = description;
		this.abbreviation = abbreviation;
	}
	
	/**
	 * Returns the description of the semantics.
	 * @return the description of the semantics.
	 */
	public String description(){
		return this.description;
	}
	
	/**
	 * Returns the abbreviation of the semantics.
	 * @return the abbreviation of the semantics.
	 */
	public String abbreviation(){
		return this.abbreviation;
	}
}
