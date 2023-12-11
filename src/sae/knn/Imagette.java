package sae.knn;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

public class Imagette {

    public int[][] imgTab;
    public int etiquette;

    public Imagette(DataInputStream imageFile, int nbLignes, int nbCols, int etiquette) {
        try {
            this.imgTab = new int[nbLignes][nbCols];
            for (int j = 0; j < nbLignes; j++) {
                for (int k = 0; k < nbCols; k++) {
                    imgTab[k][j] = imageFile.readUnsignedByte();
                }
            }
            this.etiquette = etiquette;
        } catch (Exception e) {
            System.out.println("Error while reading image file");
        }
    }

    /*public void saveImg(String fileName) throws IOException {
        BufferedImage img = new BufferedImage(this.imgTab.length, this.imgTab[0].length, BufferedImage.TYPE_INT_RGB);
        for (int j = 0; j < this.imgTab.length; j++) {
            for (int k = 0; k < this.imgTab[0].length; k++) {
                int rgb = imgTab[j][k] << 16 | imgTab[j][k] << 8 | imgTab[j][k];
                img.setRGB(j, k, rgb);
            }
        }
        ImageIO.write(img, "png", new File("./img/" + fileName + ".png"));
    }*/

}
