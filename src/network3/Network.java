package network3;

import matrix.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A 2-layer neural network
 * This network starts off with no outputs and grows depending on the inputs it receives
 *
 * @author: Quan Bach
 * @email: long-quan.bach@mail.mcgill.ca
 */

public class Network {

    final private double ETA = 0.15; //learning rate

    private double [][] weightsHidden;
    private double [][] biasesHidden;

    private int numInputNeurons;
    private int numOutputNeurons = 0;


    private double threshold;

    /**
     * network.network constructor, creates weights and biases based on number of neurons given as parameters
     *
     * @param numInputNeurons number of input neurons in our network, first layer
     * @return returns a network.network object
     */

    public Network(int numInputNeurons, double threshold){
        this.numInputNeurons = numInputNeurons;
        this.threshold = threshold;
    }

    //Getter functions

    public int getNumInputNeurons(){
        return numInputNeurons;
    }

    public int getNumOutputNeurons(){
        return numOutputNeurons;
    }

    public String getWeightsHidden() {
        return Matrix.print(weightsHidden);
    }

    public String getBiasesHidden() {
        return Matrix.print(biasesHidden);
    }


    /**
     * Feedforward function
     *
     * @param: X a 2D array of inputs
     * @return: Calculates the final outcome after passing through the two layers. Z = WX + b
     */
    public double [][] feedForward(double [][] X){
        if(weightsHidden.length == 0 || biasesHidden.length == 0){
            throw new RuntimeException("Weights and biases not instantiated in network yet");
        }
        double [][] Z;
        Z = Matrix.sigmoid(Matrix.add(Matrix.dot(weightsHidden, X), biasesHidden));
        return Z;
    }

    /**
     * Backpropagation algorithm
     * @param: X a 2D array of inputs, and Y a 2D array of the expected outputs
     *
     * https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/
     *
     * https://smalldata.tech/blog/2016/05/03/building-a-simple-neural-net-in-java#training-process
     */

    public void backpropagate(double [][] X, double [][] Y){
        if(weightsHidden.length == 0 || biasesHidden.length == 0){
            throw new RuntimeException("Weights and biases not instantiated in network yet");
        }
        //feedforward
        //Z is the expected output
        double [][] hiddenZ;
        hiddenZ = feedForward(X);

        //working with outer weights first (between hidden and output layer)

        double [][] douto = Matrix.hadamard(hiddenZ, Matrix.subtract(1, hiddenZ));
        double [][] dEtotal = Matrix.subtract(hiddenZ, Y);
        double [][] sigO = Matrix.hadamard(douto, dEtotal);
        double [][] dWouter = Matrix.dot(sigO, Matrix.transpose(X));


        weightsHidden = Matrix.subtract(weightsHidden, Matrix.multiply(ETA, dWouter));
        biasesHidden = Matrix.subtract(biasesHidden, Matrix.multiply(ETA, sigO));


    }

    //adds a new row
    //TODO: probably very long, but convert matrix package to work with arraylists for better optimization
    public void trainNewData(){
        if(weightsHidden == null){
            weightsHidden = Matrix.random(1, numInputNeurons);
            biasesHidden = Matrix.random(1,1);
        }
        else {
            weightsHidden = Matrix.addRandomRow(weightsHidden);
            biasesHidden = Matrix.addRandomRow(biasesHidden);
        }
        numOutputNeurons ++;
    }

    //deletes a row
    public void deleteOutput(int n){
        weightsHidden = Matrix.deleteRow(weightsHidden, n);
        biasesHidden = Matrix.deleteRow(biasesHidden, n);
        numOutputNeurons --;
    }


    /**
     *
     * @param input an input that matches the dimension
     * @return an array of where the output cells yield an answer of .90 or higher, making the point a potential
     * candidate
     */
    public int[] getOutputNeuronScores(double [][] input){
        if(numOutputNeurons == 0){
            return null;
        }
        double [][] scores = feedForward(input);
        int numScores = 0;

        for(int i = 0; i < scores.length; i ++){
            if(scores[i][0] > threshold){
                numScores ++;
            }
        }

        int [] neuronScores = new int[numScores];
        int count = 0;
        for(int i = 0; i < scores.length; i ++){
            if(scores[i][0] > threshold){
                neuronScores[count] = i;
                count ++;
            }
        }
        return neuronScores;
    }


//    public int[] getHighScores(double [][] input){
//
//        if(numOutputNeurons == 0){
//            return null;
//        }
//        double [][] scores = feedForward(input);
//        double highScore = 0;
//
//        for(int i = 0; i < scores.length; i ++){
//            if(scores[i][1] > highScore){
//                highScore =scores[i][1];
//            }
//        }
//
//        if(highScore < threshold){
//            return null;
//        }
//
//        int numScores = 0;
//        for(int i = 0; i < scores.length; i ++){
//            if(scores[i][1] > highScore - .1){
//                numScores ++;
//            }
//        }
//
//        int [] scorePlace = new int[numScores];
//        int count = 0;
//        for(int i = 0; i < scores.length; i ++){
//            if(scores[i][1] > highScore - .1){
//                scorePlace[count] = i;
//                count ++;
//            }
//        }
//
//        return scorePlace;
//    }





}
