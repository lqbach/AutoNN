package cluster4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DBSCAN {

    /**
     * a radius to define the neighborhood
     */
    private double radius;

    /**
     * minimum number of points to be considered a cluster
     */
    private int minPoints;

    /**
     * the groupings of clusters
     */
    private ArrayList<Cluster> groups;

    /**
     * extra noise that currently don't belong to any clusters
     */
    private ArrayList<double []> outliers;

    /**
     * keeps track of points to determine if either an outlier or neighbor
     */
    private enum Status{
        OUTLIER,
        NEIGHBOR
    }

    /**
     *
     * @param radius maximum radius for the classification of a neighberhood
     * @param minPoints number of points a cluster must have for one to be created
     */
    public DBSCAN (double radius, int minPoints){
        if(radius < 0 || minPoints < 0){
            throw new RuntimeException("Radius or number of points are not positive");
        }

        groups = new ArrayList<>();
        outliers = new ArrayList<>();

        this.radius = radius;
        this.minPoints = minPoints;
    }

    public double getRadius(){
        return radius;
    }

    public int getMinPoints(){
        return minPoints;
    }

    public ArrayList<Cluster> getGroups(){
        return groups;
    }

    public ArrayList<double []> getOutliers(){
        return outliers;
    }

    public int getNumGroups(){
        return groups.size();
    }

    /**
     * Calculates the distance between two points, throws an exception if dimensions are not equal
     * @param point1 a point
     * @param point2 another point
     * @return
     */
    private double calcDistance(double [] point1, double[] point2){
        if(point1.length != point2.length){
            throw new RuntimeException("Coordinates are not in the same dimension for the cluster.");
        }

        double sum = 0;
        for(int i = 0; i < point1.length; i ++){
            sum += Math.pow(point1[i]-point2[i], 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * This function is called in conjunction with the neural network. For the score that the neural network produces,
     * it should be added to the corresponding cluster. However, if it cannot be added to the cluster using DBSCAN, then
     * this means the network is too immature and there is underfitting.
     *
     * @param candidate the input that scored a high score and should be tested in the cluster
     * @param clusterIDs the cluster where the candidate could be put into. Cluster ID should be negative if network is not available yet to feedforward.
     * @return an int array containing the clusters were merged from the groups
     *          - returns an array of positive numbers for each cluster deleted
     *          - returns a negative singleton if new data must be trained
     *          - returns nothing if clusters remained untouched
     */
    public int[] addToCluster(double [] candidate, int [] clusterIDs){
        //TODO: add a negative comparator
//        if(groups.size() != 0 && clusterID >= groups.size()){
//            throw new RuntimeException("ID is greater than the number of groups");
//        }

        if(clusterIDs != null){
            for(int clusterID : clusterIDs){
                if(clusterID >= 0){
                    for(double [] point : groups.get(clusterID).getPoints()){

                        //join point into cluster
                        if(calcDistance(point, candidate) <= this.radius){
                            Cluster currentCluster = groups.get(clusterID);
                            currentCluster.addPoint(candidate);

                            mergeNeighboringOutliers(candidate, currentCluster);
                            return mergeNeighboringClusters(candidate, currentCluster);

                        }
                    }
                }
            }
        }


        if(addToOutliers(candidate)){
            return new int [] {-1};
        }

        return null;

    }

    /**
     * Merges the cluster with all surrounding outliers using the point parameter as a point of focus. Any neighbors
     * of point will be merged with cluster.
     *
     * @param point a point referencing cluster
     * @param cluster the cluster that contains point
     */
    private void mergeNeighboringOutliers(double [] point, Cluster cluster){
        Iterator outlierItr = outliers.iterator();
        while(outlierItr.hasNext()){
            double [] candidate = (double []) outlierItr.next();
            if(candidate != point && calcDistance(candidate, point) <= this.radius){
                cluster.addPoint(candidate);
                outlierItr.remove();
            }
        }
    }

    /**
     * Merges with any surrounding clusters, once again based on point parameter as a point of focus.
     * @param point a point referencing the cluster
     * @param cluster the cluster that contains the point parameter
     * @return an int array containing all the clusters that were merged
     */
    private int [] mergeNeighboringClusters(double [] point, Cluster cluster){
        Iterator groupsItr = groups.iterator();
        ArrayList<Integer> mergedClusters = new ArrayList<>();

        int counter = 0;
        while(groupsItr.hasNext()){
            Cluster candidate = (Cluster) groupsItr.next();
            if(candidate == cluster){
                counter ++;
                continue;
            }
            if(checkClusterNeighbor(point, candidate)){
                cluster.merge(candidate);
                groupsItr.remove();
                mergedClusters.add(counter);
            }
            counter ++;
        }

        int [] arr = new int[mergedClusters.size()];
        for(int i = 0; i < mergedClusters.size(); i ++){
            arr[i] = mergedClusters.get(i);
        }
        return arr;
    }

    /**
     * Helper function used to check if point is inside cluster
     * @param point any point
     * @param cluster a cluster
     * @return a boolean to verify if point is inside cluster
     */
    private boolean checkClusterNeighbor(double [] point, Cluster cluster){
        for(double [] clusterPoint : cluster.getPoints()){
            if(calcDistance(clusterPoint, point) <= this.radius){
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a point to outliers. Checks to see if any outliers can form a cluster. If it does, a cluster is formed.
     * @param point a point to be added to outliers
     * @return returns true if a cluster is formed. Returns false if only added to outlier group
     */
    private boolean addToOutliers(double [] point){
        ArrayList<double []> neighbors = getOutlierNeighbors(point);
        if(neighbors.size() >= minPoints){
            Cluster cluster = new Cluster(point);
            for(double [] n : neighbors){
                cluster.addPoint(n);
                outliers.remove(n);
            }
            groups.add(cluster);
            return true;
        }
        outliers.add(point);
        return false;
    }

    /**
     * Helper function that retrieves all nearby neighbors that are outliers
     * @param point a point
     * @return an ArrayList containing all neighbors
     */
    private ArrayList<double []> getOutlierNeighbors(double [] point){
        ArrayList<double []> neighbors = new ArrayList<double []>();
        for(double [] candidate : this.outliers){
            if(candidate != point && calcDistance(candidate, point) <= this.radius){
                neighbors.add(candidate);
            }
        }
        return neighbors;
    }

}
