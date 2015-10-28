package pl.agh.mkotlarz.zaids.pi;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Mateusz on 27.10.2015.
 */
public class Program {

    private static int n = 100000000;
    private static int h = 4;

    public static long calculateWOThreads() {
        System.out.println();
        System.out.println("Without threads: ");
        long startTime = System.currentTimeMillis();
        Double pi = (double) 0;
        for (double i = 0; i < n; i++) {
            pi += h / ( 1 + Math.pow( ((2*i+1) / (2*n)) , 2) );
        }
        System.out.println(pi + " - Time: "+(System.currentTimeMillis()-startTime)+" ms");
        return (System.currentTimeMillis()-startTime);
    }

    public static long calculateWithThreads(){
        System.out.println();
        System.out.println("With threads: ");
        long startTime = System.currentTimeMillis();

        try {
            Double pi = (double) 0;
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            System.out.println("Processors count: "+availableProcessors);
            ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            Set<Future<Double>> resultsSet = new HashSet<>();

            for (int i = 0; i < availableProcessors; i++) {
                int start = (n/availableProcessors)*i;
                int end = (n/availableProcessors)*i+(n/availableProcessors)-1;
                resultsSet.add(pool.submit(new ObliczanieLiczbyPi(start, end, h, n)));
            }

            for(Future<Double> elem : resultsSet) {
                pi += elem.get();
            }

            System.out.println(pi + " - Time: "+(System.currentTimeMillis()-startTime)+" ms");
            pool.shutdown();
            return (System.currentTimeMillis()-startTime);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String args[]) {
        long time1 = calculateWithThreads();
        long time2 = calculateWOThreads();

        System.out.println(((double)time2/(double)time1));

    }
}
