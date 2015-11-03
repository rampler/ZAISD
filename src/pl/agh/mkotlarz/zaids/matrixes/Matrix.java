package pl.agh.mkotlarz.zaids.matrixes;

import java.util.Arrays;

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
        for (int i = 0; i < getMatrix().length; i++) {
            for (int j = 0; j < getMatrix()[0].length; j++) {
                result += getMatrix()[i][j]+" ";
            }
            result += "\n";
        }
        return result;
    }
}
