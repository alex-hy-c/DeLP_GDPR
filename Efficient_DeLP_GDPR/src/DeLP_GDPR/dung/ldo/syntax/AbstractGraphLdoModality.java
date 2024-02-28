package DeLP_GDPR.dung.ldo.syntax;



import java.util.Set;

import DeLP_GDPR.logics.pl.syntax.PlPredicate;

/**
 * Provides common functionalities for the graph-based modalities in LDO.
 */
public abstract class AbstractGraphLdoModality extends AbstractLdoModality {

	private Set<LdoArgument> upperReferenceArguments;
	private Set<LdoArgument> lowerReferenceArguments;
	
	/**
	 * 
	 * @param innerFormula Formula
	 * @param lowerReferenceArguments Arguments
	 * @param upperReferenceArguments Arguments
	 */
	public AbstractGraphLdoModality(LdoFormula innerFormula, Set<LdoArgument> lowerReferenceArguments, Set<LdoArgument> upperReferenceArguments) {
		super(innerFormula);
		this.lowerReferenceArguments = lowerReferenceArguments;
		this.upperReferenceArguments = upperReferenceArguments;
	}

	/**
	 * Returns the lower reference arguments of this modality.
	 * @return the lower reference arguments of this modality.
	 */
	public Set<LdoArgument> getLowerReferenceArguments(){
		return this.lowerReferenceArguments;
	}
	
	/**
	 * Returns the upper reference arguments of this modality.
	 * @return the upper reference arguments of this modality.
	 */
	public Set<LdoArgument> getUpperReferenceArguments(){
		return this.upperReferenceArguments;
	}
	
	@Override
	public Set<LdoArgument> getAtoms() {
		Set<LdoArgument> result = super.getAtoms();
		result.addAll(lowerReferenceArguments);
		result.addAll(upperReferenceArguments);
		return result;
	}
	
	@Override
	public Set<PlPredicate> getPredicates() {
		Set<PlPredicate> result = super.getPredicates();
		for(LdoArgument a: this.lowerReferenceArguments)
			result.add(a.getPredicate());
		for(LdoArgument a: this.upperReferenceArguments)
			result.add(a.getPredicate());
		return result;
	}
	
	@Override
	public abstract LdoFormula clone();

}
