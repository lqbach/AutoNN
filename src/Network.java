import matrix.Matrix;


/**
 * A 3-layer neural network
 *
 * @author: Quan Bach
 * @email: long-quan.bach@mail.mcgill.ca
 */

public class Network {


    private double [][] weightsHidden;
    private double [][] weightsOuter;
    private double [][] biasesHidden;
    private double [][] biasesOuter;

    /**
     * Network constructor, creates weights and biases based on number of neurons given as parameters
     *
     * @param numInputNeurons number of input neurons in our network, first layer
     * @param numHiddenNeurons number of hidden neurons in our network, second layer, hidden
     * @param numOutputNeurons number of output neurons in our network, third layer
     * @return returns a Network object
     */

    public Network(int numInputNeurons, int numHiddenNeurons, int numOutputNeurons){
        weightsHidden = Matrix.random(numInputNeurons, numHiddenNeurons);
        weightsOuter = Matrix.random(numHiddenNeurons, numOutputNeurons);
        biasesHidden = Matrix.random(1, numHiddenNeurons);
        biasesOuter = Matrix.random(1, numOutputNeurons);
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

    public String getBiasesOuter() {
        return Matrix.print(biasesOuter);    }

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

}
