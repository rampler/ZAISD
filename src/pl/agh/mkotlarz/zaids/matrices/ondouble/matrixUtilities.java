package pl.agh.mkotlarz.zaids.matrices.ondouble;

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

}
