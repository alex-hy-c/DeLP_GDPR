package DeLP_GDPR.graphs;



/**
 *
 * @param <S> a Node
 * @param <T> a number
 */
public class WeightedDirectedEdge<S extends Node,T extends Number> extends DirectedEdge<S> implements WeightedEdge<S,T>{

	/** The weight of this edge. */
	private T weight;
	
	/**
	 * Creates a new weighted direct edge.
	 * @param nodeA some node
	 * @param nodeB some node
	 * @param weight some weight
	 */
	public WeightedDirectedEdge(S nodeA, S nodeB, T weight) {
		super(nodeA, nodeB);
		this.weight = weight;
	}

	/* (non-Javadoc)
	 * @see DeLP_GDPR.graphs.WeightedEdge#getWeight()
	 */
	@Override
	public T getWeight() {
		return this.weight;
	}
	
	/**
	 * 
	 * @param number a number for the weight
	 */
	public void setWeight(T number) {
		this.weight = number;
	}
}
