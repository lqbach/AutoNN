package cluster;

import java.util.ArrayList;

public class Cluster {

    private ArrayList<Point> points;
    private int dimension;
    private Point centroid;
    private double radius;

    public Cluster(ArrayList<Point> points){
        dimension = points.get(0).getDimension();
        for(Point point : points){
            if(point.getDimension() != dimension){
                throw new RuntimeException("Coordinates are not in the same dimension.");
            }
        }
        this.points = points;
        calculateCentroid();
        calculateMaxRadius();
    }

    public void addPoint(Point point){
        if(point.getDimension() != dimension){
            throw new RuntimeException("Coordinates are not in the same dimension.");
        }
        points.add(point);

        //TODO: make calculateCentroid() O(1) time instead of O(n)
        calculateCentroid();
    }

    public boolean inCluster(Point p){
        if(radius > p.calcDistance(centroid)){
            return true;
        }
        return false;
    }

    private void calculateCentroid(){
        double [] centroidCoor = new double [dimension];
        for(Point point : points){
            for(int i = 0; i < dimension; i ++){
                centroidCoor[i] += point.getCoordinateAt(i);
            }
        }
        for(int i = 0; i < dimension; i ++){
            centroidCoor[i] /= dimension;
        }

        Point centroid = new Point(centroidCoor);
        this.centroid = centroid;
    }

    private void calculateMaxRadius(){
        double maxRadius = 0;
        for(Point point : points){
            double dist = centroid.calcDistance(point);
            if(dist >= maxRadius){
                maxRadius = dist;
            }
        }
        radius = maxRadius;
    }


}
