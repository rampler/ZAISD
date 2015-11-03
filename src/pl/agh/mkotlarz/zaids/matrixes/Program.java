package pl.agh.mkotlarz.zaids.matrixes;

import java.util.LinkedList;

/**
 * Created by Mateusz on 03.11.2015.
 */
public class Program {

    private static LinkedList<Matrix> matrixes = new LinkedList<>();

    public static Matrix multiplyWOthreads() {
        Matrix result = matrixes.getFirst();
        for (int i = 1; i < matrixes.size(); i++) {
            result = MatrixMultiplierThread.multiplyMatrixes(result, matrixes.get(i));
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            System.out.println("Loading matrixes from file...");
            matrixes = MatrixImporter.importFromFile("sample-matrices.txt");
            System.out.println("Multiplying without threads...");
            System.out.println(multiplyWOthreads());
            System.out.println("Multiplying using threads...");
            Matrix temp = MatrixMultiplierThread.multiplyMatrixes(matrixes.get(0), matrixes.get(1));
//            MatrixMultiplierThread thread = new MatrixMultiplierThread(matrixes.get(0), matrixes.get(1));
//            thread.start();
            System.out.println(matrixes);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initializeMatrixes(){
        for (int i = 0; i < 10; i++) {
            double[][] matrix = new double[10][10];
            for (int j = 0; j < matrix.length; j++)
                for (int k = 0; k < matrix[j].length; k++)
                    matrix[j][k] = 1;

            matrixes.add(new Matrix(matrix));
        }
    }


}
