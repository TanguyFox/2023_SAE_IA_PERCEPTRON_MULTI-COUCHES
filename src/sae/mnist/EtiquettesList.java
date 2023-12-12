package sae.mnist;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EtiquettesList {

    public List<Integer> labels = new ArrayList<>();

    public EtiquettesList(DataInputStream etiquettesFile) throws IOException {
        int typeFichier = etiquettesFile.readInt();
        int nbImages = etiquettesFile.readInt();

        int compteur = 0;
        while (compteur < nbImages) {
            labels.add(etiquettesFile.readUnsignedByte());
            compteur++;
        }
    }
}
