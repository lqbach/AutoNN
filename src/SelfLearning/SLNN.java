package SelfLearning;

import network2.Network2;
import cluster.Cluster;
import cluster.Point;
import matrix.Matrix;

import java.util.ArrayList;

/*
 * Self-Learning Neural-network
 * This network will be as basic as possible. The number of group clusters will be the number of
 * output neurons in the output layer, each group will correspond to its own output neuron.
 *
 *
 */
public class SLNN {

    private ArrayList<Cluster> groups;
    private Network2 nn;

    private double [][] output;

    public SLNN(ArrayList<Cluster> groups){
        this.groups = groups;
        int numInput = groups.get(0).getDimension();
        int numOutput = groups.size();

        output = Matrix.identityMatrix(groups.size());
        nn = new Network2(numInput, numOutput);
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
        double maxRadius = 0;

        for(Cluster cluster : groups){
            if(cluster.inCluster(inputPoint)){
                cluster.addPoint(inputPoint);
                return;
            }
            if(cluster.getRadius() > maxRadius){
                maxRadius = cluster.getRadius();
            }
        }
        //create a new cluster
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(inputPoint);
        Cluster cluster = new Cluster(points, maxRadius);
        groups.add(cluster);
        output = Matrix.identityMatrix(groups.size());
        nn.trainNewData();
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
