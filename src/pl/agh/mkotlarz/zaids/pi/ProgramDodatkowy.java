package pl.agh.mkotlarz.zaids.pi;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Mateusz on 27.10.2015.
 */
public class ProgramDodatkowy {

    private static int n = 100000000;
    private static int h = 4;

    public static long calculateWOThreads() {
        System.out.println();
        System.out.println("Without threads: ");
        long startTime = System.currentTimeMillis();
        Double wynik = (double) 0;
        double prevResult = Math.log(1);
        for (double x = 1/(double)n; x < 1; x+= 1/(double)n) {
            double result = (x*Math.cos(x) + Math.log(Math.abs(x) + 1)) / (1 + Math.pow(x,4)*Math.pow(Math.sin(x),2));
            wynik += ((result+prevResult)*(1/(double)n)) / 2;
            prevResult = result;
        }
        System.out.println(wynik + " - Time: "+(System.currentTimeMillis()-startTime)+" ms");
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
                resultsSet.add(pool.submit(new Dodatkowe(start, end, n)));
            }

            for(Future<Double> elem : resultsSet) {
                pi += elem.get();
            }

            System.out.println(pi + " - Time: "+(System.currentTimeMillis()-startTime)+" ms");
            pool.shutdown();
            return (System.currentTimeMillis()-startTime);

        } catch (Exception e) {
            e.printStackTrace();
            return (System.currentTimeMillis()-startTime);
        }
    }

    public static void main(String args[]) {
        long time1 = calculateWithThreads();
        long time2 = calculateWOThreads();
        System.out.println(((double)time2/(double)time1));
    }
}
