package SL3Experiments;

import cluster4.Cluster;
import matrix.Matrix;
import SelfLearning3.SLNN;

import java.util.Arrays;

public class Experiment1 {

    public static void main(String args []){

        SLNN nn = new SLNN(3, 3, .5, 2, .2);

        double [][] inputMatrix =  {
                {0.305, 0.247, 0.894},      //blue
                {0.952, 0.207, 0.313},      //red
                {0.952, 0.227, 0.168},      //red
                {0.223, 0.149, 0.949},      //blue
                {1, 0.121, 0.333},          //red
                {0.886, 0.196, 0.239},      //red
                {0.949, 0.937, 0.227},      //yellow
                {0.792, 0.043, 0.854},      //purple
                {0.196, 0.2, 0.886},        //blue
                {0.650, 0.035, 0.701},      //purple
                {0.952, 0.207, 0.231},      //red
                {0.835, 0.043, 0.854},      //purple
                {1, 0.988, 0.341},          //yellow
                {0.764, 0.035, 0.827},      //purple
                {0.149, 0.156, 0.949},      //blue
                {0.772, 0.066, 0.831},      //purple
                {0.247, 0.364, 0.894},      //blue
                {0.945, 1, 0.341}           //yellow
        };

        for(int i = 0; i < inputMatrix.length; i ++){
//            if(nn.getNumberOfPoints() != i){
//                System.out.println("program ended forced");
//                return;
//            }
            //System.out.println("At row : " + i);
            System.out.println("Testing: " + Matrix.print(Matrix.getRow(inputMatrix, i)));
            nn.learn(Matrix.convertTo1D(Matrix.getRow(inputMatrix, i)));

            //System.out.println(nn.printWeights());
            System.out.println("Network currently has " + nn.getNumberOfClusters() + " clusters");

            System.out.println("-----------");
        }

        System.out.println("Number of clusters: " + nn.getNumberOfClusters());
        System.out.println("Number of outliers: " + nn.getNumberOfOutliers());

        for(Cluster c : nn.getClusters()){
            System.out.println("----------------");
            for(double [] point : c.getPoints()){
                System.out.println(Arrays.toString(point));
            }
        }
        //System.out.println(nn.getClusters());


    }

}
