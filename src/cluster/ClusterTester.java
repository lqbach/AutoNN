package cluster;

import java.util.ArrayList;
import java.util.Arrays;

public class ClusterTester {

    public static void main(String args[]){
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

        ArrayList<Point> redArray = new ArrayList<Point>();

        for(double [] input : redInput){
            Point point = new Point(input);
            redArray.add(point);
        }

        ArrayList<Point> blueArray = new ArrayList<Point>();

        for(double [] input : blueInput){
            Point point = new Point(input);
            blueArray.add(point);
        }

        Cluster redCluster = new Cluster(redArray);

        Cluster blueCluster = new Cluster(blueArray);

        //{0.890}, {0.984}, {0.074}
        Point yellowPoint = new Point(new double[]{.890, .984, .074});
        Point redPoint = new Point(new double[]{0.933, 0.286, 0.286});

        System.out.println(redCluster.inCluster(yellowPoint));
        System.out.println(blueCluster.inCluster(yellowPoint));
        System.out.println(redCluster.inCluster(redPoint));
    }


}
