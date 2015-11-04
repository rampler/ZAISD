package pl.agh.mkotlarz.zaids.matrices.onbigdecimal;

import java.math.BigDecimal;
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
            long startTime = System.currentTimeMillis();
            System.out.println("Loading matrixes from file...");
            matrixes = MatrixImporter.importFromFile("sample-matrices.txt");
//            initializeMatrixes();
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms");
            startTime = System.currentTimeMillis();
            System.out.println("Multiplying without threads...");
            System.out.println(multiplyWOthreads());
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms");
            startTime = System.currentTimeMillis();
            System.out.println("Multiplying using threads...");
            Matrix temp = MatrixMultiplierThread.multiplyMatrixes(matrixes.get(0), matrixes.get(1));
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms");

//            System.out.println(matrixes.get(0));
//            System.out.println();
//            System.out.println(matrixes.get(1));
//            System.out.println();
//            System.out.println(temp);
//            MatrixMultiplierThread thread = new MatrixMultiplierThread(matrixes.get(0), matrixes.get(1));
//            thread.start();
//            System.out.println(matrixes);
            System.out.println();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initializeMatrixes(){
        for (int i = 0; i < 10000; i++) {
            BigDecimal[][] matrix = new BigDecimal[10][10];
            for (int j = 0; j < matrix.length; j++)
                for (int k = 0; k < matrix[j].length; k++)
                    matrix[j][k] = new BigDecimal(1);

            matrixes.add(new Matrix(matrix));
        }
    }


}
