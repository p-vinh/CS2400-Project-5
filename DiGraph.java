// 
//  Name:		Pham, Vinh 
//  Project:	5
//  Due:		9 December 2022 
//  Course:		cs-2400-02-f22 
// 
//  Description: 
//		Implementation of a directional graph that uses a protected 
//	edge class for weighted and unweighted. Implements graph intereface.
//

import java.util.Iterator;
import java.util.PriorityQueue;

public class DiGraph<T> implements GraphInterface<T> {
	private DictionaryInterface<T, VertexInterface<T>> vertices;
	private int edgeCount;

	/**
	 * initializes DiGraph
	 */
	public DiGraph() {
		vertices = new MapDictionary<>();
		edgeCount = 0;
	}

	/**
	 * Adds a given vertex to this graph.
	 * 
	 * @param vertexLabel An object that labels the new vertex and is
	 *                    distinct from the labels of current vertices.
	 * @return True if the vertex is added, or false if not.
	 */
	public boolean addVertex(T vertexLabel) {
		VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
		return addOutcome == null;
	}

	/**
	 * Adds a weighted edge between two given distinct vertices that
	 * are currently in this graph. The desired edge must not already
	 * be in the graph. In a directed graph, the edge points toward
	 * the second vertex given.
	 * 
	 * @param begin      An object that labels the origin vertex of the edge.
	 * @param end        An object, distinct from begin, that labels the end
	 *                   vertex of the edge.
	 * @param edgeWeight The real value of the edge's weight.
	 * @return True if the edge is added, or false if not.
	 */
	public boolean addEdge(T begin, T end, double edgeWeight) {
		boolean result = false;
		boolean remove = false;
		VertexInterface<T> beginVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);

