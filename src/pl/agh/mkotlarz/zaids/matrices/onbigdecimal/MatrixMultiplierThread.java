package pl.agh.mkotlarz.zaids.matrices.onbigdecimal;

import java.math.BigDecimal;

/**
 * Created by Mateusz on 03.11.2015.
 */
public class MatrixMultiplierThread extends Thread {

    private Matrix matrix1, matrix2;

    public MatrixMultiplierThread(Matrix matrix1, Matrix matrix2) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
    }

    public static Matrix multiplyMatrixes(Matrix matrix1, Matrix matrix2) {
        Matrix result = new Matrix(new BigDecimal[matrix2.getColumnsCount()][matrix1.getRowsCount()]);

        for (int i = 0; i < matrix2.getColumnsCount(); i++) {
            for (int j = 0; j < matrix1.getRowsCount(); j++) {
                BigDecimal tempResult = new BigDecimal(0);
                for (int k = 0; k < matrix1.getColumnsCount(); k++) {
                    tempResult = tempResult.add(matrix1.getMatrix()[k][j].multiply(matrix2.getMatrix()[i][k]));
                }
                result.getMatrix()[i][j] = tempResult;
            }
        }
        return result;
    }

    @Override
    public void run() {
        super.run();
        System.out.println(matrix1 + " " + matrix2);
    }
}
