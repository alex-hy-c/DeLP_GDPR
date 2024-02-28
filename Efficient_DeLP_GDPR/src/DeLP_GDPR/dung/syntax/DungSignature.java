
package DeLP_GDPR.dung.syntax;




import java.util.*;


import org.tweetyproject.commons.*;


/**
 * This class captures the signature of a Dung theory,
 * i.e. a set of arguments.
 */
public class DungSignature extends SingleSetSignature<Argument>{

	/**
 	  * Creates a new (empty) Dung signature.
 	  */
	public DungSignature(){
		super();
	}

	/**
	 * Creates a new signature with the single given argument.
	 * @param argument an argument.
	 */
	public DungSignature(Argument argument){
		this();
		this.add(argument);
	}
	
	/**
	 * Creates a new signature with the given set of arguments.
	 * @param arguments a set of arguments.
	 */
	public DungSignature(Collection<? extends Argument> arguments){
		this();
		this.addAll(arguments);
	}

	@Override
	public void add(Object obj) {
		if (obj instanceof Argument)
			formulas.add((Argument) obj);
		else
			throw new IllegalArgumentException("Illegal type " + obj.getClass());
	
	}	
	
	@Override
	public DungSignature clone() {
		return new DungSignature(this.formulas);
	}
	
}
