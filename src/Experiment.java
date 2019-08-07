import SelfLearning.SLNN;
import cluster.Cluster;
import cluster.Point;
import matrix.Matrix;

import java.util.ArrayList;

public class Experiment {

    public static void main(String args []){

        //Create all inputs first
        double [] redInput = {0.952, 0.207, 0.313};

        ArrayList<Point> redPoints = new ArrayList<Point>();
        redPoints.add(new Point(redInput));

        Cluster redCluster = new Cluster(redPoints);

        ArrayList<Cluster> clusters = new ArrayList<Cluster>();
        clusters.add(redCluster);

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







    }

}
