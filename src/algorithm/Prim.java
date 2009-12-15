package algorithm;

import java.awt.Color;
import java.util.HashSet;

import javax.swing.JFrame;

import view.GraphGUI;

import model.DirectedEdge;
import model.Edge;
import model.Graph;
import model.Tree;
import model.Vertex;

// prim's algorithm
// get a minimal spanning tree
public class Prim implements GraphAlgorithm {
	private Graph graph;
	private Tree tree = new Tree();
	
	/**
	 * the GUI handles display
	 */
	private GraphGUI gui;
	
	public Prim(Graph graph) {
		this.graph = graph;
	}
	
	public void execute() {
		
		// prepare edges for display
		for (Edge e : graph.getEdges()) {
			e.setColor(Color.gray);
		}
		
		Edge shortestEdge;
		Vertex randomVertex = graph.getRandomVertex();
		
		// initial edge
		shortestEdge = getShortestEdge(randomVertex);
		
		// select vertex
		randomVertex.setColor(Color.red);
		
		// select vertices and edge
		shortestEdge.getV1().setColor(Color.red);
		shortestEdge.getV2().setColor(Color.red);
		shortestEdge.setColor(Color.red);
		
		// viz
		gui.repaint();
		
		// add initial edge to tree
		tree.add(shortestEdge);
		
		while (true) {
			// find next shortest edge
			shortestEdge = getShortestEdge();
			
			// none found
			if (shortestEdge == null) {
				System.out.println("Error - no shortest edge found");
				return;
			}
			
			System.out.println(shortestEdge.getV1() + " " + shortestEdge.getV2());
			
			// select vertices and edge
			shortestEdge.getV1().setColor(Color.red);
			shortestEdge.getV2().setColor(Color.red);
			shortestEdge.setColor(Color.red);
			
			// viz
			gui.repaint();
			
			// add shortest edge to tree
			tree.add(shortestEdge);
			
			// check for final spanning tree
			if (graph.getVertices().size() - 1 == tree.size()) {
				break;
			}
		}
		
		// we're done
	}

	// get shortest edge for a vertex
	private Edge getShortestEdge(Vertex vertex) {
		Edge shortest = null;
		for (DirectedEdge e : graph.getVertexEdges(vertex)) {
			
			// skip self-referential edges
			if (e.getTarget() == e.getOrigin()) {
				continue;
			}
			
			if (shortest == null || e.getEdge().getWeight() < shortest.getWeight()) {
				shortest = e.getEdge();
			}
		}
		return shortest;
	}

	// get shortest edge
	// that touches tree at 1 end exclusively
	private Edge getShortestEdge() {
		Edge shortest = null;
		for (Edge e : graph.getEdges()) {
			
			// skip self-referential edges
			if (e.getV2() == e.getV1()) {
				continue;
			}
			
			// check for tree connections
			if (!touchesTreeExclusive(e)) {
				continue;
			}
			
			if (shortest == null || e.getWeight() < shortest.getWeight()) {
				shortest = e;
			}
		}
		return shortest;
	}
	
	// graph getter
	public Graph getGraph() {
		return graph;
	}
	
	// graph setter
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	// edge touches existing tree only at one end
	private boolean touchesTreeExclusive(Edge edge) {
		int matches = 0;
		for (Vertex vertex : getTreeVertices()) {
			if (edge.getV1() == vertex) matches++;
			if (edge.getV2() == vertex) matches++;
			
			if (matches >= 2) {
				return false;
			}
		}
		return (matches == 1) ? true : false;
	}
	
	private HashSet<Vertex> getTreeVertices() {
		HashSet<Vertex> vertices = new HashSet<Vertex>();
		for (Edge e : tree.getEdges()) {
			vertices.add(e.getV1());
			vertices.add(e.getV2());
		}
		return vertices;
	}
	
	public void setGUI(GraphGUI gui) {
		this.gui = gui;
	}
	
	public void settingsFrame(JFrame parent) {
	}
	
	// reset to a neutral state
	public void reset() {
		graph.reset();
		tree = new Tree();
		
		gui.repaint();
	}
	
	/**
	 * string representation of graph
	 * 
	 * @return graph name
	 */
	public String toString() {
		return getClass().getName();
	}
	
	public static void main(String[] args) {
		Vertex a = new Vertex("A");
		Vertex b = new Vertex("B");
		Vertex c = new Vertex("C");
		Vertex d = new Vertex("D");
		Vertex e = new Vertex("E");
		Vertex f = new Vertex("F");
		
		Graph graph = new Graph();
		graph.add(a, b, c, d, e, f);
		
		graph.connect(a, b, 5);
		graph.connect(a, d, 4);
		graph.connect(a, e, 8);
		graph.connect(b, c, 3);
		graph.connect(b, d, 3);
		graph.connect(d, c, 7);
		graph.connect(d, e, 3);
		graph.connect(e, f, 1);
		graph.connect(c, f, 2);
		
		Prim prim = new Prim(graph);
		prim.execute();
	}
}
