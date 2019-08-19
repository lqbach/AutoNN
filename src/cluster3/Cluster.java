package cluster3;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This cluster class will only consist of a centroid and radius
 */
public class Cluster {

    private ArrayList<double[]> points = new ArrayList<double[]>();
    private int numPoints;
    private int dimension;
    private double[] centroid;
    private double radius;

    public Cluster(double [] point, double radius){
        points.add(point);

        this.numPoints = 1;
        this.centroid   = point;
        this.dimension  = centroid.length;
        this.radius     = radius;
    }

    //public getter functions
    public int getDimension() {
        return dimension;
    }

    public double [] getCentroid(){
        return centroid;
    }

    public double getRadius(){
        return radius;
    }

    /**
     * Calculates the distance between two clusters
     *
     * @param cluster some instance of a Cluster
     * @return a double calculating the distance between the centroids of the two clusters
     */
    public double calculateDistance(Cluster cluster){
        if(cluster.dimension != this.dimension){
            throw new RuntimeException("Coordinates are not in the same dimension for the cluster.");
        }

        double sum = 0;
        double [] coordinates = cluster.centroid;
        for(int i = 0; i < coordinates.length; i ++){
            sum += Math.pow(coordinates[i]-this.centroid[i], 2);
        }
        return Math.sqrt(sum);
    }

    public double calculateDistance(double [] coordinates){
        if(coordinates.length != this.dimension){
            throw new RuntimeException("Coordinates are not in the same dimension for the cluster.");
        }

        double sum = 0;
        for(int i = 0; i < coordinates.length; i ++){
            sum += Math.pow(coordinates[i]-this.centroid[i], 2);
        }
        return Math.sqrt(sum);
    }

    //TODO: delete this test code
//    public double [] computeCentroid(){
//        double [] centroidCoor = new double [dimension];
//        for(double [] point : points){
//            for(int i = 0; i < dimension; i ++){
//                centroidCoor[i] += point[i];
//            }
//        }
//        for(int i = 0; i < dimension; i ++){
//            centroidCoor[i] /= dimension;
//        }
//        return centroidCoor;
//    }

    /**
     * Function that merges two clusters depending on whether the radius of one of the clusters completely envelope one
     * of the points.
     * The function must update the following:
     *  - numPoints
     *  - centroid
     *  - radius
     *
     * downsides: radius can blow up very quickly if not careful, we need a better algorithm for finding the radius
     * @param cluster some instance of Cluster
     * @return a boolean indicating whether the clusters are mergeable or not.
     */
    public boolean merge(Cluster cluster){
        if(cluster.dimension != this.dimension){
            throw new RuntimeException("Coordinates are not in the same dimension for the cluster.");
        }

        double distance = this.calculateDistance(cluster);

        if(cluster.radius > distance || this.radius > distance){
            points.addAll(cluster.points);

            double [] centroidSum = new double[cluster.dimension];
            int totalPoints = this.numPoints + cluster.numPoints;

            for(int i = 0; i < cluster.dimension; i ++){
                centroidSum[i] = (this.centroid[i] * this.numPoints) + (cluster.centroid[i] * cluster.numPoints);
                centroidSum[i] /= totalPoints;
                //System.out.println("During merge: " + centroidSum[i]);
            }

            double [] furthestPoint = points.get(0);
            for(double [] point: points){
                if(this.calculateDistance(point) > this.calculateDistance(furthestPoint)){
                    furthestPoint = point;
                }
            }

            double furthestDistance = this.calculateDistance(furthestPoint);
            this.radius = (this.radius > furthestDistance) ? this.radius : furthestDistance;


            //update numPoints
            this.numPoints = totalPoints;
            this.centroid = centroidSum;


            return true;

        }
        return false;
    }

    public String toString(){
        return "Centroid: " + Arrays.toString(centroid) + "\nRadius: " + radius;
    }

    public int getNumPoints(){
        return numPoints;
    }
}
