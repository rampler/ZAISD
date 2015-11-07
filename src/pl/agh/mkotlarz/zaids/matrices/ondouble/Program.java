package pl.agh.mkotlarz.zaids.matrices.ondouble;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Mateusz on 03.11.2015.
 */
public class Program {

    private static LinkedList<Matrix> matrices = new LinkedList<>();

    /**
     * One thread for one pair of matrices
     */
    public static Matrix multiplyWithThreads() throws InterruptedException {
        LinkedList<Matrix> matrices = new LinkedList<>(Program.matrices);

        while(matrices.size() != 1) {
            Matrix[] newMatrices;
            if(matrices.size() % 2 == 1) {
                newMatrices = new Matrix[matrices.size() / 2 + 1];
                newMatrices[matrices.size()/2] = matrices.getLast();
            }
            else
                newMatrices = new Matrix[matrices.size()/2];

            LinkedList<Thread> threads = new LinkedList<>();
            for (int i = 0; i < matrices.size() / 2; i++) {
                MatrixMultiplierThread thread = new MatrixMultiplierThread(matrices.get(2*i), matrices.get(2*i+1), i, newMatrices);
                threads.add(thread);
                thread.start();
            }

            for(Thread thread : threads)
                thread.join();

            matrices = new LinkedList<>();
            for(Matrix matrix : newMatrices)
                matrices.add(matrix);
        }

        return matrices.getFirst();
    }

    /**
     * Like on Pi package - getting active processes and counting by them.
     */
    private static Matrix multiplyWithAvailableProcesses() throws ExecutionException, InterruptedException {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Processors count: "+availableProcessors);
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Future<Matrix>[] resultsSet = new Future[availableProcessors];

        for (int i = 0; i < availableProcessors; i++) {
            int startPosition = (matrices.size() / availableProcessors) * i;
            int endPosition = (matrices.size() / availableProcessors) * (i + 1) - 1;
            resultsSet[i] = (pool.submit(new MatrixMultiplierCallable(matrices.toArray(new Matrix[matrices.size()]), startPosition, endPosition)));
        }

        Matrix[] matrices = new Matrix[availableProcessors];
        int i=0;
        for(Future<Matrix> elem : resultsSet)
            matrices[i++] = elem.get();

        pool.shutdown();
        return MatrixUtilities.multiplyArrayOfMatrices(matrices);
    }

    /**
     * Calculating the minimal multiples and multiply matrices with that order
     * No extra threads
     */
    public static Matrix multiplyWithDynamicProgrammingWOThreads(){
        int[][] splitters = MatrixUtilities.calculateSplittersMatrix(matrices);
        Matrix[] matricesArray = matrices.toArray(new Matrix[matrices.size()]);
        MatrixUtilities.multiplyMatricesInSplittersOrders(matricesArray, splitters, 0, splitters.length-1);
        return matricesArray[0];
    }

    /**
     * Only for tests - to delete in future
     */
    private static void keepOnlyXMatrices(int x){
        LinkedList<Matrix> newMatrices = new LinkedList<>();
        for (int i = 0; i < x; i++)
            newMatrices.add(matrices.get(i));

        matrices = newMatrices;
    }

    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();
            System.out.println("Loading matrices from file...");
            matrices = MatrixImporter.importFromFile("sample-matrices.txt");
            keepOnlyXMatrices(100);
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms\n");

            startTime = System.currentTimeMillis();
            System.out.println("Multiplying without threads...");
            MatrixUtilities.multiplyArrayOfMatrices(matrices.toArray(new Matrix[matrices.size()]));
//            System.out.println(MatrixUtilities.multiplyArrayOfMatrices(matrices.toArray(new Matrix[matrices.size()])));
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms\n");
//
//            startTime = System.currentTimeMillis();
//            System.out.println("Multiplying using threads (thread by pair)...");
//            multiplyWithThreads();
////            System.out.println(multiplyWithThreads());
//            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms\n");
//
//            startTime = System.currentTimeMillis();
//            System.out.println("Multiplying using available processes...");
//            multiplyWithAvailableProcesses();
////            System.out.println(multiplyWithAvailableProcesses());
//            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms\n");

            startTime = System.currentTimeMillis();
            System.out.println("Multiplying using dynamic programming...");
            multiplyWithDynamicProgrammingWOThreads();
//            System.out.println(multiplyWithDynamicProgrammingWOThreads());
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms\n");

            System.out.println();
            System.out.println();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initializeMatrices(){
        for (int i = 0; i < 10; i++) {
            double[][] matrix = new double[10][10];
            for (int j = 0; j < matrix.length; j++)
                for (int k = 0; k < matrix[j].length; k++)
                    matrix[j][k] = 1;

            matrices.add(new Matrix(matrix));
        }
    }


}
