package pl.agh.mkotlarz.zaids.pi;

import java.util.concurrent.Callable;

/**
 * Created by Mateusz on 27.10.2015.
 */
public class Dodatkowe implements Callable<Double> {

    private double start;
    private double end;
    private double wynik = 0;
    private double prevResult = 0;
    private double n;

    public Dodatkowe(int start, int end, int n) {
        this.start = (double)start/ (double)n;
        this.end = (double)end/(double)n;
        this.n = (double) n;
        prevResult = fun(this.start);
    }

    @Override
    public Double call() throws Exception {

        for (double i = start+1/n; i <= end; i += 1/n) {
            double result = fun(i);
            wynik += ((result+prevResult)*(1/n)) / 2;
            prevResult = result;
        }

        return wynik;
    }

    public double fun(double x) {
        return (x*Math.cos(x) + Math.log(Math.abs(x) + 1)) / (1 + Math.pow(x,4)*Math.pow(Math.sin(x),2));
    }
}
