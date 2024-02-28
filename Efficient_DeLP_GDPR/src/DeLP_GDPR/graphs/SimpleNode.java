package DeLP_GDPR.graphs;



/**
 * A simple node of a graph with a name.
 */
public class SimpleNode implements Node{
	
	/** The name of the node */
	private String name;
	
	/**
	 * Creates a new node with the given name.
	 * @param s some string.
	 */
	public SimpleNode(String s){
		this.name = s;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return this.name;
	}
	
}