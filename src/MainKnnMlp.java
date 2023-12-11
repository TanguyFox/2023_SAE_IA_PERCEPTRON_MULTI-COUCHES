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


        EtiquettesList trainingEtiquettesList = new EtiquettesList(new DataInputStream(new FileInputStream("./imagettes/train-labels.idx1-ubyte")));
        Donnees trainingData = new Donnees();

        DataInputStream imageFile = new DataInputStream(new FileInputStream("./imagettes/train-images.idx3-ubyte"));
        int typeFichier = imageFile.readInt();
        int nbImages = imageFile.readInt();
        int nbLignes = imageFile.readInt();
        int nbCols = imageFile.readInt();

        int compteur = 0;
        while (compteur < 50) {
            Imagette trainingImagette = new Imagette(imageFile, nbLignes, nbCols, trainingEtiquettesList.labels.get(compteur));
            trainingData.imagettes.add(trainingImagette);
            compteur++;
        }
        imageFile.close();


        Donnees testData = new Donnees();
        EtiquettesList etiquettesList = new EtiquettesList(new DataInputStream(new FileInputStream("./imagettes/t10k-labels.idx1-ubyte")));
        DataInputStream imageFileTest = new DataInputStream(new FileInputStream("./imagettes/t10k-images.idx3-ubyte"));
        int typeFichierTest = imageFileTest.readInt();
        int nbImagesTest = imageFileTest.readInt();
        int nbLignesTest = imageFileTest.readInt();
        int nbColsTest = imageFileTest.readInt();
        int compteurTest = 0;


        while (compteurTest < 10) {
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

    }

}
