package pre_neural_network;

import pre_neural_network.NeuralNetwork;

import java.util.Arrays;

public class Test1 {
    public static void main(String args []){
        /*
        Test: we will use RGB values as the input
        We will train our network to recognize colors. We will first go with yellow and purple.
        Therefore, there will be 3 input, and 2 output.
        Input: {R, G, B}
        Output: {is yellow, is purple}
        Our matrix should be the following:
        {
         {_, _},
         {_, _},
         {_, _},
        }

        Each column in our matrix should be able to recognize colors as such
        Using RGB normalized decimal
         */
        NeuralNetwork network = new NeuralNetwork(3, 2);
        double [][] matrix = network.getHiddenMatrix();
//        for(int i = 0; i < matrix.length; i++){
//            for(int j = 0; j < matrix[0].length; j++){
//                System.out.println(matrix[i][j]);
//            }
//        }
        System.out.println(Arrays.deepToString(matrix).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
        System.out.println();

        double [] testInput1    = {.909, .952, .286};
        double [] testExpected1 = {1, 0};
        network.train(testInput1, testExpected1);

        matrix = network.getHiddenMatrix();
        System.out.println(Arrays.deepToString(matrix).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
        System.out.println();


        double [] testInput2    = {0.858, 0.286, 0.952};
        double [] testExpected2 = {0, 1};
        network.train(testInput2, testExpected2);

        matrix = network.getHiddenMatrix();
        System.out.println(Arrays.deepToString(matrix).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
        System.out.println();


    }
}
