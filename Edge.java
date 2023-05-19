
package final_project;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Edge {
    String pointA;
    String pointB;
    double distance;
    int velocity;
    static int traffic;
    static Set<String> allDestinations = new HashSet<>();

    public Edge(String pointA, String pointB, double distance, int velocity, int traffic){
        this.pointA = pointA;
        this.pointB = pointB;
        this.distance = distance;
        this.velocity = velocity;
        this.traffic = traffic;
    }

    public Edge(){
    }

    public double getDistance(){

        return this.distance;
    }



    public int getVelocity(){
        return velocity;
    }

    public String getSource(){
        return pointA;
    }

    public String getDestination(){
        return pointB;
    }



    public static int getCurrentTraffic(int time){
        try {
            File file = new File("/Users/Дони/IdeaProjects/algo_project/src/final_project/vertex");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while((line = bufferedReader.readLine()) != null){
                String[] eachLine = line.split(" : ");
                String[] collect = eachLine[1].split(",");
                traffic = Integer.parseInt(collect[time]);
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return traffic;
    }

    public static int setVelocity(String s){
        if(s.equalsIgnoreCase("bus")){
            return 60;
        }
        if(s.equalsIgnoreCase("walk")){
            return 6;
        }
        if(s.equalsIgnoreCase("bicycle")){
            return 15;
        }
        return 65;

    }

    @Override
    public String toString() {
        return pointA + " -> " + pointB + " (distance: " + distance + ", velocity: " + velocity + ", traffic: " + traffic + ")";
    }
}