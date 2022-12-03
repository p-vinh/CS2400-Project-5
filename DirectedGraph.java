public class DirectedGraph implements GraphInterface<VertexInterface<String>> {


    public boolean isEmpty() {

        return false;
    }



    public int getNumberOfVertices() {

        return 0;
    }



    public int getNumberOfEdges() {

        return 0;
    }



    public void clear() {

        
    }

    public boolean addVertex(VertexInterface<String> vertexLabel) {

        return false;
    }



    public boolean addEdge(VertexInterface<String> begin, VertexInterface<String> end, double edgeWeight) {

        return false;
    }



    public boolean addEdge(VertexInterface<String> begin, VertexInterface<String> end) {

        return false;
    }



    public boolean hasEdge(VertexInterface<String> begin, VertexInterface<String> end) {

        return false;
    }



    public QueueInterface<VertexInterface<String>> getBreadthFirstTraversal(VertexInterface<String> origin) {

        return null;
    }


    public QueueInterface<VertexInterface<String>> getDepthFirstTraversal(VertexInterface<String> origin) {

        return null;
    }

    public StackInterface<VertexInterface<String>> getTopologicalOrder() {

        return null;
    }


    public int getShortestPath(VertexInterface<String> begin, VertexInterface<String> end,
            StackInterface<VertexInterface<String>> path) {

        return 0;
    }


    public double getCheapestPath(VertexInterface<String> begin, VertexInterface<String> end,
            StackInterface<VertexInterface<String>> path) {

        return 0;
    }







    
}
