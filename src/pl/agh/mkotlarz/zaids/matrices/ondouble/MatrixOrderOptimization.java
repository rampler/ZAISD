package pl.agh.mkotlarz.zaids.matrices.ondouble;

/**
 * Created by Mateusz on 07.11.2015.
 */
public class MatrixOrderOptimization {

    public static void printOptimalParenthesizations(int[][] splitters) {
        boolean[] inAResult = new boolean[splitters.length];
        printOptimalParenthesizations(splitters, 0, splitters.length - 1, inAResult);
    }

    static void printOptimalParenthesizations(int[][] splitters, int i, int j,  /* for pretty printing: */ boolean[] inAResult) {
        if (i != j) {
            printOptimalParenthesizations(splitters, i, splitters[i][j], inAResult);
            printOptimalParenthesizations(splitters, splitters[i][j] + 1, j, inAResult);
            String istr = inAResult[i] ? "_result " : " ";
            String jstr = inAResult[j] ? "_result " : " ";
            System.out.println(" A_" + i + istr + "* A_" + j + jstr);
            inAResult[i] = true;
            inAResult[j] = true;
        }
    }

}
