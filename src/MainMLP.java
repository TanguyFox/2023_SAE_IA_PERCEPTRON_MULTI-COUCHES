import sae.function.TransferFunction;
import sae.mlp.MLP;
import sae.tools.ArgParseMLP;
import sae.tools.Constantes;

import java.util.Arrays;

public class MainMLP {

    public static void main(String[] args) {

        ArgParseMLP.setUsage("Utilisation :\n\n"
                + "java MainMLP [-des output] [-func transferFunc] [-lay layersTab] [-lr learningRate] [-max maxRep] [-h]\n"
                + "-func : La fonction de transfert {sig, tanh}. Par défault sig\n"
                + "-lay : Le tableau des couches {1,2,3}. Par défaut [2,1]. ATTENTION : ne pas mettre d'espace entre les éléments du tableau\n"
                + "-max : Le nombre maximum d'itérations. Par défaut 5000\n"
                + "-lr : Le taux d'apprentissage. Par défaut 0.6\n"
                + "-h    : afficher ceci (mettre à la fin)"
        );

        double learningRate = ArgParseMLP.getLearningRate(args);
        String func = ArgParseMLP.getFunctionFromCmd(args);
        String layers = ArgParseMLP.getLayersFromCmd(args);
        String out = ArgParseMLP.getTabFromCmd(args);

        //Configuration des tableau d'entrées et de sorties
        //Pour changer ces configurations, voir les constantes dans la classe tool/Constantes.java
        double[][] inputs = Constantes.INPUT_BINARY2_SHUFFLE;
        double[][] outputs = Constantes.OUTPUT_BINARY2_AND_SHUFFLE;


        int[] layersInt = ArgParseMLP.makeLayers(layers);
        TransferFunction transferFunction = ArgParseMLP.makeFunction(func);

        long startTime = System.currentTimeMillis();

        MLP mlp = new MLP(layersInt, learningRate, transferFunction);



        int maxRep = ArgParseMLP.getMaxRep(args);

        int nbInter = 0;
        //* vrai si tous les exemples passent sans erreur *//*

        Boolean[] apprentissage = new Boolean[inputs.length];
        Arrays.fill(apprentissage, false);

        while (Arrays.asList(apprentissage).contains(false) && nbInter < maxRep) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < inputs.length; j++) {
                    mlp.backPropagate(inputs[j], outputs[j]);
                }
            }

            for (int k = 0; k < inputs.length; k++) {
                double[] output = mlp.execute(inputs[k]);
                boolean check = true;
                for (int l = 0; l < mlp.getOutputLayerSize(); l++) {
                    if (!(Math.abs(output[l] - outputs[k][l]) < 0.1)) {
                        check = false;
                    }
                }
                if (check) {
                    apprentissage[k] = true;
                }
            }

            nbInter++;
        }

        long endTime;

        if (nbInter < maxRep) {
            endTime = System.currentTimeMillis();
            System.out.println("\nApprentissage réussi\n");
            System.out.println("Etat final : ");
            for (int i = 0; i < inputs.length; i++) {
                System.out.println("\tInput : " + Arrays.toString(inputs[i]) + " Output : " + Arrays.toString(mlp.execute(inputs[i])) + " Output désiré : " + Arrays.toString(outputs[i]));
            }
            System.out.println("Poids finaux : ");
        } else {
            endTime = System.currentTimeMillis();
            System.out.println("Apprentissage échoué");
        }

        System.out.println("Nombre d'interations : " + nbInter);
        System.out.println("Temps d'execution : " + (endTime - startTime) + " ms");


    }
}