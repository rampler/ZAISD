package pl.agh.mkotlarz.zaids.matrices.ondouble;

import java.util.LinkedList;

/**
 * Created by Mateusz on 03.11.2015.
 */
public class Program {

    private static LinkedList<Matrix> matrices = new LinkedList<>();

    public static Matrix multiplyWOthreads() {
        Matrix result = matrices.getFirst();
        for (int i = 1; i < matrices.size(); i++) {
            result = MatrixUtilities.multiplyMatrices(result, matrices.get(i));
        }
        return result;
    }

    /**
     * One thread for one pair of matrices
     */
    public static Matrix multiplyWithThreads1() throws InterruptedException {
        LinkedList<Matrix> matrices = new LinkedList<>(Program.matrices);

        while(matrices.size() != 1) {
            Matrix[] newMatrixes;
            if(matrices.size() % 2 == 1) {
                newMatrixes = new Matrix[matrices.size() / 2 + 1];
                newMatrixes[matrices.size()/2] = matrices.getLast();
            }
            else
                newMatrixes = new Matrix[matrices.size()/2];

            LinkedList<Thread> threads = new LinkedList<>();
            for (int i = 0; i < matrices.size() / 2; i++) {
                MatrixMultiplierThread thread = new MatrixMultiplierThread(matrices.get(2*i), matrices.get(2*i+1), i, newMatrixes);
                threads.add(thread);
                thread.start();
            }

            for(Thread thread : threads)
                thread.join();

            matrices = new LinkedList<>();
            for(Matrix matrix : newMatrixes)
                matrices.add(matrix);
        }

        return matrices.getFirst();
    }

    /**
     * Only for tests - to delete in future
     */
    private static void keepOnlyXMatrixes(int x){
        LinkedList<Matrix> newMatrixes = new LinkedList<>();
        for (int i = 0; i < x; i++)
            newMatrixes.add(matrices.get(i));

        matrices = newMatrixes;
    }

    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();
            System.out.println("Loading matrices from file...");
            matrices = MatrixImporter.importFromFile("sample-matrices.txt");
            keepOnlyXMatrixes(200);
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms");

            startTime = System.currentTimeMillis();
            System.out.println("Multiplying without threads...");
            multiplyWOthreads();
//            System.out.println(multiplyWOthreads());
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms");

            startTime = System.currentTimeMillis();
            System.out.println("Multiplying using threads (thread by pair)...");
            multiplyWithThreads1();
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms");

            System.out.println();
            System.out.println();
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

            matrices.add(new Matrix(matrix));
        }
    }


}
