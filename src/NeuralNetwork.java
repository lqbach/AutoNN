/**
 *
 * @author Quan Bach
 * @Email: long-quan.bach@mail.mcgill.ca
 *
 */

import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class NeuralNetwork {
    //TODO: remove 'static'
    private static double [][] hiddenMatrix;
//    private double [] inputLayer;
//    private double [] expectedOutputLayer;

    public NeuralNetwork(int numInput, int numOutput){
//        this.inputLayer = new double[numInput];
//        this.expectedOutputLayer = new double[numOutput];

        //create hidden matrix
        //2-D matrix: [rows][cols]
        hiddenMatrix = new double[numInput][numOutput];
        Random rand = new Random();
        IntStream.range(0, numInput)
                .forEach(i -> IntStream.range(0, numOutput)
                                .forEach(j -> hiddenMatrix[i][j] = rand.nextDouble() * 2 - 1));
    }

    public double [][] getHiddenMatrix(){
        return hiddenMatrix;
    }

    public void train(double [] input, double [] expectedOutput){
        double [] diffError = calcDiffError(input, expectedOutput);
        backprop(input, diffError);
    }

    //TODO: remove 'static'
    private static void backprop(double [] input, double [] diffError){
        double [] contribution = new double [input.length];
        for(int i = 0; i < hiddenMatrix[0].length; i ++){ //loop through all columns in hidden matrix
            int index = i;
            double oldWeightsSum = //sum entire column
                    Arrays.stream(hiddenMatrix)
                          .mapToDouble(arr -> arr[index])
                          .sum();
            IntStream.range(0, hiddenMatrix.length)
                    .forEach(j -> contribution[j]=diffError[index]*(hiddenMatrix[j][index]/oldWeightsSum));

            //update new matrices
            IntStream.range(0, hiddenMatrix.length)
                    .forEach(j -> hiddenMatrix[j][index] = ((input[j] * hiddenMatrix[j][index])-contribution[j]));
        }
    }

    //TODO: remove 'static'
    private static double [] calcDiffError(double [] input, double [] expectedOutput){
        double [] calculatedOutput = new double[expectedOutput.length];
        for(int i = 0; i < hiddenMatrix[0].length; i ++){
            for(int j = 0; j < hiddenMatrix.length; j++){
                calculatedOutput[i] += input[j] * hiddenMatrix[j][i];
            }
        }



        double [] diffError = new double[expectedOutput.length];
        for(int i = 0; i < expectedOutput.length; i ++){
            diffError[i] = calculatedOutput[i] - expectedOutput[i];
        }

        return diffError;

    }

    public static void main (String [] args){
        hiddenMatrix = new double[][]{{.153, .211}, {.747, .128}, {.724, .301}};
        System.out.println("matrix: \n" + Arrays.deepToString(hiddenMatrix).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
        double [] testInput1 = new double[]{.909, .952, .286};
        double [] testOutput1 = new double[]{1, 0};
        double [] diffError = calcDiffError(testInput1, testOutput1);
        System.out.println("Diff error: " + Arrays.toString(diffError));
        backprop(testInput1, diffError);
        System.out.println("matrix: \n" + Arrays.deepToString(hiddenMatrix).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        testInput1 = new double[]{.931, .945, .280};
        testOutput1 = new double[]{1, 0};
        diffError = calcDiffError(testInput1, testOutput1);
        System.out.println("Diff error: " + Arrays.toString(diffError));
        backprop(testInput1, diffError);

        System.out.println("matrix: \n" + Arrays.deepToString(hiddenMatrix).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
    }


}
