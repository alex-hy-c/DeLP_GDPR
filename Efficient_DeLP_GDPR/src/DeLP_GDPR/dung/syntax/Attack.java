package DeLP_GDPR.dung.syntax;




import java.util.*;

import org.tweetyproject.commons.*;

import DeLP_GDPR.dung.ldo.syntax.LdoFormula;
import DeLP_GDPR.dung.ldo.syntax.LdoNegation;
import DeLP_GDPR.dung.ldo.syntax.LdoRelation;
import DeLP_GDPR.graphs.DirectedEdge;

/**
 * This class models an attack between two arguments. It comprises of two attributes of <code>Argument</code> and is mainly used by
 * abstract argumentation theories as e.g. <code>DungTheory</code>.
 */
public class Attack extends DirectedEdge<Argument> implements DungEntity {

	/**
	 * Default constructor; initializes the two arguments used in this attack relation
	 * @param attacker the attacking argument
	 * @param attacked the attacked argument
	 */
	public Attack(Argument attacker, Argument attacked){
		super(attacker,attacked);
	}

	/**
	 * returns true if one arguments in <code>arguments</code> attacks another within this attack relation.
	 * @param arguments a list of arguments
	 * @return returns true if one arguments in <code>arguments</code> attacks another.
	 */
	public boolean isConflictFree(Collection<? extends Argument> arguments){
		Iterator<? extends Argument> it = arguments.iterator();
		while(it.hasNext()){
			Argument arg = (Argument) it.next();
			if(arg.equals(this.getAttacker())){
				Iterator<? extends Argument> it2 = arguments.iterator();
				while(it2.hasNext()){
					Argument arg2 = (Argument) it2.next();
					if(arg2.equals(this.getAttacked()))
						return false;
				}
			}
		}
		return true;
	}

	/**
	 * returns the attacked argument of this attack relation.
	 * @return the attacked argument of this attack relation.
	 */
	public Argument getAttacked() {
		return this.getNodeB();
	}

	/**
	 * returns the attacking argument of this attack relation.
	 * @return the attacking argument of this attack relation.
	 */
	public Argument getAttacker() {
		return this.getNodeA();
	}

	
	/**
	 * Return true if the given argument is in this attack relation.
	 * @param argument some argument
	 * @return true if the given argument is in this attack relation.
	 */
	public boolean contains(Argument argument){
		return this.getAttacked().equals(argument) || this.getAttacker().equals(argument);
	}

	
	
	public Signature getSignature(){
		DungSignature sig = new DungSignature();
		sig.add(this.getAttacked());
		sig.add(this.getAttacker());
		return sig;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "("+this.getAttacker().toString()+","+this.getAttacked().toString()+")";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o){
		if(!o.getClass().equals(this.getClass())) return false;
		if(!this.getAttacker().equals(((Attack)o).getAttacker())) return false;
		if(!this.getAttacked().equals(((Attack)o).getAttacked())) return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){
		return this.getAttacked().hashCode() + 7 * this.getAttacker().hashCode();
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.dung.syntax.DungEntity#getLdoFormula()
	 */
	@Override
	public LdoFormula getLdoFormula() {
		return new LdoRelation(this.getAttacker().getLdoFormula(), new LdoNegation(this.getAttacked().getLdoFormula()));
	}

}
