package pl.agh.mkotlarz.zaids.matrices.ondouble;

/**
 * Created by Mateusz on 03.11.2015.
 */
public class Matrix {

    private double matrix[][];

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
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