		if ((beginVertex != null) && (endVertex != null)) {
			// For the removal method
			if (edgeWeight == 0) {
				remove = beginVertex.connect(endVertex, edgeWeight);
			} else
				result = beginVertex.connect(endVertex, edgeWeight);
		}
		if (result)
			edgeCount++;
		if (remove) {
			edgeCount--;
			return remove;
		}
		return result;
	}

	/**
	 * Adds an unweighted edge between two given distinct vertices
	 * that are currently in this graph. The desired edge must not
	 * already be in the graph. In a directed graph, the edge points
	 * toward the second vertex given.
	 * 
	 * @param begin An object that labels the origin vertex of the edge.
	 * @param end   An object, distinct from begin, that labels the end
	 *              vertex of the edge.
	 * @return True if the edge is added, or false if not.
	 */
	public boolean addEdge(T begin, T end) {
		return addEdge(begin, end, 0);
	}

	/**
	 * Sees whether an edge exists between two given vertices.
	 * 
	 * @param begin An object that labels the origin vertex of the edge.
	 * @param end   An object that labels the end vertex of the edge.
	 * @return True if an edge exists.
	 */
	public boolean hasEdge(T begin, T end) {
		boolean found = false;
		VertexInterface<T> beginVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);

		if ((beginVertex != null) && (endVertex != null)) {
			Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();

			while (!found && neighbors.hasNext()) {
				if (endVertex.equals(neighbors.next())) {
					found = true;
				}
			}
		}
		return found;
	}

	/**
	 * Sees whether this graph is empty.
	 * 
	 * @return True if the graph is empty.
	 */
	public boolean isEmpty() {
		return vertices.isEmpty();
	}

	/**
	 * Gets the number of vertices in this graph.
	 * 
	 * @return The number of vertices in the graph.
	 */
	public int getNumberOfVertices() {
		return vertices.getSize();
	}

	/**
	 * Gets the number of edges in this graph.
	 * 
	 * @return The number of edges in the graph.
	 */
	public int getNumberOfEdges() {
		return edgeCount;
	}

	/**
	 * Removes all vertices and edges from this graph resulting in an empty graph.
	 */
	public void clear() {
		vertices.clear();
		edgeCount = 0;
	}

	protected void resetVertices() {
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		while (vertexIterator.hasNext()) {
			VertexInterface<T> nextVertex = vertexIterator.next();
			nextVertex.unvisit();
			nextVertex.setCost(0);
			nextVertex.setPredecessor(null);
		}
	}

	/**
	 * Performs a breadth-first traversal of this graph.
	 * 
	 * @param origin An object that labels the origin vertex of the traversal.
	 * @return A queue of labels of the vertices in the traversal, with
	 *         the label of the origin vertex at the queue's front.
	 */
	public QueueInterface<T> getBreadthFirstTraversal(T origin) {
		resetVertices();
		QueueInterface<T> traversalOrder = new LinkedQueue<>();
		QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();
		VertexInterface<T> originVertex = vertices.getValue(origin);
		originVertex.visit();
		traversalOrder.enqueue(origin); // Enqueue vertex label
		vertexQueue.enqueue(originVertex); // Enqueue vertex

		while (!vertexQueue.isEmpty()) {
			VertexInterface<T> frontVertex = vertexQueue.dequeue();
			Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
			while (neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (!nextNeighbor.isVisited()) {
					nextNeighbor.visit();
					traversalOrder.enqueue(nextNeighbor.getLabel());
					vertexQueue.enqueue(nextNeighbor);
				}
			}
		}
		return traversalOrder;
	}

	/**
	 * Performs a depth-first traversal of this graph.
	 * 
	 * @param origin An object that labels the origin vertex of the traversal.
	 * @return A queue of labels of the vertices in the traversal, with
	 *         the label of the origin vertex at the queue's front.
	 */
	public QueueInterface<T> getDepthFirstTraversal(T origin) {
		resetVertices();
		QueueInterface<T> traversalOrder = new LinkedQueue<>();
		StackInterface<VertexInterface<T>> vertexStack = new LinkedStack<>();
		VertexInterface<T> originVertex = vertices.getValue(origin);

		traversalOrder.enqueue(origin);
		vertexStack.push(originVertex);

		while (!vertexStack.isEmpty()) {
			VertexInterface<T> frontVertex = vertexStack.pop();
			Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();

			while (neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();

				if (!nextNeighbor.isVisited()) {
					nextNeighbor.visit();
					traversalOrder.enqueue(nextNeighbor.getLabel());
					vertexStack.push(nextNeighbor);
				}
			}
		}
		return traversalOrder;
	}

	/**
	 * Performs a topological sort of the vertices in a graph without cycles.
	 * 
	 * @return A stack of vertex labels in topological order, beginning
	 *         with the stack's top.
	 */
	public StackInterface<T> getTopologicalOrder() {
		throw new UnsupportedOperationException("getTopologicalOrder() is unsupported for this implementation");
	}

	/**
	 * Finds the shortest-length path between two given vertices in this graph.
	 * 
	 * @param begin An object that labels the path's origin vertex.
	 * @param end   An object that labels the path's destination vertex.
	 * @param path  A stack of labels that is empty initially;
	 *              at the completion of the method, this stack contains
	 *              the labels of the vertices along the shortest path;
	 *              the label of the origin vertex is at the top, and
	 *              the label of the destination vertex is at the bottom.
	 * @return The length of the shortest path.
	 */
	public int getShortestPath(T originVertex, T endVertex, StackInterface<T> path) {
		resetVertices();
		boolean done = false;
		QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();
		VertexInterface<T> orginVert = vertices.getValue(originVertex);
		VertexInterface<T> endVert = vertices.getValue(endVertex);

		orginVert.visit();
		vertexQueue.enqueue(orginVert);

		while (!done && !vertexQueue.isEmpty()) {
			VertexInterface<T> frontVertex = vertexQueue.dequeue();
			Iterator<VertexInterface<T>> neighborIterator = frontVertex.getNeighborIterator();
			while (!done && neighborIterator.hasNext()) {
				VertexInterface<T> nextNeighbor = neighborIterator.next();

				if (!nextNeighbor.isVisited()) {
					nextNeighbor.visit();
					nextNeighbor.setCost(1 + frontVertex.getCost());
					nextNeighbor.setPredecessor(frontVertex);
					vertexQueue.enqueue(nextNeighbor);
				}
				if (nextNeighbor.equals(endVert)) {
					done = true;
				}
			}
		}

		// Traversal ends; construct shortest path
		// Assuming the Label Exist
		int pathLength = (int) endVert.getCost();
		path.push(endVert.getLabel());
		VertexInterface<T> vertex = endVert;

		while (vertex.hasPredecessor()) {
			vertex = vertex.getPredecessor();
			path.push(vertex.getLabel());
		}

		return pathLength;
	}

	/**
	 * Finds the least-cost path between two given vertices in this graph.
	 * 
	 * @param begin An object that labels the path's origin vertex.
	 * @param end   An object that labels the path's destination vertex.
	 * @param path  A stack of labels that is empty initially;
	 *              at the completion of the method, this stack contains
	 *              the labels of the vertices along the cheapest path;
	 *              the label of the origin vertex is at the top, and
	 *              the label of the destination vertex is at the bottom.
	 * @return The cost of the cheapest path.
	 */
	public double getCheapestPath(T begin, T end, StackInterface<T> path) {
		resetVertices();
		boolean done = false;
		PriorityQueue<EntryPQ> priorityQueue = new PriorityQueue<>();
		VertexInterface<T> originVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);

		priorityQueue.add(new EntryPQ(originVertex, 0, null));

		while (!done && !priorityQueue.isEmpty()) {
			EntryPQ frontEntry = priorityQueue.remove();
			VertexInterface<T> frontVertex = frontEntry.getVertex();

			if (!frontVertex.isVisited()) {
				frontVertex.visit();
				frontVertex.setCost(frontEntry.getCost());
				frontVertex.setPredecessor(frontEntry.getPredecessor());

				if (frontVertex.equals(endVertex))
					done = true;
				else {
					Iterator<VertexInterface<T>> neighbor = frontVertex.getNeighborIterator();
					Iterator<Double> weightIterator = frontVertex.getWeightIterator();
					while (neighbor.hasNext()) {
						VertexInterface<T> nextNeighbor = neighbor.next();
						double weightOfEdgeToNeighbor = weightIterator.next();

						if (!nextNeighbor.isVisited()) {
							double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();
							priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
						}
					}
				}
			}
		}

		// Traversal ends; construct cheapest path
		double pathCost = endVertex.getCost();
		path.push(endVertex.getLabel());
		VertexInterface<T> vertex = endVertex;

		while (vertex.hasPredecessor()) {
			vertex = vertex.getPredecessor();
			path.push(vertex.getLabel());
		}

		return pathCost;
	}

	private class EntryPQ implements Comparable<EntryPQ> {
		private VertexInterface<T> vertex;
		private double cost;
		private VertexInterface<T> predecessor;

		private EntryPQ(VertexInterface<T> origin, double cost, VertexInterface<T> predecessor) {
			vertex = origin;
			this.cost = cost;
			this.predecessor = predecessor;
		}

		private VertexInterface<T> getVertex() {
			return vertex;
		}

		private void setVertex(VertexInterface<T> vertex) {
			this.vertex = vertex;
		}

		private double getCost() {
			return cost;
		}

		private void setCost(double cost) {
			this.cost = cost;
		}

		private VertexInterface<T> getPredecessor() {
			return predecessor;
		}

		private void setPredecessor(VertexInterface<T> predecessor) {
			this.predecessor = predecessor;
		}

		@Override
		public int compareTo(EntryPQ o) {
			return Double.compare(cost, o.cost);
		}

	}
}
