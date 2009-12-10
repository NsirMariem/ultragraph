package model;

import java.awt.Color;

public class Edge implements Comparable<Edge> {
	private Vertex v1;
	private Vertex v2;
	private int weight;
	
	private Color color = Color.black;
	
	public Edge(Vertex origin, Vertex target, int weight) {
		setV1(origin);
		setV2(target);
		setWeight(weight);
	}
	
	public Edge(Vertex origin, Vertex target) {
		setV1(origin);
		setV2(target);
		setWeight(1);
	}
	
	public Vertex getV1() {
		return v1;
	}
	
	public void setV1(Vertex origin) {
		this.v1 = origin;
	}
	
	public Vertex getV2() {
		return v2;
	}
	
	public void setV2(Vertex target) {
		this.v2 = target;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	// get full relative weight, must specify origin
	public int getFullWeight(Vertex origin) {
		return (origin.isLabeled() ? origin.getLabel() : 0) + weight;
	}

	// reset to a neutral state
	public void reset() {
		color = Color.black;
	}
	
	public String toString() {
		return getV1() + " " + getV2() + " " + getWeight();
	}
	
	public int compareTo(Edge e) {
		if (getWeight() > e.getWeight()) {
			return 1;
		} else if (getWeight() < e.getWeight()) {
			return -1;
		} else {
			return 0;
		}
	}
}
