package final_project;


import java.io.*;
import java.util.*;

public class Main {
    static ArrayList<String> src_dest;
    static String trasport;
    static int time;

    public static void main(String[] args) throws IOException {
        long startTime_l = System.currentTimeMillis();

        Graph gr = new Graph();

        File file = new File("/Users/Дони/IdeaProjects/algo_project/src/final_project/vertex");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String[] eachLine = line.split(" : ");
            String[] collect = eachLine[0].split(" - ");
            String source = collect[0];
            String destination = collect[1];
            Edge.allDestinations.add(destination);
            gr.addEdge(source.toLowerCase(), destination.toLowerCase(), Double.parseDouble(collect[2]), Integer.parseInt(collect[3]), Edge.getCurrentTraffic(15));
        }

        bufferedReader.close();
        fileReader.close();


        Edge.allDestinations.removeAll(gr.graph.keySet());
        if (!Edge.allDestinations.isEmpty()) {
            for (String lasts : Edge.allDestinations) {
                gr.addEdge(lasts, null, 0, 0, 0);
            }
        }


        tries();

        int time = 0;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter time (hour:min)");
        String enterTime = in.next();
        int hour = Integer.parseInt(enterTime.split(":")[0]);
        System.out.println();
        int mins = Integer.parseInt(enterTime.split(":")[1]);
        if(mins < 30){
            time = hour;
        }
        if(mins > 30){
            time = hour+1;
        }


//        System.out.println("Dijkstra for " + src_dest.get(0) + " " + src_dest.get(1) + DijkstraAlgorithm.shortestPath(gr, src_dest.get(0), src_dest.get(1), 60, trasport, time));
//        System.out.println("BellmanFord for" + src_dest.get(0) + " " + src_dest.get(1) + BellmanFord.shortestPath(gr, src_dest.get(0), src_dest.get(1), 60, trasport, time));
        long endTime_l = System.currentTimeMillis();
        //  System.out.println("time of executing code : " + (endTime_l - startTime_l) + " millis");
        //long t_d = (long) d.get("time");


        int velocity = Edge.setVelocity(trasport);
        Map<String, Object> dijk = (Map<String, Object>) DijkstraAlgorithm.dijkstraPath(gr, src_dest.get(0), src_dest.get(1), velocity, trasport, time);
        HashMap<String, Object> bell = (HashMap<String, Object>) BellmanFord.bellmanPath(gr, src_dest.get(0), src_dest.get(1), velocity, trasport, time);


        List<String> pathb = (List<String>) bell.get("path");
        int i = 0;
        System.out.println("BellmanFord path: ");
        while (i < pathb.size()) {
            if (i == pathb.size() - 1) {
                System.out.print(pathb.get(i));
            } else {
                System.out.print(pathb.get(i) + " -- " + "(edge distance - " + gr.getEdge(pathb.get(i), pathb.get(i + 1)).getDistance() + ")-->");
            }
            i++;
        }
        System.out.println();
        System.out.println();

        List<String> pathd = (List<String>) dijk.get("path");
        int j=0;
        System.out.println("Dijkstra path: ");
        while (j < pathd.size()) {
            if (j == pathd.size() - 1) {
                System.out.print(pathd.get(j));
            } else {
                System.out.print(pathd.get(j) + " -- " + "(edge distance - " + gr.getEdge(pathd.get(j), pathd.get(j + 1)).getDistance() + ")-->");
            }
            j++;
        }
        System.out.println();
        System.out.println();


        printTable(dijk.get("time of execution"), bell.get("time of execution"), trasport, (int) dijk.get("traffic"), (double) dijk.get("distance"), (double) bell.get("distance"),newTime(enterTime, hour, mins, (int)dijk.get("time")) ,newTime(enterTime, hour, mins, (int)bell.get("time")));
        //System.out.println(gr.graph.keySet());
        print_vetrTable(gr);
//        String travelTime_d = Integer.toString((int) dijk.get("time"));
//        String travelTime_b = Integer.toString((int) bell.get("time"));


//        System.out.println(newTime(hour, mins, (int)dijk.get("time")));
//        System.out.println( newTime(hour, mins, (int)bell.get("time")));

       // System.out.println("Travel times: " + enterTime + " - " + newTime(hour, mins, timeToAdd));

    }



    public static String newTime(String start, int hours, int mins, int timeToAdd){
        mins += timeToAdd;
        hours += mins/60;
        mins %= 60;
        hours %= 24;
        String a = String.format("%02d:%02d", hours, mins);
        return String.format(start+" - "+ a);
    }

