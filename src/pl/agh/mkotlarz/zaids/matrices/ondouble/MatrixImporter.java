package pl.agh.mkotlarz.zaids.matrices.ondouble;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Mateusz on 03.11.2015.
 */
public class MatrixImporter {

    public static LinkedList<Matrix> importFromFile(String path) throws FileNotFoundException {
        LinkedList<Matrix> matrixes = new LinkedList<>();
        Scanner in = new Scanner(new File(path));

        boolean createNextMatrix = true;
        LinkedList<LinkedList<Double>> tempMatrix = null;

        while(in.hasNextLine()) {
            String line = in.nextLine();
            if(line.isEmpty()) {
                matrixes.add(new Matrix(translate(tempMatrix)));
                createNextMatrix = true;
            }
            else {
                String[] values = line.split(";");
                if(createNextMatrix){
                    tempMatrix = new LinkedList<>();
                    createNextMatrix = false;
                }

                tempMatrix.add(new LinkedList<Double>());

                for(String value : values)
                    tempMatrix.getLast().add(new Double(value)/8);
            }
        }

        return matrixes;
    }

    private static double[][] translate(LinkedList<LinkedList<Double>> inMatrix) {
        double[][] outMatrix = new double[inMatrix.get(0).size()][inMatrix.size()];
        for (int i = 0; i < inMatrix.size(); i++)
            for (int j = 0; j < inMatrix.get(0).size(); j++)
                outMatrix[j][i] = inMatrix.get(i).get(j);

        return outMatrix;
    }
}
