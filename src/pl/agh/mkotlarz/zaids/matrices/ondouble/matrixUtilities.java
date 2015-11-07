package pl.agh.mkotlarz.zaids.matrices.ondouble;

import java.util.LinkedList;

/**
 * Created by Mateusz on 04.11.2015.
 */
public class MatrixUtilities {

    public static Matrix multiplyMatrices(Matrix matrix1, Matrix matrix2) {
        Matrix result = new Matrix(new double[matrix2.getColumnsCount()][matrix1.getRowsCount()]);

        for (int i = 0; i < matrix2.getColumnsCount(); i++) {
            for (int j = 0; j < matrix1.getRowsCount(); j++) {
                double tempResult = 0;
                for (int k = 0; k < matrix1.getColumnsCount(); k++) {
                    tempResult += matrix1.getMatrix()[k][j] * matrix2.getMatrix()[i][k];
                }
                result.getMatrix()[i][j] = tempResult;
            }
        }
        return result;
    }

    public static Matrix multiplyArrayOfMatrices(Matrix[] matrices) {
        Matrix result = matrices[0];
        for (int i = 1; i < matrices.length; i++)
            result = MatrixUtilities.multiplyMatrices(result, matrices[i]);
        return result;
    }

    public static Matrix multiplyArrayOfMatricesFromRange(Matrix[] matrices, int startPosition, int endPosition) {
        Matrix result = matrices[startPosition];
        for (int i = startPosition+1; i <= endPosition; i++)
            result = MatrixUtilities.multiplyMatrices(result, matrices[i]);
        return result;
    }


    public static int[][] calculateSplittersMatrix(LinkedList<Matrix> matrices) {
        int[] dimensionsArray = new int[matrices.size()+1];

        for (int i = 0; i < matrices.size(); i++)
            dimensionsArray[i] = matrices.get(i).getRowsCount();
        dimensionsArray[dimensionsArray.length-1] = matrices.getLast().getColumnsCount();

        int n = dimensionsArray.length - 1;
        int[][] minCosts = new int[n][n];
        int[][] splitters = new int[n][n];

        for (int m = 1; m < n; m++) {
            for (int i = 0; i < n - m; i++) {
                int j = i + m;
                minCosts[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int q = minCosts[i][k] + minCosts[k+1][j] + dimensionsArray[i]*dimensionsArray[k+1]*dimensionsArray[j+1];
                    if (q < minCosts[i][j]) {
                        minCosts[i][j] = q;
                        splitters[i][j] = k;
                    }
                }
            }
        }
        return splitters;
    }

    public static void multiplyMatricesInSplittersOrders(Matrix[] matrices, int[][] splitters, int i, int j) {
        if (i != j) {
            multiplyMatricesInSplittersOrders(matrices, splitters, i, splitters[i][j]);
            multiplyMatricesInSplittersOrders(matrices, splitters, splitters[i][j] + 1, j);
//            System.out.println(" A_" + i + istr + "* A_" + j + jstr);
            matrices[i] = multiplyMatrices(matrices[i], matrices[j]);
            matrices[j] = matrices[i];
        }
    }
}
