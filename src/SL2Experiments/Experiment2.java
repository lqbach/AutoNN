package SL2Experiments;

import SelfLearning2.SLNN;

import java.io.*;
import java.util.Arrays;

/**
 * This experiment will go through all the values in wines.data
 *
 */

public class Experiment2 {

    public static void main(String args []){

        //normalize all the data
        String filename = "wines.data";

        double [] minValue = null;
        double [] maxValue = null;

        //retrieve all min and max values of each column
        try (BufferedReader br = new BufferedReader(new FileReader("resources/" + filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if(minValue == null){
                    minValue = new double[values.length];
                    Arrays.fill(minValue, Double.MAX_VALUE);

                    maxValue = new double[values.length];
                    Arrays.fill(maxValue, Double.MIN_VALUE);
                }

                for(int i = 0; i < values.length; i ++){
                    double input = Double.parseDouble(values[i]);

                    if(input > maxValue[i]){
                        maxValue[i] = input;
                    }
                    if(input < minValue[i]){
                        minValue[i] = input;
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("File not found!");
            System.out.println(e);
        }

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("resources/normalized.data"));
            BufferedReader br = new BufferedReader(new FileReader("resources/" + filename));

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String standardLine = "";
                for(int i = 0; i < values.length; i ++){
                    //skip the first value
                    if(i == 0){
                        standardLine += values[i];
                        continue;
                    }

                    standardLine += ",";
                    double input = Double.parseDouble(values[i]);

                    double standardizedInput = (input - minValue[i])/(maxValue[i] - minValue[i]);
                    standardLine += standardizedInput;

                }

                standardLine += "\n";
                bw.write(standardLine);

            }
            bw.close();
            br.close();
        }
        catch(IOException e){
            System.out.println("File cannot be created");
            System.out.println(e);
        }


        //The actual experiment
        SLNN nn = null;
        try{
            BufferedReader br = new BufferedReader(new FileReader("resources/" + "normalized.data"));

            String line;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double [] input = Arrays.stream(values)
                                        .mapToDouble(Double::parseDouble)
                                        .toArray();
                if(nn == null){
                    nn = new SLNN(input.length - 1, .50, .40);
                }

                double [] validInput = Arrays.copyOfRange(input, 1, input.length);
                nn.learn(validInput);
//                System.out.println(counter);
//                counter++;
                System.out.println("Number of clusters is: " + nn.getNumberOfClusters());
            }

        }
        catch(IOException e){
            System.out.println(e);
            System.out.println("Cannot find normalized data file");
        }




    }

}
