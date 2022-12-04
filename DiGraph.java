// 
//  Name:		Pham, Vinh 
//  Project:	5
//  Due:		9 December 2022 
//  Course:		cs-2400-02-f22 
// 
//  Description: 
//
//
// 

import java.util.Iterator;

// Why cant i just do implements BasicGraphInterface<T>, GraphAlgorithmsInterface<T>
public class DiGraph<T> implements GraphInterface<T> {
    private DictionaryInterface<T, VertexInterface<T>> vertices;
    private int edgeCount;

    public DiGraph() {
        vertices = new MapDictionary<>();
        edgeCount = 0;
    }

    public boolean addVertex(T vertexLabel) {
        VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
        return addOutcome == null;
    }

    public boolean addEdge(T begin, T end, double edgeWeight) {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);

        if ((beginVertex != null) && (endVertex != null)) {
            result = beginVertex.connect(endVertex, edgeWeight);
        }
        if (result)
            edgeCount++;
        return result;
    }

    public boolean addEdge(T begin, T end) {
        return addEdge(begin, end, 0);
    }

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

    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    public int getNumberOfVertices() {
        return vertices.getSize();
    }

    public int getNumberOfEdges() {
        return edgeCount;
    }

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

    public StackInterface<T> getTopologicalOrder() {
        throw new UnsupportedOperationException("getTopologicalOrder() is unsupported for this implementation");
    }

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

    public double getCheapestPath(T begin, T end, StackInterface<T> path) {
        throw new UnsupportedOperationException("getCheapestPath() is unsupported in this implementation");
    }

}
