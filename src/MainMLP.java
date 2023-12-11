import sae.function.SigmoideFunction;
import sae.function.TangeanteHyperboliqueFunction;
import sae.function.TransferFunction;
import sae.mlp.MLP;
import sae.tools.ArgParse;

import java.util.Arrays;
import java.util.Random;

public class MainMLP {

    public static void main(String[] args) {

        double[][] inputs_In = new double[][]{
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1},
        };

        ArgParse.setUsage("Utilisation :\n\n"
                + "java MainMLP [-des output] [-func transferFunc] [-lay layersTab] [-lr learningRate] [-max maxRep]"
                + "[-v] [-h]\n"
                + "-des : Le tableau de sortie désiré {and, or, xor}. Par défaut and\n"
                + "-func : La fonction de transfert {sig, tanh}. Par défault sig\n"
                + "-lay : Le tableau des couches {1, 2, 3}. Par défaut [2, 1]\n"
                + "-max : Le nombre maximum d'itérations. Par défaut 5000\n"
                + "-lr : Le taux d'apprentissage. Par défaut 0.6\n"
                + "-v    : Rendre bavard (mettre à la fin)\n"
                + "-h    : afficher ceci (mettre à la fin)");

        /* double learningRate = 0.03;

        TransferFunction transferFunction = new SigmoideFunction();
        int[] layers = new int[]{2, 1};
        MLP mlp = new MLP(layers, learningRate, transferFunction);



        double[][] outputs_ET = new double[][]{
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
        };

        double[][] outputs_XOR = new double[][]{
                {0},
                {1},
                {1},
                {0},
        };*/

        double learningRate = ArgParse.getLearningRate(args);
        String func = ArgParse.getFunctionFromCmd(args);
        String layers = ArgParse.getLayersFromCmd(args);
        String out = ArgParse.getTabFromCmd(args);


        double[][] inputs = inputs_In;
        double[][] outputs = ArgParse.makeOutput(out);
        int[] layersInt = ArgParse.makeLayers(layers);
        TransferFunction transferFunction = ArgParse.makeFunction(func);

        MLP mlp = new MLP(layersInt, learningRate, transferFunction);

        //System.out.println("Learning rate : " + learningRate);
        //System.out.println("Fonction d'activation : " + transferFunction);
        //System.out.println("Table : " + Arrays.deepToString(outputs));
        //System.out.println("Layers : " + Arrays.toString(layersInt));


        int maxRep = ArgParse.getMaxRep(args);
        int nbRep = 0;

//* vrai si tous les exemples passent sans erreur *//*
        boolean appris = false;
        boolean[] apprentissage = new boolean[inputs.length];
        Arrays.fill(apprentissage, false);


        while (!appris && nbRep < maxRep) {

            Random random = new Random();
            int ligne = random.nextInt(apprentissage.length);

            while (apprentissage[ligne]) {
                ligne = random.nextInt(apprentissage.length);
            }

            double sortie = mlp.backPropagate(inputs[ligne], outputs[ligne]);

            if (sortie < 0.1) {
                apprentissage[ligne] = true;
                for (boolean b : apprentissage) {
                    if (!b) {
                        appris = false;
                        break;
                    }
                    appris = true;
                }
            } else {
                Arrays.fill(apprentissage, false);
            }
            if (nbRep % 100 == 0) System.out.println("Différence sortie désirée / sortie obtenue : " + sortie);
            nbRep++;
        }
        if (appris) {
            System.out.println("Appris !");
        } else {
            System.out.println("Non appris !");
        }
        System.out.println("Nombre d'iterations : " + nbRep);


        /*while (nbRep < maxRep && !appris) {
            double totalError = 0;

            for (int i = 0; i < inputs.length; i++) {
                double[] input = inputs[i];
                double[] output = outputs[i];
                double error = mlp.backPropagate(input, output);
                totalError += error;
            }
            double averageError = totalError / inputs.length;

            System.out.println("Epoch = " + epoch + " Error = " + averageError);

            nbRep++;

        }

        double[] prediction = mlp.execute(new double[]{1, 1, 0});
        System.out.println(Arrays.toString(prediction));
*/
    }
}