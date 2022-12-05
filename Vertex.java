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
import java.util.NoSuchElementException;

public class Vertex<T> implements VertexInterface<T> {
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
        return label;
    }

    public void visit() {
        visited = true;
    }

    public void unvisit() {
        visited = false;
    }

    public boolean isVisited() {
        return visited;
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
        return new WeightIterator();
    }

    public boolean hasNeighbor() {
        return !edgeList.isEmpty();
    }

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

    public void setPredecessor(VertexInterface<T> predecessor) {
        previousVertex = predecessor;
    }

    public VertexInterface<T> getPredecessor() {
        return previousVertex;
    }

    public boolean hasPredecessor() {
        return previousVertex != null;
    }

    public void setCost(double newCost) {
        cost = newCost;
    }

    public double getCost() {
        return cost;
    }

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
            return cost + weight;
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
