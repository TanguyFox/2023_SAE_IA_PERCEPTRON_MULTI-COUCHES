import sae.function.SigmoideFunction;
import sae.function.TangeanteHyperboliqueFunction;
import sae.function.TransferFunction;
import sae.mlp.MLP;

import java.util.Arrays;

public class MainMLP {

    public static void main(String[] args) {
        double learningRate = 0.03;

        TransferFunction transferFunction = new SigmoideFunction();
        int[] layers = new int[]{3, 1};
        MLP mlp = new MLP(layers, learningRate, transferFunction);

        double[][] inputs_In = new double[][]{
                {0, 0, 0},
                {0, 0, 1},
                {0, 1, 0},
                {0, 1, 1},
                {1, 0, 0},
                {1, 0, 1},
                {1, 1, 0},
                {1, 1, 1},
        };

        double[][] outputs_ET = new double[][]{
                {0},
                {0},
                {0},
                {0},
                {0},
                {0},
                {0},
                {1},
        };

        double[][] outputs_OR = new double[][]{
                {0},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
        };

        double[][] outputs_XOR = new double[][]{
                {1},
                {0},
                {0},
                {0},
                {0},
                {0},
                {0},
                {0},
        };

        int numEpochs = 1000000;

        double[][] inputs = inputs_In;
        double[][] outputs = outputs_XOR;

        for(int epoch = 0; epoch < numEpochs; epoch++){
            double totalError = 0;

            for(int i = 0; i < inputs.length; i++){
                double[] input = inputs[i];
                double[] output = outputs[i];
                double error = mlp.backPropagate(input, output);
                totalError += error;
            }

            double averageError = totalError / inputs.length;

            System.out.println("Epoch = " + epoch + " Error = " + averageError);

        }

        double[] prediction = mlp.execute(new double[]{1, 1, 0});
        System.out.println(Arrays.toString(prediction));


    }
}
