package final_project;

import java.util.*;

public class DijkstraAlgorithm {
    public static Object dijkstraPath(Graph graph, String startVertex, String destination, int velocity, String transType, int time) {
        long startTime_l = System.currentTimeMillis();
        int arr = 0;
        List<Integer> avgTraffic = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        HashMap<String, Double> times = new HashMap<>();
        HashMap<String, String> previousNodes = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (String vertex : graph.graph.keySet()) {
            times.put(vertex, Double.POSITIVE_INFINITY);
            previousNodes.put(vertex, null);
        }

        times.put(startVertex, 0.0);
        pq.add(new Node(startVertex, 0.0));
        // Visit all vertices
        while (!pq.isEmpty()) {

            Node currentNode = pq.poll();

            String current = currentNode.getNode();
            double currentDistance = currentNode.getDistance();

            if(current.equalsIgnoreCase(destination)){
                break;
            }

            if(visited.contains(current)){
                continue;
            }

            visited.add(current);


            for(Edge edge : graph.getEdges(current)){
                if(!visited.contains(edge.getDestination())) {
                    if(times.get(edge.getDestination()) == null){
//                        return "there is no such path";
                        continue;
                    }
                    double newTime;
                    if(transType.equalsIgnoreCase("car") || transType.equalsIgnoreCase("bus")) {
                        newTime = times.get(current) + (edge.getDistance() * graph.getEdge(edge.pointA, edge.pointB).getCurrentTraffic(time) / (velocity));
                        arr = graph.getEdge(edge.pointA, edge.pointB).getCurrentTraffic(time);
                        avgTraffic.add(arr);
                    }else {
                        newTime = times.get(current) + (edge.getDistance() / velocity);
                    }

                    if (newTime < times.get(edge.getDestination())) {
                        times.put(edge.getDestination(), newTime);

                        previousNodes.put(edge.getDestination(), edge.getSource());
                        pq.add(new Node(edge.getDestination(), newTime));
                    }

                }
            }

        }

//        if (times.get(source) + distance < times.get(dest)) {
//            if (transType.equalsIgnoreCase("car") || transType.equalsIgnoreCase("bus")) {
//                double newTime = times.get(source) + (distance * graph.getEdge(edge.pointA, edge.pointB).getCurrentTraffic(time) / velocity);
//                arr = graph.getEdge(edge.pointA, edge.pointB).getCurrentTraffic(time);
//                times.put(dest, newTime);
//                avgTraffic.add(arr);
//            }

        List<String> path = new ArrayList<>();
        String currentNode = destination;
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = previousNodes.get(currentNode);
        }
        Collections.reverse(path);

        double newdistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String s = path.get(i);
            String d = path.get(i + 1);
            newdistance += graph.getEdge(s, d).getDistance();

        }


        Map<String, Object> result = new HashMap<>();
        result.put("time", (int) (times.get(destination)*60));
//        result.put("distance", graph.getDistanceOfPath(path));
        result.put("path", path);
        result.put("distance", newdistance);
        if(transType.equalsIgnoreCase("walk") || transType.equalsIgnoreCase("bicycle")){
            result.put("traffic", 0);
        }

        int sumT = 0;
        if(avgTraffic.isEmpty()){
            result.put("traffic", 0);
        }else{
            for(int i : avgTraffic){
                sumT += i;
            }
            result.put("traffic", sumT/avgTraffic.size());

        }




        long endTime_l = System.currentTimeMillis();
        long total = endTime_l-startTime_l;

        result.put("time of execution", total);
        result.put("transport", transType);
//        System.out.println(result.get("avg traffic"));
//        System.out.println("dijkstra time - "+result.get("time"));

 //       printTable(total, transType, a rr);
        return result;


    }

}


class Node implements Comparable<Node> {
    private String node;
    private double distance;

    public Node(String node, double distance) {
        this.node = node;
        this.distance = distance;
    }

    public String getNode() {
        return node;
    }


    public double getDistance() {
        return distance;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(distance, other.distance);
    }
}
