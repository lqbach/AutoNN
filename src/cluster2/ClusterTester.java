package cluster2;

import matrix.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ClusterTester {
    public static void main(String args []){
        double [][] inputMatrix =  {
//                //red
//                {0.952, 0.207, 0.313},
//                {0.952, 0.207, 0.231},
//                //{0.831, 0.090, 0.031},
//                {0.952, 0.227, 0.168},
//                {1, 0.121, 0.333},
//                {0.886, 0.196, 0.239},
//                //blue
//                {0.196, 0.2, 0.886},
//                //{0.011, 0.019, 0.607},
//                {0.149, 0.156, 0.949},
//                {0.223, 0.149, 0.949},
//                {0.305, 0.247, 0.894},
//                {0.247, 0.364, 0.894},
//
//                {0.631, 0.039, 0.682},  //purple
//                {0.945, 1, 0.341}       //yellow
                {0.305, 0.247, 0.894},  //blue
                {0.952, 0.207, 0.313},  //red
                {0.952, 0.227, 0.168},  //red
                {0.223, 0.149, 0.949},  //blue
                {1, 0.121, 0.333},      //red
                {0.886, 0.196, 0.239},  //red
                {0.196, 0.2, 0.886},    //blue
                {0.952, 0.207, 0.231},  //red
                {0.149, 0.156, 0.949},  //blue
                {0.247, 0.364, 0.894},  //blue
                {0.631, 0.039, 0.682},  //purple
                {0.945, 1, 0.341}       //yellow
        };


        ArrayList<Cluster> groups = new ArrayList<Cluster>();

        for(int i = 0; i < inputMatrix.length; i++){
            Cluster input = new Cluster(Matrix.convertTo1D(Matrix.getRow(inputMatrix, i)), 0.1);

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

        System.out.println(groups.size());
        for(Cluster cluster : groups){
            System.out.println(Arrays.toString(cluster.getCentroid()));
            System.out.println(cluster.getRadius());
            System.out.println("--------------");
        }
    }

}
