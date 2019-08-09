package SelfLearning2;

import network3.Network;
import cluster2.Cluster;

import matrix.Matrix;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Self-Learning Neural-network 2
 * Focuses more on the centroid being trained
 * Works with batches
 * Starts off from scratch
 * If two centroids with their radius overlap, the clusters can be combined
 *
 */
public class SLNN {

    private ArrayList<Cluster> groups;
    private double influenceRadius;
    private Network nn;
    private double [][] output;

    public SLNN (int numInputs, double threshhold, double influenceRadius){
        groups = new ArrayList<Cluster>();
        this.influenceRadius = influenceRadius;
        this.nn = new Network(numInputs, threshhold);
    }


    //helper functions

    //TODO: when combining clusters, choose the cluster with more larger density to become parent cluster
    private void mergeClusters(Cluster input){
        Iterator itr = groups.listIterator();
        while(itr.hasNext()){
            Cluster cluster = (Cluster)itr.next();
            if(input.merge(cluster)){
                //System.out.println("Success at " + i);
                itr.remove();
                itr = groups.listIterator();
            }
        }
        groups.add(input);
    }

    private void learnCluster(Cluster cluster, int id){
        nn.backpropagate(Matrix.transpose(Matrix.convertTo2D(cluster.getCentroid())), Matrix.getColumn(output, id));
    }

//    private boolean checkScore(int clusterId, double upperBound, double lowerBound){
//
//    }

    public double calculateAverageScore(Cluster cluster, int id){
        double [][] score = nn.feedForward(Matrix.transpose(Matrix.convertTo2D(cluster.getCentroid())));
        double avgScores = score[id][0];

        return avgScores;
    }

    private void autoTrain(){
        output = Matrix.identityMatrix(groups.size());
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

        Cluster cluster = new Cluster(input, influenceRadius);

        int [] scores = nn.getOutputNeuronScores(Matrix.transpose(Matrix.convertTo2D(input)));
        if(scores != null){
            for(int i : scores){
                Cluster candidate = groups.get(i);
                candidate.merge(cluster);
            }
        }
        nn.trainNewData();
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






}
