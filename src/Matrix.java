import java.util.Arrays;
import java.util.Random;
import java.util.stream.*;

/**
 *
 * @author: Quan Bach
 * @email: long-quan.bach@mail.mcgill.ca
 */

public class Matrix {


    /**
     * Dot product multiplication
     *
     * @param: X an l*m matrix
     * @param: Y an m*n matrix
     * @return: an l*n matrix calculating the dot product
     * @throws RuntimeException if (X[0].length (columns of X) != Y.length (rows of Y))
     */
    public static double[][] dot(double [][] X, double [][] Y){

        if (X[0].length != Y.length) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        double[][] result = Arrays.stream(X).map(r ->
                IntStream.range(0, Y[0].length).mapToDouble(i ->
                        IntStream.range(0, Y.length).mapToDouble(j -> r[j] * Y[j][i]).sum()
                ).toArray()).toArray(double[][]::new);
        return result;
    }

    /**
     * Hadamard product multiplication
     *
     * @param: X an l*m matrix
     * @param: Y an m*n matrix
     * @return: an l*n matrix calculating the dot product
     * @throws RuntimeException if rows and columns of both X and Y are not the same
     */
    public static double[][] hadamard(double [][] X, double [][] Y){

        if (X[0].length != Y[0].length || X.length != Y.length) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        double[][] result = Arrays.stream(X).map(r ->
                IntStream.range(0, Y[0].length).mapToDouble(i ->
                        IntStream.range(0, Y.length).mapToDouble(j -> r[j] * Y[j][i]).sum()
                ).toArray()).toArray(double[][]::new);
        return result;
    }

}
