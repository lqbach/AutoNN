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
     * @param: X an m*n matrix
     * @param: Y an m*n matrix
     * @return: an m*n matrix calculating the hadamard product of X and Y
     * @throws RuntimeException if rows and columns of both X and Y are not the same
     */
    public static double[][] hadamard(double [][] X, double [][] Y){

        if (X[0].length != Y[0].length || X.length != Y.length) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        double[][] result = new double[X.length][X[0].length];
        IntStream.range(0, X.length).forEach( i ->
                IntStream.range(0, X[0].length).forEach( j->
                        result[i][j] = X[i][j] * Y[i][j]
                )
        );
        return result;
    }

    /**
     * Scalar product multiplication
     *
     * @param: a a scalar
     * @param: Y an m*n matrix
     * @return: an m*n matrix calculating the scalar product of a and Y
     */
    public static double[][] multiply(double a, double [][] Y){

        double[][] result = new double[Y.length][Y[0].length];
        IntStream.range(0, Y.length).forEach( i ->
                IntStream.range(0, Y[0].length).forEach( j->
                        result[i][j] = a * Y[i][j]
                )
        );
        return result;
    }

    /**
     * Add two matrices together
     *
     * @param: X an m*n matrix
     * @param: Y an m*n matrix
     * @return: an m*n matrix calculating the sum of X and Y
     * @throws RuntimeException if rows and columns of both X and Y are not the same
     */
    public static double[][] add(double [][] X, double [][] Y){

        if (X[0].length != Y[0].length || X.length != Y.length) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        double[][] result = new double[Y.length][Y[0].length];
        IntStream.range(0, Y.length).forEach( i ->
                IntStream.range(0, Y[0].length).forEach( j->
                        result[i][j] = X[i][j] + Y[i][j]
                )
        );
        return result;

    }

    /**
     * Add two matrices together
     *
     * @param: X an m*n matrix
     * @param: Y an m*n matrix
     * @return: an m*n matrix calculating the difference of X and Y
     * @throws RuntimeException if rows and columns of both X and Y are not the same
     */
    public static double[][] subtract(double [][] X, double [][] Y){

        if (X[0].length != Y[0].length || X.length != Y.length) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        double[][] result = new double[Y.length][Y[0].length];
        IntStream.range(0, Y.length).forEach( i ->
                IntStream.range(0, Y[0].length).forEach( j->
                        result[i][j] = X[i][j] - Y[i][j]
                )
        );
        return result;

    }

    /**
     * Find sigmoid function of a matrix
     *
     * @param: X an m*n matrix
     * @return: an m*n matrix calculating the sigmoid function of all values of X
     */
    public static double[][] sigmoid(double [][] X){

        double[][] result = new double[X.length][X[0].length];
        IntStream.range(0, X.length).forEach( i ->
                IntStream.range(0, X[0].length).forEach( j->
                        result[i][j] = 1.0 / (1.0 + Math.exp(-X[i][j]))
                )
        );
        return result;

    }

    /**
     * Pretty print a matrix
     *
     * @param: X an m*n matrix
     * @return: A string with all values of matrix
     */
    public static String print(double [][] X){

        String matrixString = (Arrays.deepToString(X).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
        return matrixString;

    }




}
