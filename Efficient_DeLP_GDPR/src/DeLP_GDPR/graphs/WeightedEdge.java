package DeLP_GDPR.graphs;



/**
 * Interface for weighted edges.
 * @param <S> The type of node this edge connects
 * @param <T> The type of weight
 */
public interface WeightedEdge<S extends Node,T extends Number> {

	/**
	 * Returns the weight of this edge.
	 * @return the weight of this edge.
	 */
	public T getWeight();
}
