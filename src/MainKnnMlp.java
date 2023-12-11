import sae.function.TransferFunction;
import sae.mlp.MLP;
import sae.tools.ArgParse;
import sae.knn.*;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class MainKnnMlp {

    public static void main(String[] args) throws IOException {

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


        EtiquettesList trainingEtiquettesList = new EtiquettesList(new DataInputStream(new FileInputStream("./knn_ressources/train-labels.idx1-ubyte")));
        Donnees trainingData = new Donnees();

        DataInputStream imageFile = new DataInputStream(new FileInputStream("./knn_ressources/train-images.idx3-ubyte"));
        int typeFichier = imageFile.readInt();
        int nbImages = imageFile.readInt();
        int nbLignes = imageFile.readInt();
        int nbCols = imageFile.readInt();

        int compteur = 0;
        while (compteur < 6000) {
            Imagette trainingImagette = new Imagette(imageFile, nbLignes, nbCols, trainingEtiquettesList.labels.get(compteur));
            trainingData.imagettes.add(trainingImagette);
            compteur++;
        }
        imageFile.close();


        Donnees testData = new Donnees();
        EtiquettesList etiquettesList = new EtiquettesList(new DataInputStream(new FileInputStream("./knn_ressources/t10k-labels.idx1-ubyte")));
        DataInputStream imageFileTest = new DataInputStream(new FileInputStream("./knn_ressources/t10k-images.idx3-ubyte"));
        int typeFichierTest = imageFileTest.readInt();
        int nbImagesTest = imageFileTest.readInt();
        int nbLignesTest = imageFileTest.readInt();
        int nbColsTest = imageFileTest.readInt();
        int compteurTest = 0;


        while (compteurTest < 1000) {
            Imagette testImagette = new Imagette(imageFileTest, nbLignesTest, nbColsTest, etiquettesList.labels.get(compteurTest));
            testData.imagettes.add(testImagette);
            compteurTest++;
        }
        imageFileTest.close();


        double learningRate = ArgParse.getLearningRate(args);
        String func = ArgParse.getFunctionFromCmd(args);
        String layers = ArgParse.getLayersFromCmd(args);


        int[] layersInt = ArgParse.makeLayers(layers);
        TransferFunction transferFunction = ArgParse.makeFunction(func);

        MLP mlp = new MLP(layersInt, learningRate, transferFunction);

        int maxRep = ArgParse.getMaxRep(args);

        boolean appris = false;
        int nbInter = 0;
        //* vrai si tous les exemples passent sans erreur *//*


        Boolean[] apprentissage = new Boolean[inputs.length];
        Arrays.fill(apprentissage, false);

        while(Arrays.asList(apprentissage).contains(false) && nbInter < maxRep){
            for(int i =0; i< 10; i++) {
                for (int j = 0; j < inputs.length; j++) {
                    mlp.backPropagate(inputs[j], imagette.getOuput());
                }
            }

            for (int k = 0; k < inputs.length; k++) {
                double[] output = mlp.execute(inputs[k]);
                for (int i = 0; i < output.length; i++) {
                    if(Math.abs(output[i] - imagette.getOuput()[i]) < 0.1) {
                        System.out.println("Sortie : " + Arrays.toString(output) + " Sortie désirée : " + Arrays.toString(imagette.getOuput()));
                        apprentissage[k] = true;
                    }
                }
            }

            nbInter++;
        }
    }
        if (nbInter < maxRep) {
            System.out.println("\nApprentissage réussi\n");
        } else {
            System.out.println("Apprentissage échoué");
        }
        System.out.println("Nombre d'interations : " + nbInter);

        System.out.println("Test : ");
        System.out.println(Arrays.toString(mlp.execute(testData.imagettes.get(0).imgTab[0])) + " attendu : " + testData.imagettes.get(0).etiquette);

    }

}
