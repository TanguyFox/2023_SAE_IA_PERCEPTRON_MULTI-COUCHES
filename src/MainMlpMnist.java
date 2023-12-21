import sae.function.TransferFunction;
import sae.mlp.MLP;
import sae.tools.ArgParse;
import sae.mnist.*;
import sae.tools.ExportToCSV;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainMlpMnist {

    public static void main(String[] args) throws IOException {

        ArgParse.setUsage("Utilisation :\n\n"
                + "java MainMLP [-des output] [-func transferFunc] [-lay layersTab] [-lr learningRate] [-max maxRep]"
                + "[-h]\n"
                + "-func : La fonction de transfert {sig, tanh}. Par défault sig\n"
                + "-lay : Le tableau des couches {1,2,3}. Par défaut [2,1]  mais doit être configuré comme [784,...,10]. ATTENTION : ne pas mettre d'espace entre les éléments du tableau\n"
                + "-max : Le nombre maximum d'itérations. Par défaut 5000\n"
                + "-lr : Le taux d'apprentissage. Par défaut 0.6\n"
                + "-h    : afficher ceci (mettre à la fin)"
        );


        EtiquettesList trainingEtiquettesList = new EtiquettesList(new DataInputStream(new FileInputStream("./ressources/mnist/train-labels.idx1-ubyte")));
        Donnees trainingData = new Donnees();

        DataInputStream imageFile = new DataInputStream(new FileInputStream("./ressources/mnist/train-images.idx3-ubyte"));
        int typeFichier = imageFile.readInt();
        int nbImages = imageFile.readInt();
        int nbLignes = imageFile.readInt();
        int nbCols = imageFile.readInt();

        System.out.println("Processing training data...");

        int compteur = 0;
        while (compteur < nbImages) {
            Imagette trainingImagette = new Imagette(imageFile, nbLignes, nbCols, trainingEtiquettesList.labels.get(compteur));
            trainingData.imagettes.add(trainingImagette);
            compteur++;
        }
        imageFile.close();
        System.out.println("Training data processed.");

        System.out.println("Processing test data...");
        Donnees testData = new Donnees();
        EtiquettesList etiquettesList = new EtiquettesList(new DataInputStream(new FileInputStream("./ressources/mnist/t10k-labels.idx1-ubyte")));
        DataInputStream imageFileTest = new DataInputStream(new FileInputStream("./ressources/mnist/t10k-images.idx3-ubyte"));
        int typeFichierTest = imageFileTest.readInt();
        int nbImagesTest = imageFileTest.readInt();
        int nbLignesTest = imageFileTest.readInt();
        int nbColsTest = imageFileTest.readInt();
        int compteurTest = 0;


        while (compteurTest < nbImagesTest) {
            Imagette testImagette = new Imagette(imageFileTest, nbLignesTest, nbColsTest, etiquettesList.labels.get(compteurTest));
            testData.imagettes.add(testImagette);
            compteurTest++;
        }
        imageFileTest.close();

        System.out.println("Test data processed.");
        double learningRate = ArgParse.getLearningRate(args);
        String func = ArgParse.getFunctionFromCmd(args);
        String layers = ArgParse.getLayersFromCmd(args);


        int[] layersInt = ArgParse.makeLayers(layers);
        TransferFunction transferFunction = ArgParse.makeFunction(func);

        MLP mlp = new MLP(layersInt, learningRate, transferFunction);

        int maxRep = ArgParse.getMaxRep(args);

        int nbInterTest = 1;

        Statistiques stats = new Statistiques();
        double reussiteTrain = 0;
        double reussiteTest = 0;


        System.out.println("Start learning...");
        while (reussiteTest < 98 && nbInterTest <= maxRep) {
            System.out.println("Shuffling data...");
            Collections.shuffle(trainingData.imagettes);

            double averageError = 0;

            //Ces deux lignes ci-dessous permettent de mélanger les données d'apprentissage. Les commentées pour ne pas les mélanger.
            System.out.println("Iteration " + nbInterTest + " start");
            double error = 0;
            for (int i = 0; i < 10; i++) {

                for (Imagette imagette : trainingData.imagettes) {
                    error += mlp.backPropagate(imagette.getPixels(), imagette.getOuput());
                }
            }

            System.out.println("\n\t- Erreur sur l'apprentissage : " + error);

            ///Réalisation des statistiques de reconnaissance sur la base d'entrainement
            reussiteTrain = stats.calculerStats(trainingData, mlp);
            System.out.println("\t- Taux de réussite sur la base d'apprentissage : " + reussiteTrain + "%");

            ///Réalisation des statistiques de reconnaissance sur la base de test
            reussiteTest = stats.calculerStats(testData, mlp);

            System.out.println("\t- Taux de réussite sur la base de test : " + reussiteTest + "%");
            nbInterTest++;
        }


        if (reussiteTest >= 98) {
            System.out.println("Le réseau a atteint un taux de réussite d'au moins 98% sur la base de test.");
        } else {
            System.out.println("Le réseau n'a pas atteint un taux de réussite de 98% sur la base de test.");
        }
    }
}
