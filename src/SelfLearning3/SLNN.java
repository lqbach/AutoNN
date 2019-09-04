package SelfLearning3;

import network4.Network;
import cluster4.DBSCAN;
import cluster4.Cluster;
import matrix.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Self Learning Neural Network 3
 * Uses DBSCAN algorithm as a clustering model
 *
 */
public class SLNN {

    private Network nn;
    private DBSCAN dbscan;
    private double [][] output;

    public SLNN(int numInputNeurons, int numHiddenNeurons, double threshold, int minPts, double initRadius){

        nn = new Network(numInputNeurons, numHiddenNeurons, threshold);
        dbscan = new DBSCAN(initRadius, minPts);

    }

    public void learn(double [] input){
        int [] scores = nn.getOutputNeuronScores(Matrix.transpose(Matrix.convertTo2D(input)));
        int [] feedback = dbscan.addToCluster(input, scores);

        /*3 possibilities for feedback:
            - returns array of positive numbers for clusters that need to be deleted
            - returns singleton array of negative number indicating new data must be trained because a cluster has been
                added
            - returns nothing, meaning no clusters have changed
        */

        //System.out.println(Arrays.toString(feedback));

        if(feedback == null || feedback.length == 0){
            return;
        }
        else if(feedback[0] == -1){
            nn.trainNewData();
        }
        else{
            deleteOutputs(feedback);
        }

        train(1000);


    }

    public double [][] feedforward(double [] input){
        return nn.feedforward(Matrix.transpose(Matrix.convertTo2D(input)));
    }

    public ArrayList<Cluster> getClusters(){
        return dbscan.getGroups();
    }

    public int getNumberOfClusters(){
        return dbscan.getNumGroups();
    }

    public int getNumberOfOutliers(){
        return dbscan.getOutliers().size();
    }

    private void deleteOutputs(int [] array){
        Arrays.sort(array);
        reverse(array);
        for(int i : array){
            nn.deleteOutput(i);
        }
    }

    private void reverse(int[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        int tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    private void train(int n){
        ArrayList<Cluster> groups = dbscan.getGroups();
        output = Matrix.identityMatrix(groups.size());
        for(int i = 0; i < n; i ++) {
            int id = 0;
            for(Cluster cluster : groups){
                learnCluster(cluster, id);
                id ++;
            }
        }
    }

    private void learnCluster(Cluster cluster, int clusterID){
        for(double [] input : cluster.getPoints()){
            nn.backprop(Matrix.transpose(Matrix.convertTo2D(input)), Matrix.getColumn(output, clusterID));
        }
    }

}
