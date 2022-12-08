// 
//  Name:		Pham, Vinh 
//  Project:	5
//  Due:		9 December 2022 
//  Course:		cs-2400-02-f22 
// 
//  Description: 
//		Implementation of vertex contains a protected edge class that contains 
//		a private weight data field. Useful for directed and undirected graphs.
//		

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Vertex<T> implements VertexInterface<T> {
	private T label;
	private ListWithIteratorInterface<Edge> edgeList;
	private boolean visited; // True if visited
	private VertexInterface<T> previousVertex; // On path to this vertex
	private double cost;

	/**
	 * Creates a vertex and initializes all private data fields
	 * 
	 * @param vertexLabel label for the vertex
	 */
	public Vertex(T vertexLabel) {
		label = vertexLabel;
		edgeList = new LinkedListWithIterator<>();
		visited = false;
		previousVertex = null;
		cost = 0;
	}

	protected class Edge {
		private VertexInterface<T> vertex; // Vertex at end of edge
		private double weight;

		protected Edge(VertexInterface<T> endVertex, double edgeWeight) {
			vertex = endVertex;
			weight = edgeWeight;
		}

		protected Edge(VertexInterface<T> endVertex) {
			vertex = endVertex;
			weight = 0;
		}

		protected VertexInterface<T> getEndVertex() {
			return vertex;
		}

		protected double getWeight() {
			return weight;
		}
	}

	/**
	 * Gets this vertex's label.
	 * 
	 * @return The object that labels the vertex.
	 */
	public T getLabel() {
		return label;
	}

	/** Marks this vertex as visited. */
	public void visit() {
		visited = true;
	}

	/** Removes this vertex's visited mark. */
	public void unvisit() {
		visited = false;
	}

	/**
	 * Sees whether this vertex is marked as visited.
	 * 
	 * @return True if the vertex is visited.
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * Connects this vertex and a given vertex with a weighted edge.
	 * The two vertices cannot be the same, and must not already
	 * have this edge between them. In a directed graph, the edge
	 * points toward the given vertex.
	 * 
	 * @param endVertex  A vertex in the graph that ends the edge.
	 * @param edgeWeight A real-valued edge weight, if any.
	 * @return True if the edge is added, or false if not.
	 */
	public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
		boolean result = false;

		if (!this.equals(endVertex)) { // Vertices are distinct
			Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
			boolean duplicateEdge = false;

			if (edgeWeight == 0) {
				return removeEdge(endVertex);
			}

			while (!duplicateEdge && neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();

				if (endVertex.equals(nextNeighbor))
					duplicateEdge = true;
			}
			if (!duplicateEdge && edgeWeight != 0) {
				edgeList.add(new Edge(endVertex, edgeWeight));
				result = true;
			}
		}
		return result;
	}

	private boolean removeEdge(VertexInterface<T> endVertex) {
		boolean result = false;

		Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
		int index = 0;
		while (neighbors.hasNext()) {

			if (neighbors.next().equals(endVertex)) {
				edgeList.remove(index);
				endVertex.setCost(0);
				setCost(0);
				result = true;
				break;
			}
			index++;
		}
		return result;
	}

	/**
	 * Connects this vertex and a given vertex with an unweighted edge.
	 * The two vertices cannot be the same, and must not already
	 * have this edge between them. In a directed graph, the edge
	 * points toward the given vertex.
	 * 
	 * @param endVertex A vertex in the graph that ends the edge.
	 * @return True if the edge is added, or false if not.
	 */
	public boolean connect(VertexInterface<T> endVertex) {
		return connect(endVertex, 0);
	}

	/**
	 * Creates an iterator of this vertex's neighbors by following
	 * all edges that begin at this vertex.
	 * 
	 * @return An iterator of the neighboring vertices of this vertex.
	 */
	public Iterator<VertexInterface<T>> getNeighborIterator() {
		return new NeighborIterator();
	}

	/**
	 * Creates an iterator of the weights of the edges to this
	 * vertex's neighbors.
	 * 
	 * @return An iterator of edge weights for edges to neighbors of this
	 *         vertex.
	 */
	public Iterator<Double> getWeightIterator() {
		return new WeightIterator();
	}

	/**
	 * Sees whether this vertex has at least one neighbor.
	 * 
	 * @return True if the vertex has a neighbor.
	 */
	public boolean hasNeighbor() {
		return !edgeList.isEmpty();
	}

	/**
	 * Gets an unvisited neighbor, if any, of this vertex.
	 * 
	 * @return Either a vertex that is an unvisited neighbor or null
	 *         if no such neighbor exists.
	 */
	public VertexInterface<T> getUnvisitedNeighbor() {
		VertexInterface<T> result = null;
		Iterator<VertexInterface<T>> neighbors = getNeighborIterator();

		while (neighbors.hasNext() && (result == null)) {
			VertexInterface<T> nextNeighbor = neighbors.next();
			if (!nextNeighbor.isVisited())
				result = nextNeighbor;
		}
		return result;
	}

	/**
	 * Records the previous vertex on a path to this vertex.
	 * 
	 * @param predecessor The vertex previous to this one along a path.
	 */
	public void setPredecessor(VertexInterface<T> predecessor) {
		previousVertex = predecessor;
	}

	/**
	 * Gets the recorded predecessor of this vertex.
	 * 
	 * @return Either this vertex's predecessor or null if no predecessor
	 *         was recorded.
	 */
	public VertexInterface<T> getPredecessor() {
		return previousVertex;
	}

	/**
	 * Sees whether a predecessor was recorded for this vertex.
	 * 
	 * @return True if a predecessor was recorded.
	 */
	public boolean hasPredecessor() {
		return previousVertex != null;
	}

	/**
	 * Records the cost of a path to this vertex.
	 * 
	 * @param newCost The cost of the path.
	 */
	public void setCost(double newCost) {
		cost = newCost;
	}

	/**
	 * Gets the recorded cost of the path to this vertex.
	 * 
	 * @return The cost of the path.
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Compares two objects to each other
	 * 
	 * @return True if objects are equal to each other, False if not
	 */
	@Override
	public boolean equals(Object other) {
		boolean result;
		if ((other == null) || (getClass() != other.getClass()))
			result = false;
		else {
			@SuppressWarnings("unchecked")
			Vertex<T> otherVertex = (Vertex<T>) other;
			result = label.equals(otherVertex.label);
		}
		return result;
	}

	private class NeighborIterator implements Iterator<VertexInterface<T>> {
		private Iterator<Edge> edges;

		private NeighborIterator() {
			edges = edgeList.getIterator();
		}

		public boolean hasNext() {
			return edges.hasNext();
		}

		public VertexInterface<T> next() {
			VertexInterface<T> nextNeighbor = null;
			if (edges.hasNext()) {
				nextNeighbor = edges.next().getEndVertex();
			} else
				throw new NoSuchElementException();
			return nextNeighbor;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private class WeightIterator implements Iterator<Double> {
		private Iterator<Edge> edges;

		private WeightIterator() {
			edges = edgeList.getIterator();
		}

		public boolean hasNext() {
			return edges.hasNext();
		}

		public Double next() {
			double nextNeighborWeight;
			if (edges.hasNext()) {
				nextNeighborWeight = edges.next().getWeight();
			} else
				throw new NoSuchElementException();
			return nextNeighborWeight;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
