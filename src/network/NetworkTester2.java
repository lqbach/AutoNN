package network;

import matrix.Matrix;

import java.io.*;
import java.util.Arrays;

public class NetworkTester2 {

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
            Network nn = new Network(7, 4, 3);
            double [][] output = Matrix.identityMatrix(3);

            for(int i = 0; i < 500; i ++){
                try{
                    BufferedReader br = new BufferedReader(new FileReader("resources/" + "normalized.data"));

                    String line;
                    int counter = 0;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        double [] input = Arrays.stream(values)
                                .mapToDouble(Double::parseDouble)
                                .toArray();


                        double [] validInput = Arrays.copyOfRange(input, 0, input.length-1);



                        nn.backprop(Matrix.transpose(Matrix.convertTo2D(validInput)),  Matrix.getColumn(output, (int)input[input.length - 1] - 1));

                    }


                }
                catch(IOException e){
                    System.out.println(e);
                    System.out.println("Cannot find normalized data file");
                }
            }



            //Testing can be done down here

        double [][] testers = {
                {0.04839246368991347,0.041469893742620265,0.13703655690951547,0.04080330330330206,0.09579133150052584,-0.3033716408197938,-0.13592881761271738},
        };
            double [][] score = nn.feedforward(Matrix.transpose(testers));
            System.out.println(Matrix.print(score));


    }

}
