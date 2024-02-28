package DeLP_GDPR.graphs;



/**
 * Instances of this class represent directed edges.
 *
 * @param <T> The type of the nodes this edge connects
 */
public class DirectedEdge<T extends Node> extends Edge<T> {

	/** Creates a new directed edge for the given nodes.
	 * @param nodeA some node.
	 * @param nodeB some node.
	 */
	public DirectedEdge(T nodeA, T nodeB) {
		super(nodeA, nodeB);
	}

	/** Creates a new directed edge for the given nodes.
	 * @param nodeA some node.
	 * @param nodeB some node.
	 * @param label some edge label.
	 */
	public DirectedEdge(T nodeA, T nodeB, String label) {
		super(nodeA, nodeB, label);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "(" + this.getNodeA() + "," + this.getNodeB() + ")"; 
	}
}
