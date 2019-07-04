public class NetworkTester {
    public static void main(String [] args){
        Network nn = new Network(3, 4, 2);

        System.out.println("Getting biases in hidden layer: \n" + nn.getBiasesHidden());
        System.out.println("-------------------------------------------------------");

        System.out.println("Getting biases in outer layer: \n" + nn.getBiasesOuter());
        System.out.println("-------------------------------------------------------");

        System.out.println("Getting weights in hidden layer: \n" + nn.getWeightsHidden());
        System.out.println("-------------------------------------------------------");

        System.out.println("Getting weights in outer layer: \n" + nn.getBiasesOuter());
        System.out.println("-------------------------------------------------------");


    }
}
