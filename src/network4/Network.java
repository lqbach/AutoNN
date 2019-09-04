package network4;

import matrix.Matrix;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A 3-layer neural network
 * The number of output neurons can be altered
 *
 * @author: Quan Bach
 * @email: long-quan.bach@mail.mcgill.ca
 */

public class Network {

    final private double ETA = 0.1 ; //learning rate

    private double [][] weightsHidden;
    private double [][] weightsOuter;
    private double [][] biasesHidden;
    private double [][] biasesOuter;

    private int numInputNeurons;
    private int numHiddenNeurons;
    private int numOutputNeurons = 0;

    private double threshold;

    /**
     * network.network constructor, creates weights and biases based on number of neurons given as parameters
     *
     * @param numInputNeurons number of input neurons in our network, first layer
     * @param numHiddenNeurons number of hidden neurons in our network, second layer, hidden
     * @param numOutputNeurons number of output neurons in our network, third layer
     * @return returns a network.network object
     */

    public Network(int numInputNeurons, int numHiddenNeurons, int numOutputNeurons){
        weightsHidden = Matrix.random(numHiddenNeurons, numInputNeurons);
        biasesHidden = Matrix.random(numHiddenNeurons, 1);

        weightsOuter = Matrix.random(numOutputNeurons, numHiddenNeurons);
        biasesOuter = Matrix.random(numOutputNeurons, 1);
    }

    public Network(int numInputNeurons, int numHiddenNeurons, double threshold){
        weightsHidden = Matrix.random(numHiddenNeurons, numInputNeurons);
        biasesHidden = Matrix.random(numHiddenNeurons, 1);
        this.numInputNeurons = numInputNeurons;
        this.numHiddenNeurons = numHiddenNeurons;
        this.threshold = threshold;
    }

    //Getter functions


    public String getWeightsHidden() {
        return Matrix.print(weightsHidden);
    }

    public String getWeightsOuter() {
        return Matrix.print(weightsOuter);
    }

    public String getBiasesHidden() {
        return Matrix.print(biasesHidden);
    }

    public String getBiasesOuter() { return Matrix.print(biasesOuter);    }

    /**
     * Feedforward function
     *
     * @param: X a 2D array of inputs
     * @return: Calculates the final outcome after passing through the two layers. Z = WX + b
     */
    public double [][] feedforward(double [][] X){
        double [][] Z;
        Z = Matrix.sigmoid(Matrix.add(Matrix.dot(weightsHidden, X), biasesHidden));
        Z = Matrix.sigmoid(Matrix.add(Matrix.dot(weightsOuter, Z), biasesOuter));
        return Z;
    }

    /**
     * Backpropagation algorithm
     * @param: X a 2D array of inputs, and Y a 2D array of the expected outputs
     *
     * https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/
     */

    public void backprop(double [][] X, double [][] Y){
        //feedforward
        //Z is the expected output
        double [][] hiddenZ, outputZ;
        hiddenZ = Matrix.sigmoid(Matrix.add(Matrix.dot(weightsHidden, X), biasesHidden));
        outputZ = Matrix.sigmoid(Matrix.add(Matrix.dot(weightsOuter, hiddenZ), biasesOuter));

        //working with outer weights first (between hidden and output layer)

        double [][] douto = Matrix.hadamard(outputZ, Matrix.subtract(1, outputZ));
        double [][] dEtotal = Matrix.subtract(outputZ, Y);
        double [][] sigO = Matrix.hadamard(douto, dEtotal);
        double [][] dWouter = Matrix.dot(sigO, Matrix.transpose(hiddenZ));

        weightsOuter = Matrix.subtract(weightsOuter, Matrix.multiply(ETA, dWouter));
        biasesOuter = Matrix.subtract(biasesOuter, Matrix.multiply(ETA, sigO));

        //inner weights (between input and hidden)

        double [][] douth = Matrix.hadamard(hiddenZ, Matrix.subtract(1, hiddenZ));
        double [][] dE_douto = Matrix.dot(Matrix.transpose(sigO), weightsOuter);
        double [][] sigH = Matrix.hadamard(Matrix.transpose(dE_douto), douth);
        double [][] dWinner = Matrix.dot(sigH, Matrix.transpose(X));

        weightsHidden = Matrix.subtract(weightsHidden, Matrix.multiply(ETA, dWinner));
        biasesHidden = Matrix.subtract(biasesHidden, Matrix.multiply(ETA, sigH));

    }

    /**
     * Adds a new row to the matrix between outer and hidden layer for weights and biases
     * TODO: convert the double arrays to arraylists instead for constant time add in addRandomRow
     */
    public void trainNewData(){
        if(weightsOuter == null){
            weightsOuter = Matrix.random(1, numHiddenNeurons);
            biasesOuter = Matrix.random(1,1);
        }
        else {
            weightsOuter = Matrix.addRandomRow(weightsOuter);
            biasesOuter = Matrix.addRandomRow(biasesOuter);
        }
        numOutputNeurons ++;
    }

    public void deleteOutput(int n){
        weightsOuter = Matrix.deleteRow(weightsOuter, n);
        biasesOuter = Matrix.deleteRow(biasesOuter, n);
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
        double [][] scores = feedforward(input);
        int numScores = 0;

        ArrayList<Integer> listScores = new ArrayList<>();

        for(int i = 0; i < scores.length; i ++){
            if(scores[i][0] > threshold){
                listScores.add(i);
            }
        }

        int [] neuronScores = new int [listScores.size()];

        for(int i = 0; i < neuronScores.length; i ++){
            neuronScores[i] = listScores.get(i);
        }

        return neuronScores;
    }


}
