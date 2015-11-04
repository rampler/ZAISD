package pl.agh.mkotlarz.zaids.matrices.ondouble;

import java.util.concurrent.Callable;

/**
 * Created by Mateusz on 04.11.2015.
 */
public class MatrixMultiplierCallable implements Callable<Matrix> {

    private int startPosition, endPosition;
    private Matrix[] matrices;

    public MatrixMultiplierCallable(Matrix[] matrices, int startPosition, int endPosition) {
        this.matrices = matrices;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    @Override
    public Matrix call() throws Exception {
        return MatrixUtilities.multiplyArrayOfMatricesFromRange(matrices, startPosition, endPosition);
    }
}
