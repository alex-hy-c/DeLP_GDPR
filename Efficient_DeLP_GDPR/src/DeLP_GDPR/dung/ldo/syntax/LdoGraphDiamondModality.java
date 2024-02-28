package DeLP_GDPR.dung.ldo.syntax;




import java.util.Set;



public class LdoGraphDiamondModality extends AbstractGraphLdoModality{
	
	
	/**
	 * 
	 * @param innerFormula inner Formula
	 * @param lowerReferenceArguments lower Reference Arguments
	 * @param upperReferenceArguments upper Reference Arguments
	 */
	public LdoGraphDiamondModality(LdoFormula innerFormula,	Set<LdoArgument> lowerReferenceArguments, Set<LdoArgument> upperReferenceArguments) {
		super(innerFormula, lowerReferenceArguments,upperReferenceArguments);
	}

	@Override
	public LdoFormula clone() {
		return new LdoGraphBoxModality(this.getInnerFormula(),this.getLowerReferenceArguments(),this.getUpperReferenceArguments());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "<" + this.getLowerReferenceArguments() + "," + this.getUpperReferenceArguments() + ">(" + this.getInnerFormula() + ")";
	}
}
