package DeLP_GDPR.dung.ldo.syntax;



import java.util.Set;

public class LdoGraphBoxModality extends AbstractGraphLdoModality {

	/**
	 * 
	 * @param innerFormula inner Formula
	 * @param lowerReferenceArguments lower Reference Arguments
	 * @param upperReferenceArguments upper Reference Arguments
	 */
	public LdoGraphBoxModality(LdoFormula innerFormula,	Set<LdoArgument> lowerReferenceArguments, Set<LdoArgument> upperReferenceArguments) {
		super(innerFormula, lowerReferenceArguments,upperReferenceArguments);
	}

	@Override
	public LdoFormula clone() {
		return new LdoGraphBoxModality(this.getInnerFormula(),this.getLowerReferenceArguments(),this.getUpperReferenceArguments());
	}

	public String toString(){
		return "[" + this.getLowerReferenceArguments() + "," + this.getUpperReferenceArguments() + "](" + this.getInnerFormula() + ")";
	}
}
