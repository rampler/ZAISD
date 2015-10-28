package pl.agh.mkotlarz.zaids.pi;

import java.util.concurrent.Callable;

/**
 * Created by Mateusz on 27.10.2015.
 */
public class ObliczanieLiczbyPi implements Callable<Double> {

    private int start;
    private int end;
    private double h;
    private double n;
    private double wynik = 0;

    public ObliczanieLiczbyPi(int start, int end, int h, int n) {
        this.start = start;
        this.end =  end;
        this.h = (double) h;
        this.n = (double) n;
    }

    @Override
    public Double call() throws Exception {
        for (double i = start; i <= end; i++) {
            wynik += h / ( 1 + Math.pow( ((2*i+1) / (2*n)) , 2) );
        }

        return wynik;
    }
}
