package pl.agh.mkotlarz.zaids.matrices.ondouble;

/**
 * Created by Mateusz on 07.11.2015.
 */
public class MatrixOrderUtilities {

    public static void printOrderOfMultiplications(int[][] splitters) {
        boolean[] inAResult = new boolean[splitters.length];
        printOrderOfMultiplications(splitters, 0, splitters.length - 1, inAResult);
    }

    private static void printOrderOfMultiplications(int[][] splitters, int i, int j, boolean[] inAResult) {
        if (i != j) {
            printOrderOfMultiplications(splitters, i, splitters[i][j], inAResult);
            printOrderOfMultiplications(splitters, splitters[i][j] + 1, j, inAResult);
            String istr = inAResult[i] ? "_result " : " ";
            String jstr = inAResult[j] ? "_result " : " ";
            System.out.println(" A_" + i + istr + "* A_" + j + jstr);
            inAResult[i] = true;
            inAResult[j] = true;
        }
    }

}
