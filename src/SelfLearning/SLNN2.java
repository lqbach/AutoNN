package SelfLearning;

import network2.Network2;
import cluster.Cluster;
import cluster.Point;
import matrix.Matrix;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * Self-Learning Neural-network 2
 * Focuses more on the centroid being trained
 * Works with batches
 * Starts off from scratch
 * If two centroids with their radius overlap, the clusters can be combined
 *
 */
public class SLNN2 {

    private ArrayList<Cluster> groups;
    private Network2 nn;

    private int batchSize;
    private int currentBatchSize = 0;

    private double influenceRadius;
    private boolean clusteringChanged;
    private double [][] output;
    private ArrayList<Point> batch;

    public SLNN2 (double influenceRadius, int batchSize){
        this.influenceRadius = influenceRadius;
        this.batchSize = batchSize;
        groups = new ArrayList<Cluster>();
        batch = new ArrayList<Point>(batchSize);
    }

    //helper methods
    private void learnCluster(Cluster cluster, int id){
        for(Point point : cluster.getPoints()){
            //System.out.println(Arrays.deepToString(Matrix.convertTo2D(point.getCoordinates())));
            nn.backprop(Matrix.transpose(Matrix.convertTo2D(point.getCoordinates())), Matrix.getColumn(output, id));
        }
    }

    public double calculateAverageScore(Cluster cluster, int id){
        double avgScores = 0;
        int numPoints = 0;
        for(Point point : cluster.getPoints()){
            double [][] score = nn.feedforward(Matrix.transpose(Matrix.convertTo2D(point.getCoordinates())));
            avgScores += score[id][0];
            numPoints ++;
        }
        return avgScores/numPoints;
    }

    /** Goes through all the groups and trains each group once
     *
     */
    public void train(int n){
        for(int i = 0; i < n; i ++) {
            int id = 0;
            for(Cluster cluster : groups){
                learnCluster(cluster, id);
                id ++;
            }
        }
    }

    /**
     * autoTrain should train the groups until output cells are given a score of .95 or above to maximize
     * score outputs
     */

    public void autoTrain(){
        nn = new Network2(groups.get(0).getDimension(), groups.size());
        int succ = 0;
        while(true){
            int id = 0;
            for(Cluster cluster : groups){
                learnCluster(cluster, id);
                if(calculateAverageScore(cluster, id) > .95){
                    succ ++;
                }
                else{
                    succ = 0;
                }
                id ++;
            }
            if(succ == groups.size()){
                break;
            }
        }
    }

    /**
     * Learning process:
     *    Accepts some input, should be a column array
     *    Either create new cluster to learn or put it inside some other cluster
     *    If a new cluster is created, the output must be changed to accomadate this,
     *    and relearning must occur.
     *    Keep track of radius! We will test two scenarios:
     */

    public void learn(double [] input){
        Point inputPoint = new Point(input);
        Cluster inputCluster = new Cluster(inputPoint, influenceRadius);

        Iterator itr = groups.iterator();
        while(itr.hasNext()){
            Cluster cluster = (Cluster)itr.next();
            if(cluster.inCluster(inputCluster)){
                inputCluster.combineCluster(cluster);
                itr.remove();
            }
        }
        groups.add(inputCluster);

//        currentBatchSize ++;
//        if(currentBatchSize > batchSize){
//            output = Matrix.identityMatrix(groups.size());
//            autoTrain();
//            currentBatchSize = 0;
//        }
    }

    public double [][] feedforward(double [] input){
        return nn.feedforward(Matrix.transpose(Matrix.convertTo2D(input)));
    }

    public String printWeights(){
        return nn.getWeightsHidden();
    }

    public String printBiases(){
        return nn.getBiasesHidden();
    }


    public int getNumberOfClusters(){
        return groups.size();
    }




}
