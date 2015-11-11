package pl.agh.mkotlarz.zaids.matrices.ondouble;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

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

        Matrix[] matricesArray = matrices.toArray(new Matrix[matrices.size()]);

        for (int i = 0; i < availableProcessors; i++) {
            int startPosition = (matrices.size() / availableProcessors) * i;
            int endPosition = (matrices.size() / availableProcessors) * (i + 1) - 1;
            resultsSet[i] = (pool.submit(new MatrixMultiplierCallable(matricesArray, startPosition, endPosition)));
        }

        Matrix[] matrices = new Matrix[availableProcessors];
        int i=0;
        for(Future<Matrix> elem : resultsSet)
            matrices[i++] = elem.get();

        pool.shutdown();
        return MatrixUtilities.multiplyArrayOfMatrices(matrices);
    }

    /**
     * Create Executor with parametrized number of threads;
     */
    private static Matrix multiplyWithXThreads(int x) throws ExecutionException, InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(x);
        Future<Matrix>[] resultsSet = new Future[x];

        Matrix[] matricesArray = matrices.toArray(new Matrix[matrices.size()]);

        for (int i = 0; i < x; i++) {
            int startPosition = (matrices.size() / x) * i;
            int endPosition = (matrices.size() / x) * (i + 1) - 1;
            resultsSet[i] = (pool.submit(new MatrixMultiplierCallable(matricesArray, startPosition, endPosition)));
        }

        int diff = matrices.size() - 1 - ((matrices.size() / x) * x - 1);
        Matrix[] finalMatrices = new Matrix[x+diff];

        for(int i=0; i<diff; i++)
            finalMatrices[x+i] = matricesArray[matrices.size()-diff+i];

        int i=0;
        for(Future<Matrix> elem : resultsSet)
            finalMatrices[i++] = elem.get();

        pool.shutdown();
        return MatrixUtilities.multiplyArrayOfMatrices(finalMatrices);
    }

    /**
     * Calculating the minimal multiples and multiply matrices with that order
     * No extra threads
     */
    public static Matrix multiplyWithDynamicProgrammingWOThreads(){
        long startTime = System.currentTimeMillis();
        Matrix[] matricesArray = matrices.toArray(new Matrix[matrices.size()]);
        int[][] splitters = MatrixUtilities.calculateSplittersMatrix(matricesArray);
        System.out.println("-- Ordering multiplies time: "+(System.currentTimeMillis()-startTime)+" ms");
        startTime = System.currentTimeMillis();
        MatrixUtilities.multiplyMatricesInSplittersOrders(matricesArray, splitters, 0, splitters.length-1);
        System.out.println("-- Multiplying time: "+(System.currentTimeMillis()-startTime)+" ms");
        return matricesArray[0];
    }

    /**
     * Calculating the minimal multiples and multiply matrices with that order
     * No extra threads
     */
    public static Matrix multiplyWithDynamicProgrammingWithThreads() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Matrix[] matricesArray = matrices.toArray(new Matrix[matrices.size()]);
        int[][] splitters = MatrixUtilities.calculateSplittersMatrix(matricesArray);
        System.out.println("-- Ordering multiplies time: "+(System.currentTimeMillis()-startTime)+" ms");
        startTime = System.currentTimeMillis();
        MatrixUtilities.multiplyMatricesInSplittersOrdersWithThreads(matricesArray, splitters, 0, splitters.length-1);
        System.out.println("-- Multiplying time: "+(System.currentTimeMillis()-startTime)+" ms");
        return matricesArray[0];
    }

    public static Matrix multiplyWithDynamicProgrammingWOThreads2(){
        long startTime = System.currentTimeMillis();
        Matrix[] results = new Matrix[100];
        Matrix[] matricesArray = matrices.toArray(new Matrix[matrices.size()]);
        for (int i = 0; i < 100; i++) {
            Matrix[] tempMatrix = new Matrix[100];
            System.arraycopy(matricesArray,100*i, tempMatrix, 0, 100);

            int[][] splitters = MatrixUtilities.calculateSplittersMatrix(tempMatrix);
//            System.out.println("-- Ordering multiplies time: "+(System.currentTimeMillis()-startTime)+" ms");
            startTime = System.currentTimeMillis();
            MatrixUtilities.multiplyMatricesInSplittersOrders(tempMatrix, splitters, 0, splitters.length-1);
//            System.out.println("-- Multiplying time: "+(System.currentTimeMillis()-startTime)+" ms");
            results[i] = tempMatrix[0];
        }
        return MatrixUtilities.multiplyArrayOfMatrices(results);
    }

    public static Matrix multiplyWithDynamicProgrammingWithThreads2() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Matrix[] results = new Matrix[100];
        Matrix[] matricesArray = matrices.toArray(new Matrix[matrices.size()]);
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            final int ii = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Matrix[] tempMatrix = new Matrix[100];
                    System.arraycopy(matricesArray,100*ii, tempMatrix, 0, 100);
                    int[][] splitters = MatrixUtilities.calculateSplittersMatrix(tempMatrix);
                    MatrixUtilities.multiplyMatricesInSplittersOrders(tempMatrix, splitters, 0, splitters.length-1);
                    results[ii] = tempMatrix[0];
                }
            });
            threads[i] = thread;
            thread.start();
        }
        for (int i = 0; i < threads.length; i++)
            threads[i].join();

        return MatrixUtilities.multiplyArrayOfMatrices(results);
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
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms\n");

