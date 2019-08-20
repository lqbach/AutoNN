package cluster4;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Implements DBSCAN  Clustering
 */
public class Cluster {

    /**
     * points contained in cluster (inside the neighborhood)
     */
    private ArrayList<double []> points;

    private int dimension;

    /**
     * center of cluster
     */
    private double[] centroid;

    public Cluster(double [] center){
        centroid = center;
        dimension = center.length;
        points = new ArrayList<double []> ();
        points.add(center);
    }

    /**
     * Adds a point to the cluster
     * @param point a point to be added to the cluster
     */
    public void addPoint(double [] point){
        if(point.length != dimension){
            throw new RuntimeException("Dimension are invalid!");
        }
        points.add(point);
    }

    public ArrayList<double []> getPoints(){
        return points;
    }

    public void merge(Cluster cluster){
        for(double [] point : cluster.points){
            this.points.add(point);
        }
    }

    public double[] getCentroid(){
        return centroid;
    }

}
