import java.util.Iterator;
import java.util.NoSuchElementException;

public class Vertex<T> implements VertexInterface<T> {
    // 1099
    private T label;
    private ListWithIteratorInterface<Edge> edgeList;
    private boolean visited; // True if visited
    private VertexInterface<T> previousVertex; // On path to this vertex
    private double cost;

    public Vertex(T vertexLabel) {
        label = vertexLabel;
        edgeList = new LinkedListWithIterator<>();
        visited = false;
        previousVertex = null;
        cost = 0;
    }

    public T getLabel() {

        return null;
    }

    public void visit() {

    }

    public void unvisit() {

    }

    public boolean isVisited() {

        return false;
    }

    public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
        boolean result = false;
        if (!this.equals(endVertex)) { // Vertices are distinct
            Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
            boolean duplicateEdge = false;
            while (!duplicateEdge && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    duplicateEdge = true;
            }
            if (!duplicateEdge) {
                edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            }
        }
        return result;
    }

    public boolean connect(VertexInterface<T> endVertex) {
        return connect(endVertex, 0);
    }

    public Iterator<VertexInterface<T>> getNeighborIterator() {

        return new NeighborIterator();
    }

    public Iterator<Double> getWeightIterator() {

        return null;
    }

    public boolean hasNeighbor() {

        return false;
    }

    public VertexInterface<T> getUnvisitedNeighbor() {
        return !edgeList.isEmpty();
    }

    public void setPredecessor(VertexInterface<T> predecessor) {

    }

    public VertexInterface<T> getPredecessor() {

        return null;
    }

    public boolean hasPredecessor() {

        return false;
    }

    public void setCost(double newCost) {

    }

    public double getCost() {

        return 0;
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
                Edge edgeToNextNeighbor = edges.next();
                nextNeighbor = edgeToNextNeighbor.getEndVertex();
            } else
                throw new NoSuchElementException();
            return nextNeighbor;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
