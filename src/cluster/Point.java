package cluster;

import java.util.Arrays;

public class Point {
    private double [] coordinates;

    public Point(double [] coordinates){
        this.coordinates = coordinates;
    }

    public String toString(){
        return Arrays.toString(coordinates);
    }

//    public double[] getCoordinates(){
//        return coordinates;
//    }

    public double getCoordinateAt(int i ){
        if(i >= coordinates.length){
            throw new RuntimeException("Dimension out of bounds");
        }
        return coordinates[i];
    }

    public int getDimension(){
        return coordinates.length;
    }

    public double calcDistance(Point p){
        if(p.coordinates.length != coordinates.length){
            throw new RuntimeException("Coordinates are not in the same dimension.");
        }

        double sum = 0;
        for(int i = 0; i < coordinates.length; i ++){
            sum += Math.pow(p.coordinates[i]-coordinates[i], 2);
        }
        return Math.sqrt(sum);
    }

}
