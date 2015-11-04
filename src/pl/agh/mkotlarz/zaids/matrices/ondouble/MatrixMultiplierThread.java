package pl.agh.mkotlarz.zaids.matrices.ondouble;

/**
 * Created by Mateusz on 03.11.2015.
 */
public class MatrixMultiplierThread extends Thread {

    private Matrix matrix1, matrix2;
    private Matrix[] matrices;
    private int index;

    public MatrixMultiplierThread(Matrix matrix1, Matrix matrix2, int index, Matrix[] matrices) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.index = index;
        this.matrices = matrices;
    }

    @Override
    public void run() {
        super.run();
        this.matrices[index] = MatrixUtilities.multiplyMatrices(matrix1, matrix2);
    }
}