//            keepOnlyXMatrices(200);


            startTime = System.currentTimeMillis();
            System.out.println("Multiplying without threads...");
            MatrixUtilities.multiplyArrayOfMatrices(matrices.toArray(new Matrix[matrices.size()]));
//            System.out.println(MatrixUtilities.multiplyArrayOfMatrices(matrices.toArray(new Matrix[matrices.size()])));
            double secTime = (System.currentTimeMillis()-startTime);
            System.out.println("Time: "+secTime+" ms\n");

            startTime = System.currentTimeMillis();
            System.out.println("Multiplying using threads (thread by pair)...");
            multiplyWithThreads();
//            System.out.println(multiplyWithThreads());
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms");
            System.out.println("Diff: "+(secTime/(System.currentTimeMillis()-startTime))+"\n");

            startTime = System.currentTimeMillis();
            System.out.println("Multiplying using available processes...");
            multiplyWithAvailableProcesses();
//            System.out.println(multiplyWithAvailableProcesses());
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms");
            System.out.println("Diff: "+(secTime/(System.currentTimeMillis()-startTime))+"\n");


            for (int i = 2; i <= 2048; i*=2) {
                startTime = System.currentTimeMillis();
                multiplyWithXThreads(i);
//                System.out.println(multiplyWithXThreads(i));
                System.out.println("Threads count: "+i+" Time: "+(System.currentTimeMillis()-startTime)+" ms");
            }
            System.out.println();

            startTime = System.currentTimeMillis();
            System.out.println("Multiplying using dynamic programming WO threads - 100 parts...");
            multiplyWithDynamicProgrammingWOThreads2();
//            System.out.println(multiplyWithDynamicProgrammingWOThreads2());
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms");
            System.out.println("Diff: "+(secTime/(System.currentTimeMillis()-startTime))+"\n");

            startTime = System.currentTimeMillis();
            System.out.println("Multiplying using dynamic programming With threads - 100 parts...");
            multiplyWithDynamicProgrammingWithThreads2();
//            System.out.println(multiplyWithDynamicProgrammingWithThreads2());
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms");
            System.out.println("Diff: "+(secTime/(System.currentTimeMillis()-startTime))+"\n");

            keepOnlyXMatrices(1000);

            startTime = System.currentTimeMillis();
            System.out.println("Multiplying using dynamic programming WO threads...");
            multiplyWithDynamicProgrammingWOThreads();
//            System.out.println(multiplyWithDynamicProgrammingWOThreads());
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms");
            System.out.println("Diff: "+(secTime/(System.currentTimeMillis()-startTime))+"\n");


            startTime = System.currentTimeMillis();
            System.out.println("Multiplying using dynamic programming With threads...");
            multiplyWithDynamicProgrammingWithThreads();
//            System.out.println(multiplyWithDynamicProgrammingWithThreads());
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms");
            System.out.println("Diff: "+(secTime/(System.currentTimeMillis()-startTime))+"\n");


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
