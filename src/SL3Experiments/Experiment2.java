package SL3Experiments;

import SelfLearning3.SLNN;
import cluster4.Cluster;
import matrix.Matrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Experiment2 {

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

        //find standard deviations
        double [] stanDev = null;
        int bigN = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("resources/" + filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if(stanDev == null){
                    stanDev = new double[values.length];
                }

                for(int i = 0; i < values.length; i ++){
                    double input = Double.parseDouble(values[i]);
                    stanDev[i] += Math.pow(input - mean[i], 2);
                }
                bigN ++;
            }
        }
        catch(Exception e){
            System.out.println("File not found!");
            System.out.println(e);
        }

        for(int i = 0; i < stanDev.length; i ++){
            stanDev[i] = stanDev[i] / bigN;
            stanDev[i] = Math.sqrt(stanDev[i]);
        }

        //System.out.println(Arrays.toString(stanDev));

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

                    double standardizedInput = (input - mean[i])/(stanDev[i]);
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
        SLNN nn = null;
        int counter = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader("resources/" + "normalized.data"));

            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double [] input = Arrays.stream(values)
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                if(nn == null){
                    nn = new SLNN(input.length - 1, 4, .50, 10, .9);
                }

                double [] validInput = Arrays.copyOfRange(input, 0, input.length-1);
                nn.learn(validInput);
                counter ++;
                //System.out.println("Number of clusters is: " + nn.getNumberOfClusters());
                //System.out.println(Arrays.toString(validInput));

            }

            //System.out.println("Number of actual points are: " + counter);
            System.out.println("Number of clusters: " + nn.getNumberOfClusters());
            System.out.println("Number of outliers: " + nn.getNumberOfOutliers());
            int points = 0;
            points+=nn.getNumberOfOutliers();
            for(Cluster c : nn.getClusters()){
                points+=c.getPoints().size();
            }
            //System.out.println("Total points in system: " + points);

        }
        catch(IOException e){
            System.out.println(e);
            System.out.println("Cannot find normalized data file");
        }


        //TESTING THE NETWORK

        ArrayList<Integer> list = new ArrayList<Integer>(counter);
        for(int i = 0; i <= counter; i ++){
            list.add(i);
        }

        ArrayList<Integer> testList = new ArrayList<Integer>();
        Random rand = new Random();
        while(testList.size() < 75){
            int index = rand.nextInt(list.size());
            testList.add(list.remove(index));
        }

        ArrayList<Integer> cluster1 = new ArrayList<>();
        ArrayList<Integer> cluster2 = new ArrayList<>();
        ArrayList<Integer> cluster3 = new ArrayList<>();

        int numOfTestInputs = 75;
        int numOfIncorrect = 0;

        for(int index : testList){
            try (BufferedReader br = new BufferedReader(new FileReader("resources/normalized.data"))) {
                for (int i = 0; i < index; i++)
                    br.readLine();
                String line = br.readLine();
                String[] values = line.split(",");
                double [] input = Arrays.stream(values)
                        .mapToDouble(Double::parseDouble)
                        .toArray();

                double [] validInput = Arrays.copyOfRange(input, 0, input.length-1);
                int score = findLargestOutputScore(nn.feedforward(validInput));

                switch (score){
                    case 0:
                        cluster1.add((int) input[input.length - 1]);
                        break;
                    case 1:
                        cluster2.add((int) input[input.length - 1]);
                        break;
                    case 2:
                        cluster3.add((int) input[input.length - 1]);
                        break;
                }


            }
            catch(IOException e){
                System.out.println(e);
                System.out.println("Cannot find normalized data file");
            }
        }

        int majority1 = majorityNumber(cluster1);
        System.out.println("Majority of cluster1 is " + majority1);
        for(int num : cluster1){
            if(num != majority1){
                numOfIncorrect ++;
            }
        }

        int majority2 = majorityNumber(cluster2);
        System.out.println("Majority of cluster2 is " + majority2);

        for(int num : cluster2){
            if(num != majority2){
                numOfIncorrect ++;
            }
        }

        int majority3 = majorityNumber(cluster3);
        System.out.println("Majority of cluster3 is " + majority3);

        for(int num : cluster3){
            if(num != majority3){
                numOfIncorrect ++;
            }
        }

        System.out.println(Arrays.toString(cluster1.toArray()));
        System.out.println(Arrays.toString(cluster2.toArray()));
        System.out.println(Arrays.toString(cluster3.toArray()));

        System.out.println("Program finished.");
        System.out.println((numOfTestInputs-numOfIncorrect) + "/" + numOfTestInputs + " are correct");



    }

    private static int majorityNumber(ArrayList<Integer> nums) {
        int candidate = 0;
        int count = 0;
        for (int num : nums) {
            if (count == 0)
                candidate = num;
            if (num == candidate)
                count++;
            else
                count--;
        }
        return candidate;
    }

    private static int findLargestOutputScore(double [][] output){
        double [] finalOutput = Matrix.convertTo1D(Matrix.transpose(output));
        int maxIndex = 0;
        double max = 0;
        for(int i = 0; i < finalOutput.length; i ++){
            if(finalOutput[i] > max){
                max = finalOutput[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

}
