package SelfLearning2;

import network3.Network;
import cluster3.Cluster;

import matrix.Matrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Self-Learning Neural-network 2
 * Focuses more on the centroid being trained
 * Works with batches
 * Starts off from scratch
 * If two centroids with their radius overlap, the clusters can be combined
 *
 * NOTES:
 * whenever a cluster is added, a new row must be added
 * whenever a cluster is removed, the corresponding row must be removed
 *
 */
public class SLNN {

    private ArrayList<Cluster> groups;
    private double influenceRadius;
    private Network nn;
    private double [][] output;

    public SLNN (int numInputs, double threshold, double influenceRadius){
        this.groups = new ArrayList<Cluster>();
        this.influenceRadius = influenceRadius;
        this.nn = new Network(numInputs, threshold);
    }


    //helper functions

    //TODO: when combining clusters, choose the cluster with more larger density to become parent cluster

    /**
     * Merges the cluster with the groups, and checks if any clusters formed will make larger clusters
     * O(n^2) time, must be improved!
     *
     * @param input takes in a cluster
     * @param n an integer telling us what cluster we need to evaluate
     */
    private boolean mergeCluster(Cluster input, int n){
        Cluster candidate = groups.get(n);
        if(candidate.merge(input)){
            Iterator itr = groups.listIterator();
            int counter = 0;
            while(itr.hasNext()){
                Cluster cluster = (Cluster)itr.next();
                if(cluster != candidate && candidate.merge(cluster)){
                    //remove the cluster and delete its corresponding row from the matrix
                    itr.remove();
                    nn.deleteOutput(counter);
                    //reset counter and iterator to check with the new cluster
                    itr = groups.listIterator();
                    counter = 0;
                }
                else{
                    counter ++;
                }

            }

            //TODO: train the candidate here after combining

            return true;
        }
        return false;
    }

    private void learnCluster(Cluster cluster, int id){
        nn.backpropagate(Matrix.transpose(Matrix.convertTo2D(cluster.getCentroid())), Matrix.getColumn(output, id));
    }

    private boolean checkScore(Cluster cluster, int clusterId, double upperBound, double lowerBound){
        double [][] score = nn.feedForward(Matrix.transpose(Matrix.convertTo2D(cluster.getCentroid())));
//        System.out.println(Matrix.print(score));
        for(int i = 0; i < score.length; i++){
            if(i == clusterId){
                if(score[i][0] < upperBound){
                    return false;
                }
            }
            else{
                if(score[i][0] > lowerBound){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkScore(Cluster cluster, int clusterId, double bound){
        double [][] score = nn.feedForward(Matrix.transpose(Matrix.convertTo2D(cluster.getCentroid())));
//        System.out.println(Matrix.print(score));
        for(int i = 0; i < score.length; i++){
            if(i == clusterId){
                if(score[i][0] < bound){
                    return false;
                }
            }
            else{
                if(score[i][0] > bound){
                    return false;
                }
            }
        }
        return true;
    }

    public double calculateAverageScore(Cluster cluster, int id){
        double [][] score = nn.feedForward(Matrix.transpose(Matrix.convertTo2D(cluster.getCentroid())));
        double avgScores = score[id][0];

        return avgScores;
    }

    private void train(int n){
        output = Matrix.identityMatrix(groups.size());
        for(int i = 0; i < n; i ++) {
            int id = 0;
            for(Cluster cluster : groups){
                learnCluster(cluster, id);
                id ++;
            }
        }
    }

    private void autoTrain(){
        output = Matrix.identityMatrix(groups.size());
        int timesTrained = 0;
        int succ = 0;
        while(true){
            int id = 0;

            for(Cluster cluster : groups){
                int numPoints = cluster.getNumPoints();
                if(numPoints < 5){
                    if(checkScore(cluster, id, .60)){
                        succ ++;
                    }
                    else{
                        learnCluster(cluster, id);
                        succ = 0;
                    }
                }

                else if(numPoints < 15){
                    if(checkScore(cluster, id, .70)){
                        succ ++;
                    }
                    else{
                        learnCluster(cluster, id);
                        succ = 0;
                    }
                }

                else if(numPoints < 20){
                    if(checkScore(cluster, id, .85)){
                        succ ++;
                    }
                    else{
                        learnCluster(cluster, id);
                        succ = 0;
                    }
                }

                else{
                    if(checkScore(cluster, id, .90)){
                        succ ++;
                    }
                    else{
                        learnCluster(cluster, id);
                        succ = 0;
                    }
                }

                id ++;
            }
            timesTrained ++;
            if(succ >= groups.size()){
                break;
            }
        }
        System.out.println("Trained " + timesTrained + " times");
    }

    /**
     * Checks neural network to see if it can be categorized. Check the score. There are two outcomes for the score:
     * 1) Object has a high score in an output cell
     *      - verify if object belongs to the corresponding cluster
     *          a) if it belongs, put it into the cluster and tweak centroid accordingly
     *          b) if it doesn't then, a new output cell must be created
     * 2) Object has a low score in all output cells
     *
     * @param input a double array containing input to learn
      */
    public void learn(double [] input){
        Cluster clusterInput = new Cluster(input, influenceRadius);

        int [] scores = nn.getOutputNeuronScores(Matrix.transpose(Matrix.convertTo2D(input)));
        if(scores!=null){
            System.out.println("Score: " + Arrays.toString(scores));
            int shift = 0;
            for(int score : scores){
                if(mergeCluster(clusterInput,score-shift)){
                    shift ++;
                    autoTrain();
                    return;
                }
            }
        }
        groups.add(clusterInput);
        nn.trainNewData();
        //output = Matrix.identityMatrix(groups.size());
        autoTrain();

//        train(1000);
    }

    public double [][] feedForward(double [] input){
        return nn.feedForward(Matrix.transpose(Matrix.convertTo2D(input)));
    }

    public String printWeights(){
        return nn.getWeightsHidden();
    }

    public String printBiases(){
        return nn.getBiasesHidden();
    }

    public double getInfluenceRadius(){
        return influenceRadius;
    }

    public int getNumberOfClusters(){
        return groups.size();
    }

    public String getClusters(){
        String message = "";
        for(Cluster cluster : groups){
            message += cluster.toString();
            message += "\n";
        }
        return message;
    }

    public int getNumberOfPoints(){
        int numPoints = 0;
        for(Cluster c : groups){
            numPoints += c.getNumPoints();
        }
        return numPoints;
    }






}
