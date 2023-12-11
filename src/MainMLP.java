import sae.function.SigmoideFunction;
import sae.function.TangeanteHyperboliqueFunction;
import sae.function.TransferFunction;
import sae.mlp.MLP;
import sae.tools.ArgParse;
import sae.tools.Constantes;

import java.util.Arrays;
import java.util.Random;

public class MainMLP {

    public static void main(String[] args) {

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

        double learningRate = ArgParse.getLearningRate(args);
        String func = ArgParse.getFunctionFromCmd(args);
        String layers = ArgParse.getLayersFromCmd(args);
        String out = ArgParse.getTabFromCmd(args);

        double[][] inputs = Constantes.INPUT_BINARY_2;
        double[][] outputs = ArgParse.makeOutput(out);
        int[] layersInt = ArgParse.makeLayers(layers);
        TransferFunction transferFunction = ArgParse.makeFunction(func);

        MLP mlp = new MLP(layersInt, learningRate, transferFunction);

        //System.out.println("Learning rate : " + learningRate);
        //System.out.println("Fonction d'activation : " + transferFunction);
        //System.out.println("Table : " + Arrays.deepToString(outputs));
        //System.out.println("Layers : " + Arrays.toString(layersInt));

        int maxRep = ArgParse.getMaxRep(args);

        //* vrai si tous les exemples passent sans erreur *//*
        mlp.train(inputs, outputs, maxRep);
    }
}