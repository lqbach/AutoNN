import matrix.Matrix;
import java.util.Random;

/**
 * A 2-layer neural network
 *
 * @author: Quan Bach
 * @email: long-quan.bach@mail.mcgill.ca
 */

public class Network2 {

    final private double ETA = 0.1 ; //learning rate

    private double [][] weightsHidden;

    private double [][] biasesHidden;


    /**
     * Network constructor, creates weights and biases based on number of neurons given as parameters
     *
     * @param numInputNeurons number of input neurons in our network, first layer
     * @param numOutputNeurons number of output neurons in our network, second layer
     * @return returns a Network object
     */

    public Network2(int numInputNeurons, int numOutputNeurons){
        weightsHidden = Matrix.random(numOutputNeurons, numInputNeurons);
        biasesHidden = Matrix.random(numOutputNeurons, 1);

    }

    //Getter functions


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
    public double [][] feedforward(double [][] X){
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

    public void backprop(double [][] X, double [][] Y){
        //feedforward
        //Z is the expected output
        double [][] hiddenZ;
        hiddenZ = feedforward(X);

        //working with outer weights first (between hidden and output layer)

        double [][] douto = Matrix.hadamard(hiddenZ, Matrix.subtract(1, hiddenZ));
        double [][] dEtotal = Matrix.subtract(hiddenZ, Y);
        double [][] sigO = Matrix.hadamard(douto, dEtotal);
        double [][] dWouter = Matrix.dot(sigO, Matrix.transpose(X));


        weightsHidden = Matrix.subtract(weightsHidden, Matrix.multiply(ETA, dWouter));
        biasesHidden = Matrix.subtract(biasesHidden, Matrix.multiply(ETA, sigO));


    }


    /**
     * Takes in some input and treats it as a potential new data set
     *
     *
     * @param: X a 2D array of inputs
     *
     *
     */
    public void trainNewData(double [][] X){
        double [][] newHiddenWeights = new double [weightsHidden.length + 1][weightsHidden[0].length];
        for(int i = 0; i < weightsHidden.length; i++){
            for(int j = 0; j < weightsHidden[0].length; j ++){
                newHiddenWeights[i][j] = weightsHidden[i][j];
            }
        }

        double [][] addedWeights = Matrix.random(1, weightsHidden[0].length);
        for(int i = 0; i < newHiddenWeights[0].length; i ++){
            addedWeights[addedWeights.length -1][i] = addedWeights[0][i];
        }

        Random rand = new Random();
        double [][] newHiddenBias = new double [biasesHidden.length + 1][1];
        for(int i = 0; i < biasesHidden.length; i ++){
            newHiddenBias[i][0] = biasesHidden[i][0];
        }

        newHiddenBias[newHiddenBias.length -1][0] = rand.nextDouble()* 2 - 1;

        weightsHidden = newHiddenWeights;
        biasesHidden = newHiddenBias;

        double [][] output = new double [weightsHidden.length][1];
        for(int i = 0; i < weightsHidden.length -1; i ++){
            output[i][0]= 0;
        }
        output[weightsHidden.length-1][0] = 1;


        // train 50 times

        backprop(X, output);



    }


}
