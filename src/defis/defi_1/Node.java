package defis.defi_1;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class Node {

    private String nom_ville;
    private int classe;

    private double coeficient;

    public Node(String nom_ville, int classe) {
        this.nom_ville = nom_ville;
        this.classe = classe;
        switch (classe){
            case 1 -> coeficient = 0.6;
            case 2 -> coeficient = 0.8;
            case 3 -> coeficient = 1;
            default -> throw new IllegalArgumentException("Classe inconnue");
        }
    }


}
