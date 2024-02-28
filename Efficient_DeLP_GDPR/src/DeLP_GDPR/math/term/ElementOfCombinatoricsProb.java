package DeLP_GDPR.math.term;



import java.util.ArrayList;

/**
 * This class implements an element for a combinatorial problem. It can be expanded later to implement more behavior
 */

public class ElementOfCombinatoricsProb extends OptProbElement{
	public ArrayList<Term> components;
	
	public ElementOfCombinatoricsProb(ArrayList<Term> elements) {
		this.components = elements;
	}


}
