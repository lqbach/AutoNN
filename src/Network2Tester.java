import matrix.Matrix;

public class Network2Tester {
    public static void main(String [] args){
        Network2 nn = new Network2(3, 2);

        System.out.println("Getting biases in hidden layer: \n" + nn.getBiasesHidden());
        System.out.println("-------------------------------------------------------");

        System.out.println("Getting weights in hidden layer: \n" + nn.getWeightsHidden());
        System.out.println("-------------------------------------------------------");


        //we will train the network to recognize red and blue colors

        //http://doc.instantreality.org/tools/color_calculator/

        //used normalized RGB decimals

        double [][] redInput = {
                {0.952, 0.207, 0.313},
                {0.952, 0.207, 0.231},
                {0.831, 0.090, 0.031},
                {0.952, 0.227, 0.168},
                {1, 0.121, 0.333},
                {0.886, 0.196, 0.239},
        };

        double [][] redExpectedOutput ={{1}, {0}};

        double [][] blueInput = {
                {0.196, 0.2, 0.886},
                {0.011, 0.019, 0.607},
                {0.149, 0.156, 0.949},
                {0.223, 0.149, 0.949},
                {0.305, 0.247, 0.894},
                {0.247, 0.364, 0.894}
        };

        double [][] blueExpectedOutput = {{0}, {1}};

        double [][] input;

        //transpose the inputs so that vectors are columnized
        redInput = Matrix.transpose(redInput);
        blueInput = Matrix.transpose(blueInput);

        //training red color recognition
        for(int c = 0; c < 1500; c ++){
            for(int i = 0; i < redInput[0].length; i ++){
                input = Matrix.getColumn(redInput, i);
                nn.backprop(input, redExpectedOutput);
            }

            //training blue color recognition
            for(int i = 0; i < blueInput[0].length; i ++){
                input = Matrix.getColumn(blueInput, i);
                nn.backprop(input, blueExpectedOutput);
            }
        }
//        for(int i = 0; i < redInput[0].length; i ++){
//            input = Matrix.getColumn(redInput, i);
//            for(int j = 0; j < 1; j ++){
//                nn.backprop(input, redExpectedOutput);
//            }
//        }
//
//        //training blue color recognition
//        for(int i = 0; i < blueInput[0].length; i ++){
//            input = Matrix.getColumn(blueInput, i);
//            for(int j = 0; j < 1; j ++){
//                nn.backprop(input, blueExpectedOutput);
//            }
//        }

        double [][] testInput1 = {{0.035}, {0.184}, {0.603}}; //blue
        double [][] ffoutput = nn.feedforward(testInput1);
        System.out.println("Feedforward testing input1 for blue output: \n" + Matrix.print(ffoutput));
        System.out.println("-------------------------------------------------------");

        double [][] testInput2 = {{.874}, {.125}, {.176}};  //red
        ffoutput = nn.feedforward(testInput2);
        System.out.println("Feedforward testing input2 for red output: \n" + Matrix.print(ffoutput));
        System.out.println("-------------------------------------------------------");

        double [][] testInput3 = {{0.890}, {0.984}, {0.074}};  //yellow
        ffoutput = nn.feedforward(testInput3);
        System.out.println("Feedforward testing input3 for yellow output: \n" + Matrix.print(ffoutput));
        System.out.println("-------------------------------------------------------");

        double [][] testInput4 = {{.207}, {.952}, {.243}};  //green
        ffoutput = nn.feedforward(testInput4);
        System.out.println("Feedforward testing input4 for green output: \n" + Matrix.print(ffoutput));
        System.out.println("-------------------------------------------------------");

        double [][] testInput5 = {{0.937}, {0.207}, {0.952}};  //purple
        ffoutput = nn.feedforward(testInput5);
        System.out.println("Feedforward testing input5 for purple output: \n" + Matrix.print(ffoutput));
        System.out.println("-------------------------------------------------------");


        //NEW DATA TRAINED
        nn.trainNewData(testInput3);

        System.out.println("Testing with new trained network \n\n\n\n\n");



        //double [][] testInput1 = {{0.035}, {0.184}, {0.603}}; //blue
        ffoutput = nn.feedforward(testInput1);
        System.out.println("Feedforward testing input1 for blue output: \n" + Matrix.print(ffoutput));
        System.out.println("-------------------------------------------------------");

        //double [][] testInput2 = {{.874}, {.125}, {.176}};  //red
        ffoutput = nn.feedforward(testInput2);
        System.out.println("Feedforward testing input2 for red output: \n" + Matrix.print(ffoutput));
        System.out.println("-------------------------------------------------------");

        //double [][] testInput3 = {{0.890}, {0.984}, {0.074}};  //yellow
        ffoutput = nn.feedforward(testInput3);
        System.out.println("Feedforward testing input3 for yellow output: \n" + Matrix.print(ffoutput));
        System.out.println("-------------------------------------------------------");

        //double [][] testInput4 = {{.207}, {.952}, {.243}};  //green
        ffoutput = nn.feedforward(testInput4);
        System.out.println("Feedforward testing input4 for green output: \n" + Matrix.print(ffoutput));
        System.out.println("-------------------------------------------------------");

        //double [][] testInput5 = {{0.937}, {0.207}, {0.952}};  //purple
        ffoutput = nn.feedforward(testInput5);
        System.out.println("Feedforward testing input5 for purple output: \n" + Matrix.print(ffoutput));
        System.out.println("-------------------------------------------------------");
    }
}
