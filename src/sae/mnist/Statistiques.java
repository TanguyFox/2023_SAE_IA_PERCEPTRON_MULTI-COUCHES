package sae.mnist;

import sae.mlp.MLP;

import java.util.Arrays;

public class Statistiques {

    public double calculerStats(Donnees data, MLP mlp) {
        double nbCorrect = 0;
        double nbIncorrect = 0;
        for (Imagette imagette : data.imagettes) {
            double[] pixels = Arrays.stream(imagette.imgTab).flatMapToDouble(Arrays::stream).toArray();
            double[] output = mlp.execute(pixels);
            double max = Double.NEGATIVE_INFINITY;
            int index = 0;
            for (int l = 0; l < mlp.getOutputLayerSize(); l++) {
                if (output[l] > max) {
                    max = output[l];
                    index = l;
                }
            }
            if(index == imagette.etiquette) {
                nbCorrect++;
            } else {
                nbIncorrect++;
            }
        }
        System.out.println("\n\tNombre d'images correctement classifiées : " + nbCorrect);
        System.out.println("\n\tNombre d'images incorrectement classifiées : " + nbIncorrect);
        return (nbCorrect * 100) / (nbCorrect + nbIncorrect);
    }
}
