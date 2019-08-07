package matrix;

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
     * Subtracts two matrices together
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
     * Creates a matrix composed of the double and subtracts accordingly
     *
     * @param: a some double
     * @param: Y an m*n matrix
     * @return: an m*n matrix calculating the difference of a with every element in Y
     * @throws RuntimeException if rows and columns of both X and Y are not the same
     */
    public static double[][] subtract(double a, double [][] Y){

        double[][] result = new double[Y.length][Y[0].length];
        IntStream.range(0, Y.length).forEach( i ->
                IntStream.range(0, Y[0].length).forEach( j->
                        result[i][j] = a - Y[i][j]
                )
        );
        return result;

    }

    /**
     * Transposes a matrix
     *
     * @param: X an m*n matrix
     * @return: an m*n transpose matrix of X
     */
    public static double[][] transpose(double [][] X){
        double[][] result = new double[X[0].length][X.length];
        IntStream.range(0, X.length).forEach( i ->
                IntStream.range(0, X[0].length).forEach( j->
                        result[j][i] = X[i][j]
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
     * Pretty print a matrix accompanied by a newline character
     *
     * @param: X an m*n matrix
     * @return: A string with all values of matrix
     */
    public static String print(double [][] X){

        String matrixString = (Arrays.deepToString(X).replace("], ", "]\n").replace("[[", "[").replace("]]", "]") + '\n');
        return matrixString;

    }

    public static double [][] convertTo2D(double [] X){
        double [][] arr = new double[1][X.length];
        IntStream.range(0, X.length)
                .forEach(i -> arr[0][i] = X[i]);
        return arr;
    }

    public static double [] convertTo1D(double [][] X){
        if(X.length > 1){
            throw new RuntimeException("Can only convert 2D arrays with one row");
        }
        double [] arr = new double[X[0].length];
        IntStream.range(0, X[0].length)
                .forEach(i -> arr[i] = X[0][i]);
        return arr;
    }

    public static double [][] identityMatrix(int n){
        double [][] arr = new double[n][n];
        IntStream.range(0, n)
                .forEach(i -> IntStream.range(0, n)
                        .forEach(j -> {
                            if(i ==j){
                                arr[i][j] = 1;
                            }
                            else{
                                arr[i][j] = 0;
                            }
                        }));
        return arr;
    }

    public static double [][] getRow(double [][] X, int a){
        if (X.length < a) {
            throw new RuntimeException("Number of columns not enough");
        }
        double [][] row = new double [1][X[0].length];
        IntStream.range(0, X[0].length)
                .forEach(i -> row[0][i] = X[a][i]);
        return row;
    }

    public static double [][] getColumn(double [][] X, int a){
        if (X[0].length < a) {
            throw new RuntimeException("Number of columns not enough");
        }
        double [][] col = new double [X.length][1];
        IntStream.range(0, X.length)
                .forEach(i -> col[i][0] = X[i][a]);
        return col;
    }


    /**
     * Fill an m*n matrix with random values within a normal distribution with mean 0 and variance 1
     *
     * @param: m indicating number of rows
     * @param: n indicating number of columns
     * @return: A string with all values of matrix
     */
    public static double [][] random(int m, int n){

        double [][] randomMatrix = new double[m][n];
        Random rand = new Random();
        IntStream.range(0, m)
                .forEach(i -> IntStream.range(0, n)
                        .forEach(j -> randomMatrix[i][j] = rand.nextDouble() * 2 - 1));
        return randomMatrix;
    }


}
