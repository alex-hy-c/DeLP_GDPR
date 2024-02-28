package DeLP_GDPR.graphs;





import java.util.*;

/**
 * An extended version of the DefaultGraph which supports removing of nodes as well as
 * some utility functions for d-separation
 *
 * @param <T> the type of the node
 */
public class SimpleGraph<T extends Node> extends DefaultGraph<T> implements Graph<T> {

    /**
     * create a copy of the given graph
     * @param graph some graph
     */
    public SimpleGraph(Graph<T> graph) {
        super();
        this.addAll(new HashSet<>(graph.getNodes()));
        this.addAllEdges(new HashSet<>(((DefaultGraph<T>) graph).getEdges()));
    }

    /**
     * create a new SimpleGraph instance
     */
    public SimpleGraph() {
        super();
    }

    
    /**
     * add all nodes to the graph
     * @param c a collection of nodes
     * @return true iff all nodes are added
     */
    public boolean addAll(Collection<? extends T> c) {
        boolean result = true;
        for(T t: c){
            boolean sub = this.add(t);
            result = result && sub;
        }
        return result;
    }

    /**
     * add all edges to the graph
     * @param c a collection of edges
     * @return true iff all edges are added
     */
    public boolean addAllEdges(Collection<Edge<T>> c) {
        boolean result = false;
        for(Edge<T> e: c)
            result |= this.add(e);
        return result;
    }

    /**
     * remove the given node from the graph
     * @param node some node
     * @return true iff the structure has been changed
     */
    public boolean remove(T node) {
        boolean result = true;
        for (Edge<T> edge: new HashSet<>(this.getEdges())) {
            if (node == edge.getNodeA() || node == edge.getNodeB())
            result &= this.remove(edge);
        }
        result &= this.nodes.remove(node);
        return result;
    }

    /**
     * remove the given edge from the graph
     * @param edge some edge
     * @return true iff the structure has changed
     */
    public boolean remove(Edge<T> edge) {
        return this.edges.remove(edge);
    }

    /**
     * Compute whether nodesA and nodesB are disconnected and thus d-separated in the  graph
     * @param nodesA a set of arguments
     * @param nodesB a set of arguments
     * @return true iff argsA and argsB are disconnected in the graph
     */
    public boolean areDisconnected(Collection<? extends T> nodesA, Collection<? extends T> nodesB) {
        for (T a: nodesA) {
            for (T b: nodesB) {
                // if there is a path from any node in nodesA to any node in nodesB, the sets are connected
                // since we assume all edges to be undirected, this method works just fine
                if (this.existsDirectedPath(a, b)) {
                    return false;
                }
            }
        }
        // no connection found
        return true;
    }

    /**
     * convert the graph into a undirected graph
     * @return the undirected graph
     */
    public SimpleGraph<T> toUndirectedGraph() {
        SimpleGraph<T> undirectedGraph = new SimpleGraph<>();
        undirectedGraph.addAll(this.getNodes());
        // replace all edges with undirected edges
        for (Edge<T> edge: this.getEdges()) {
            undirectedGraph.add(new UndirectedEdge<>(edge.getNodeA(), edge.getNodeB()));
        }

        return undirectedGraph;
    }
}