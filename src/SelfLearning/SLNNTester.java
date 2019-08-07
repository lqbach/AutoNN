package SelfLearning;

import cluster.Cluster;
import cluster.Point;
import matrix.Matrix;

import java.util.ArrayList;

public class SLNNTester {

    public static void main(String args []){

        //Create all inputs first
        double [][] redInput = {
                {0.952, 0.207, 0.313},
                {0.952, 0.207, 0.231},
                {0.831, 0.090, 0.031},
                {0.952, 0.227, 0.168},
                {1, 0.121, 0.333},
                {0.886, 0.196, 0.239},
        };
        double [][] blueInput = {
                {0.196, 0.2, 0.886},
                {0.011, 0.019, 0.607},
                {0.149, 0.156, 0.949},
                {0.223, 0.149, 0.949},
                {0.305, 0.247, 0.894},
                {0.247, 0.364, 0.894}
        };

        ArrayList<Point> redPoints = new ArrayList<Point>();
        for(int i = 0; i < redInput.length; i ++){
            double [] coordinate = Matrix.convertTo1D(Matrix.getRow(redInput, i));
            Point point = new Point(coordinate);
            redPoints.add(point);
        }
        Cluster redCluster = new Cluster(redPoints);

        ArrayList<Point> bluePoints = new ArrayList<Point>();
        for(int i = 0; i < blueInput.length; i ++){
            double [] coordinate = Matrix.convertTo1D(Matrix.getRow(blueInput, i));
            Point point = new Point(coordinate);
            bluePoints.add(point);
        }
        Cluster blueCluster = new Cluster(bluePoints);

        ArrayList<Cluster> clusters = new ArrayList<Cluster>();
        clusters.add(redCluster);
        clusters.add(blueCluster);

        SLNN slnn = new SLNN(clusters);
        slnn.autoTrain();



        //testing with feedforward inputs

        double [] testRed = {0.901, 0.078, 0.078};
        System.out.println(Matrix.print(slnn.feedforward(testRed)));
        System.out.println("----------------");

        double [] testBlue = {0.078, 0.270, 0.901};
        System.out.println(Matrix.print(slnn.feedforward(testBlue)));
        System.out.println("----------------");

        double [] testYellow = {0.819, 0.901, 0.078};
        System.out.println(Matrix.print(slnn.feedforward(testYellow)));
        System.out.println("----------------");

        double [] testGreen = {0.078, 0.901, 0.278};
        System.out.println(Matrix.print(slnn.feedforward(testGreen)));
        System.out.println("----------------");


        //testing new data

        System.out.println("Learning new red color: ");
        slnn.learn(testRed);
        slnn.autoTrain();
        System.out.println(Matrix.print(slnn.feedforward(testRed)));
        System.out.println("----------------");

        System.out.println("Learning new green color: ");
        slnn.learn(testGreen);
        slnn.autoTrain();
        System.out.println(Matrix.print(slnn.feedforward(testGreen)));
        System.out.println("----------------");

        System.out.println("Re testing test points");

        System.out.println(Matrix.print(slnn.feedforward(testRed)));
        System.out.println("----------------");

        System.out.println(Matrix.print(slnn.feedforward(testBlue)));
        System.out.println("----------------");

        System.out.println(Matrix.print(slnn.feedforward(testYellow)));
        System.out.println("----------------");

        System.out.println(Matrix.print(slnn.feedforward(testGreen)));
        System.out.println("----------------");

        System.out.println("Calculating average scores");

        System.out.println("Calculate average score for red cluster");
        System.out.println(slnn.calculateAverageScore(redCluster, 0));
        System.out.println("----------------");

        System.out.println("Calculate average score for blue cluster");
        System.out.println(slnn.calculateAverageScore(blueCluster, 1));
        System.out.println("----------------");





    }

}
