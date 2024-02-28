package DeLP_GDPR.dung.syntax;

import org.tweetyproject.commons.*;

import DeLP_GDPR.dung.ldo.syntax.LdoArgument;
import DeLP_GDPR.dung.ldo.syntax.LdoFormula;
import DeLP_GDPR.graphs.Node;

/**
 * This class models an argument used by Dung's abstract argumentation theory and is just described by its name.
 *
 */
public class Argument implements DungEntity, Node, Comparable<Argument>{
	/**
	 * The name of the argument.
	 */
	private String name;

	/**
	 * Default constructor that assigns the given <code>name</code> to this argument
	 * @param name the name of the argument
	 */
	public Argument(String name){
		this.name = name;
	}

	/**
	 * returns the name of the argument
	 * @return the name of the argument
	 */
	public String getName(){
		return name;
	}

	/**
	 * sets the name of the argument
	 * @param name the name of the argument
	 */
	public void setName(String name){
		this.name = name;
	}
	

	public Signature getSignature(){
		return new DungSignature(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o){
		if(!o.getClass().equals(this.getClass())) return false;
		if(!((Argument)o).getName().equals(getName())) return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){
		return this.name.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return name;
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.dung.syntax.DungEntity#getLdoFormula()
	 */
	@Override
	public LdoFormula getLdoFormula() {
		return getLdoArgument();
	}
	
	/**
	 * 
	 * @return getLdoArgument
	 */
	public LdoArgument getLdoArgument(){
		return new LdoArgument(this.name);
	}

	@Override
	public int compareTo(Argument o) {
		return this.name.compareTo(o.name);
	}
}
