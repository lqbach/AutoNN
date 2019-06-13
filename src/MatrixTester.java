import java.util.Arrays;

public class MatrixTester {
    public static void main(String [] args){
        double [][] matrixA =
                {       {3, 2, 6},
                        {5, 7, 8},
                        {1, 3, 2},
                        {2, 1, 5}
                };

        double [][] matrixB =
                {       {1, 2, 3, 4},
                        {5, 6, 7, 8},
                        {1, 2, 3, 4},
                };

        double [][] matrixC =
                {       {1, 2, 3},
                        {6, 5, 4},
                        {7, 8, 9},
                        {4, 2, 1}
                };

        double [][] prod = Matrix.dot(matrixB, matrixC);
        System.out.println(Arrays.deepToString(prod).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));


    }
}
