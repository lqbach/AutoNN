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

        double [][] dot_prod = Matrix.dot(matrixB, matrixC);
        System.out.println(Arrays.deepToString(dot_prod).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        System.out.println("length = "+ matrixA.length);

        double [][] had_prod = Matrix.hadamard(matrixA, matrixC);
        System.out.println(Arrays.deepToString(had_prod).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        double [][] sca_prod = Matrix.multiply(1.5, matrixA);
        System.out.println(Arrays.deepToString(sca_prod).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        double [][] sum = Matrix.add(matrixA, matrixA);
        System.out.println(Arrays.deepToString(sum).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        double [][] difference = Matrix.subtract(matrixA, matrixA);
        System.out.println(Arrays.deepToString(difference).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        double [][] sig_mat = Matrix.sigmoid(matrixA);
        System.out.println(Matrix.print(sig_mat));
    }
}
