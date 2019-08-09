import SelfLearning.SLNN2;
import cluster.Cluster;
import cluster.Point;
import matrix.Matrix;

import java.util.ArrayList;

public class Experiment {

    /**
     *
     * Experiment 1:
     *  In this experiment, we will test a bunch of RGB inputs
     *
     *
     */
    public static void main(String args []){

        SLNN2 nn = new SLNN2(.1, 20);

        double [][] inputMatrix =  {
                //red
                {0.952, 0.207, 0.313},
                {0.952, 0.207, 0.231},
                {0.831, 0.090, 0.031},
                {0.952, 0.227, 0.168},
                {1, 0.121, 0.333},
                {0.886, 0.196, 0.239},
                //blue
                {0.196, 0.2, 0.886},
                {0.011, 0.019, 0.607},
                {0.149, 0.156, 0.949},
                {0.223, 0.149, 0.949},
                {0.305, 0.247, 0.894},
                {0.247, 0.364, 0.894}
        };

        for(int i = 0; i < inputMatrix.length; i ++){
            //System.out.println("At row : " + i);
            nn.learn(Matrix.convertTo1D(Matrix.getRow(inputMatrix, i)));
        }

        System.out.println(nn.getNumberOfClusters());

    }

}
