import sae.function.TangeanteHyperboliqueFunction;
import sae.function.TransferFunction;
import sae.mlp.MLP;

public class MainMLP {

    public static void main(String[] args) {
        double learningRate = 0.1;
        TransferFunction transferFunction = new TangeanteHyperboliqueFunction();
        int[] layers = new int[]{2, 2};
        MLP mlp = new MLP(layers, learningRate, transferFunction);
        double[][] inputs = new double[][]{
                new double[]{0, 0},
                new double[]{0, 1},
                new double[]{1, 0},
                new double[]{1, 1}
        };

        double[] sortiesS_ET = new double[]{0, 0, 0, 1};
        double[] sortiesS_OR = new double[]{0, 1, 1, 1};
        double[] sortiesS_XOR = new double[]{1, 0, 0, 0};



//        for(double[] input : inputs) {
//            double error = mlp.backPropagate(new double[]{0, 0}, new double[]{0});
//            System.out.println("Error : " + error);
//        }

        double error = mlp.backPropagate(new double[]{0, 1, 1}, new double[]{0, 1, 1});
        System.out.println("Error : " + error);

    }
}
