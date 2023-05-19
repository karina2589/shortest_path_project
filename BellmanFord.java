package final_project;

import java.util.*;

class BellmanFord{
    public static Map<String, Object> bellmanPath(Graph graph, String startVertex, String destination, int velocity, String transType, int time) {
        long startTime_l = System.currentTimeMillis();
        List<Integer> avgTraffic = new ArrayList<>();

//        Set<String> visited = new HashSet<>();
        HashMap<String, Double> times = new HashMap<>();
        HashMap<String, String> previousNodes = new HashMap<>();
        int arr = 0;

        for (String vertex : graph.graph.keySet()) {
//            times.put(vertex, Double.POSITIVE_INFINITY);
//            previousNodes.put(vertex, null);
            if(vertex.equals(startVertex)){
                times.put(startVertex, 0.0);

            }else {
                times.put(vertex,Double.POSITIVE_INFINITY);

            }
            previousNodes.put(vertex, null);
        }

        times.put(startVertex, 0.0);

        for (int i = 1; i < graph.graph.size(); i++) {
            for (String source : graph.graph.keySet()) {
                for (Edge edge : graph.getEdges(source)) {
                    String dest = edge.getDestination();
                    double distance = edge.getDistance();

                    if(times.get(edge.getDestination()) == null){
//                        return "there is no such path";
                        continue;
                    }

                    if (times.get(source) + (edge.getDistance() / velocity) < times.get(dest)) {
                        if (transType.equalsIgnoreCase("car") || transType.equalsIgnoreCase("bus")) {
                            double newTime = times.get(source) + (distance * graph.getEdge(edge.pointA, edge.pointB).getCurrentTraffic(time) / velocity);
                            arr = graph.getEdge(edge.pointA, edge.pointB).getCurrentTraffic(time);
                            avgTraffic.add(arr);
                            times.put(dest, newTime);
                        } else {
                            double newTime = times.get(source) + (distance / velocity);
                            times.put(dest, newTime);
                        }
                        previousNodes.put(dest, source);
                    }
                }
            }
        }

        // Check for negative cycles
        for (String source : graph.graph.keySet()) {
            for (Edge edge : graph.getEdges(source)) {
                String dest = edge.getDestination();
                double distance = edge.getDistance();
                if(times.get(edge.getDestination()) == null){
//                        return "there is no such path";
                    continue;
                }

                if (times.get(source) + distance < times.get(dest)) {
                    return null;
                }
            }
        }

        // Reconstruct the path
        List<String> path = new ArrayList<>();
        String currentNode = destination;
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = previousNodes.get(currentNode);
        }
        Collections.reverse(path);

        double newDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String s = path.get(i);
            String d = path.get(i + 1);
            newDistance += graph.getEdge(s, d).getDistance();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("time", (int) (times.get(destination) * 60));
        result.put("path", path);
        result.put("distance", newDistance);
//        System.out.println(result.get("distance"));
        if (transType.equalsIgnoreCase("walk") || transType.equalsIgnoreCase("bicycle")) {
            result.put("traffic", 0);
        }

        //result.put("traffic", arr);

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

//        System.out.println("belllman time - "+result.get("time"));


        //  printTable(total, transType, arr, (double)result.get("distance"));

        return result;
    }



}