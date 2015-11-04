package pl.agh.mkotlarz.zaids.matrices.ondouble;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
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
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms\n");

            startTime = System.currentTimeMillis();
            System.out.println("Multiplying without threads...");
            MatrixUtilities.multiplyArrayOfMatrices(matrices.toArray(new Matrix[matrices.size()]));
//            System.out.println(MatrixUtilities.multiplyArrayOfMatrices(matrices.toArray(new Matrix[matrices.size()])));
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms\n");

            startTime = System.currentTimeMillis();
            System.out.println("Multiplying using threads (thread by pair)...");
            multiplyWithThreads();
//            System.out.println(multiplyWithThreads());
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms\n");

            startTime = System.currentTimeMillis();
            System.out.println("Multiplying using available processes...");
            multiplyWithAvailableProcesses();
//            System.out.println(multiplyWithAvailableProcesses());
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms\n");

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
