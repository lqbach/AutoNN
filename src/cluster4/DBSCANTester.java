package cluster4;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class DBSCANTester {

    public static void main(String args []){
        //normalize all the data
        String filename = "seeds.data";

        double [] minValue = null;
        double [] maxValue = null;

        double [] mean = null;

        int numInputs = 0;

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

                    mean = new double [values.length];
                }

                for(int i = 0; i < values.length; i ++){
                    double input = Double.parseDouble(values[i]);
                    mean[i] += input;
                    if(input > maxValue[i]){
                        maxValue[i] = input;
                    }
                    if(input < minValue[i]){
                        minValue[i] = input;
                    }
                }
                numInputs ++;
            }
        }
        catch(Exception e){
            System.out.println("File not found!");
            System.out.println(e);
        }

        for(int i = 0; i < mean.length; i ++){
            mean[i] = mean[i] / numInputs;
        }

        //System.out.println(Arrays.toString(mean));

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("resources/normalized.data"));
            BufferedReader br = new BufferedReader(new FileReader("resources/" + filename));

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String standardLine = "";
                for(int i = 0; i < values.length; i ++){
                    //skip the last value
                    if(i == values.length - 1){
                        standardLine += values[i];
                        continue;
                    }


                    double input = Double.parseDouble(values[i]);

                    double standardizedInput = (input - mean[i])/(maxValue[i] - minValue[i]);
                    standardLine += standardizedInput;
                    standardLine += ",";
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
        try{
            DBSCAN clustering = new DBSCAN(.3, 10);

            BufferedReader br = new BufferedReader(new FileReader("resources/" + "normalized.data"));



            String line;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                counter ++;
                String[] values = line.split(",");
                double [] input = Arrays.stream(values)
                        .mapToDouble(Double::parseDouble)
                        .toArray();


                double [] validInput = Arrays.copyOfRange(input, 0, input.length-1);

                int id = (int) input[input.length - 1] - 1;
                //System.out.println(Arrays.toString(validInput) + id);
                clustering.addToCluster(validInput, new int[] {-1});

                //System.out.println(counter);



            }

            System.out.println(clustering.getNumGroups());
            System.out.println(clustering.getOutliers().size());

        }
        catch(IOException e){
            System.out.println(e);
            System.out.println("Cannot find normalized data file");
        }

    }

}
