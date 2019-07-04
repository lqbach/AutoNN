package pre_neural_network; /**
 *
 * @author Quan Bach
 * @Email: long-quan.bach@mail.mcgill.ca
 *
 */

import java.util.Arrays;
import java.util.Random;
import java.util.stream.*;

public class Experiment {
    public static void main(String args[]){
    }

    private static double [] backprop(double [] input, double [] matrix, double diffError){
        //calculate contribution
        double oldWeightsSum = DoubleStream.of(matrix).sum();
        double [] contribution = new double [input.length];
        IntStream.range(0, input.length)
                .forEach(i -> contribution[i] = (matrix[i]/oldWeightsSum) * diffError);
        //System.out.println("Contribution: " + Arrays.toString(contribution));

        double [] newWeights = new double [input.length];
        IntStream.range(0, input.length)
                .forEach(i -> newWeights[i] = ((input[i] * matrix[i])-contribution[i])/input[i]);
        //System.out.println("New Weights: " + Arrays.toString(newWeights));

        return newWeights;
    }

    private static double calcDiffError(double [] input, double [] matrix, double expectedOutput){
        double [] calculatedOutput = {0};
        IntStream.range(0, input.length)
                .forEach(i -> calculatedOutput[0] += (input[i] * matrix[i]));
        return (calculatedOutput[0] - expectedOutput);
    }
}
