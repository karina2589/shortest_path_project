package final_project;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {

    HashMap<String, List<Edge>> graph;

    public Graph(){
        this.graph = new HashMap<>();
    }

    public void addVertex(String source){
        this.graph.put(source, new ArrayList<>());
    }



    public void addEdge(String source, String destination, double distance, int velocity, int traffic) {
        Edge edge = new Edge(source, destination, distance, velocity, traffic);
        List<Edge> edges = this.graph.get(source);
        if (edges == null) {
            edges = new ArrayList<>();
            this.graph.put(source, edges);
        }
        edges.add(edge);
    }

    public List<Edge> getEdges(String vertex) {
        return this.graph.get(vertex);
    }

    public Edge getEdge(String source, String destination) {
        List<Edge> edges = graph.get(source);
        if (edges != null) {
            for (Edge edge : edges) {
                if (edge.getDestination().equals(destination)) {
                    return edge;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String source : graph.keySet()) {
            sb.append(source).append(": ");

            List<Edge> edges = graph.get(source);
            for (int i = 0; i < edges.size(); i++) {
                sb.append(edges.get(i).toString());

                if (i < edges.size() - 1) {
                    sb.append(", ");
                }
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    public void printGraph(){
        System.out.println(graph);
    }
}