//15 points - A table with columns:
//    Vertex name
//    Shortest distance value to SDU
//    Edges of the path till SDU
    public static void print_vetrTable(Graph graph){

        Formatter formatter = new Formatter(System.out);
        formatter.format("%-30s |", "| Vertex name"); //отступ с выраниванием влево
        formatter.format("%-35s |", "Shortest distance value to SDU");//отступ с выравнивание вправо
        formatter.format("%-20s ", "Edges of the path till SDU");

        System.out.print("\n________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
        System.out.println();
        for(String vertex: graph.graph.keySet()){
            Map<String, Object> dijk = (Map<String, Object>) DijkstraAlgorithm.dijkstraPath(graph, vertex, "sdu",60, trasport, time );
            List<String> path = (List<String>) dijk.get("path");
            double dist = (double) dijk.get("distance");
            formatter.format("%-30s |", vertex);
            formatter.format("%-35s |", dist+" km");
            int j = 0;
            while (j < path.size()) {
                if (j == path.size() - 1) {
                    System.out.print(path.get(j));
                } else {
                    formatter.format("%-20s ",path.get(j) + " -- " + "(edge distance - " + graph.getEdge(path.get(j), path.get(j + 1)).getDistance() + ")-->");
                }
                j++;
            }


         //   formatter.format("%-10s |", "");
            System.out.println();
        }
    }


    public static void printTable(Object total_d,Object total_b, String transport, int traf, double dist_d, double dist_b, String time_d, String time_b) {
        //Dijkstra         |  TIME  |  TRANSPORT  |  TRAFFIC CONGESTION

        Formatter formatter = new Formatter(System.out);
        formatter.format("%-20s |", "| Algorithm"); //отступ с выраниванием влево
        formatter.format("%-20s |", "TIME OF EXECUTION");//отступ с выравнивание вправо
        formatter.format("%-20s |", "TRANSPORT");
        formatter.format("%-20s |", "TRAFFIC CONGESTION"); //отступ с выраниванием влево
        formatter.format("%-20s |", "DISTANCE");
        formatter.format("%-20s |", "DATETIME");


        System.out.print("\n____________________________________________________________________________________________________________________________________");
        System.out.println();
        formatter.format("%-20s |", "| Dijkstra");
        formatter.format("%-20s |", total_d + " ms");
        formatter.format("%-20s |", transport);
        formatter.format("%-20s |", traf);
        formatter.format("%-20s |", dist_d);
        formatter.format("%-20s |", time_d);

        System.out.println();
        formatter.format("%-20s |", "| BellmanFord");
        formatter.format("%-20s |",total_b + " ms");
        formatter.format("%-20s |", transport);
        formatter.format("%-20s |", traf);
        formatter.format("%-20s |", dist_b);
        formatter.format("%-20s |", time_b);



        System.out.println();
        System.out.println();
        System.out.println();

    }


    public static void tries() {
        ArrayList<String> temp = new ArrayList<>();
        String[] info = {"bus", "bicycle", "car", "walk"};

        try {
            File file = new File("/Users/Дони/IdeaProjects/algo_project/src/final_project/vertex");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            Set<String> s = new HashSet<>();

            while ((line = bufferedReader.readLine()) != null) {
                String[] collect = line.split(" - ");
                for (int i = 0; i < collect.length; i++) {

                    if (!s.contains(collect[0])) {
                        s.add(collect[0]);
                        temp.add(collect[0]);
                    }
                    if (!s.contains(collect[1])) {
                        s.add(collect[1]);
                        temp.add(collect[1]);
                    }
                }
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] keys = new String[temp.size()];
        keys = temp.toArray(keys);
//        for (String a : keys) {
//            System.out.println(a);
//        }
        Tries trie = new Tries();
        //Trie_node root = new Trie_node();

        for (int i = 0; i < keys.length; i++) {
            trie.insert(keys[i]);
        }
        String output[] = {"Not present in trie", "Present in trie"};

        src_dest = new ArrayList<>();
        System.out.println("Please choose start point and destination, we will check whether you place is in our graph/tries");

        Scanner in = new Scanner(System.in);
        while (src_dest.size() < 2) {
            System.out.println();
            System.out.println("write first two or more letters/ whole name of station");
            String a = in.next().toLowerCase();
            ArrayList words = trie.findWords(a);

            //System.out.println(trie.findWords(a));

            if (words.isEmpty()) {
                System.out.println(output[0]);

            } else {
                System.out.println("choose correct station name");

                for (int i = 0; i < words.size(); i++) {
                    System.out.println((i + 1) + "  " + words.get(i));
                }
                int choose = in.nextInt();
                src_dest.add((String) words.get(choose - 1));

            }


        }
        System.out.println(src_dest);
//        System.out.println("Now write exact time(in hours 0-24)");
//        time = in.nextInt();
        System.out.println("Which way you prefer to get to the destination: ");
        while (trasport == null) {
            for (int i = 0; i < info.length; i++) {
                System.out.println((i + 1) + " " + info[i]);
            }
            int tr = in.nextInt();
            trasport = info[tr - 1];
        }

    }


}