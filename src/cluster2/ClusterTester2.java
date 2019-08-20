package cluster2;

import matrix.Matrix;
import network.Network;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ClusterTester2 {
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
                BufferedReader br = new BufferedReader(new FileReader("resources/" + "normalized.data"));


                ArrayList<Cluster> groups = new ArrayList<>();


                String line;
                int counter = 0;
                while ((line = br.readLine()) != null) {
                    counter ++;
                    String[] values = line.split(",");
                    double [] input = Arrays.stream(values)
                            .mapToDouble(Double::parseDouble)
                            .toArray();


                    double [] validInput = Arrays.copyOfRange(input, 0, input.length-1);
                    Cluster inp = new Cluster(validInput, 0.0699987);

                    Iterator itr = groups.listIterator();
                    while(itr.hasNext()){
                        Cluster cluster = (Cluster)itr.next();
                        if(inp.merge(cluster)){
                            //System.out.println("Success at " + i);
                            itr.remove();
                            itr = groups.listIterator();
                        }
                    }
                    groups.add(inp);


                }

                System.out.println(groups.size());
                System.out.println(groups.get(0).getRadius());


            }
            catch(IOException e){
                System.out.println(e);
                System.out.println("Cannot find normalized data file");
            }

        }

}
