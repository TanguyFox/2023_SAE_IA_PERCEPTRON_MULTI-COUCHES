package sae.mnist;

import java.io.DataInputStream;
import java.util.Arrays;

public class Imagette {

    public double[][] imgTab;
    public int etiquette;

   public Imagette(DataInputStream imageFile, int nbLignes, int nbCols, int etiquette) {
        try {
            this.imgTab = new double[nbLignes][nbCols];
            for (int j = 0; j < nbLignes; j++) {
                for (int k = 0; k < nbCols; k++) {
                    imgTab[k][j] = imageFile.readUnsignedByte() / 255.0;
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

    public double[] getPixels() {
        return Arrays.stream(this.imgTab).flatMapToDouble(Arrays::stream).toArray();
    }

    public double[] getOuput() {
        switch (this.etiquette) {
            case 0:
                return new double[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            case 1:
                return new double[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
            case 2:
                return new double[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 0};
            case 3:
                return new double[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 0};
            case 4:
                return new double[]{0, 0, 0, 0, 1, 0, 0, 0, 0, 0};
            case 5:
                return new double[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
            case 6:
                return new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0};
            case 7:
                return new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0};
            case 8:
                return new double[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
            case 9:
                return new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
            default:
                return null;
        }
    }

}
