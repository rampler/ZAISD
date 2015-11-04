package pl.agh.mkotlarz.zaids.matrices.onbigdecimal;

import java.math.BigDecimal;

/**
 * Created by Mateusz on 03.11.2015.
 */
public class Matrix {

    private BigDecimal matrix[][];

    public Matrix(BigDecimal[][] matrix) {
        this.matrix = matrix;
    }

    public BigDecimal[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(BigDecimal[][] matrix) {
        this.matrix = matrix;
    }

    public int getColumnsCount() {
        return matrix.length;
    }
    public int getRowsCount(){
        return matrix[0].length;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < getMatrix()[0].length; i++) {
            for (int j = 0; j < getMatrix().length; j++) {
                result += getMatrix()[j][i]+" ";
            }
            result += "\n";
        }
        return result;
    }
}
