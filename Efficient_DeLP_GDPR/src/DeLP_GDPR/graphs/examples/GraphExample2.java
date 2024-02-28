package DeLP_GDPR.graphs.examples;

import DeLP_GDPR.graphs.DefaultGraph;
import DeLP_GDPR.graphs.DirectedEdge;
import DeLP_GDPR.graphs.Graph;
import DeLP_GDPR.graphs.SimpleNode;
import DeLP_GDPR.graphs.util.GraphUtil;

/**
 * Another example that shows how to construct graphs programmatically.
 */
public class GraphExample2 {
	/**
	 * main
	 * @param args arguments
	 */
	public static void main(String[] args) {
		Graph<SimpleNode> g = new DefaultGraph<SimpleNode>();
		SimpleNode[] nodes = new SimpleNode[6];
		nodes[0] = new SimpleNode("A");
		nodes[1] = new SimpleNode("B");
		nodes[2] = new SimpleNode("C");
		nodes[3] = new SimpleNode("D");		
		nodes[4] = new SimpleNode("E");
		nodes[5] = new SimpleNode("F");
		for(SimpleNode n: nodes)
			g.add(n);
		g.add(new DirectedEdge<SimpleNode>(nodes[0],nodes[1]));
		g.add(new DirectedEdge<SimpleNode>(nodes[1],nodes[2]));
		g.add(new DirectedEdge<SimpleNode>(nodes[2],nodes[3]));
		g.add(new DirectedEdge<SimpleNode>(nodes[3],nodes[4]));
		g.add(new DirectedEdge<SimpleNode>(nodes[4],nodes[5]));
		g.add(new DirectedEdge<SimpleNode>(nodes[5],nodes[1]));
		
		System.out.println(GraphUtil.betweennessCentralityNormalised(g));
	}
}
