package defis.defi_1.structure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Node {

    private String nom_ville;

    private Node nodeParent;

    private ArrayList<String> nodeEnfants;

    private double distance;

    public Node(String nom_ville, double distance, Node nodeParent, ArrayList<String> nodeEnfants) {
        this.nom_ville = nom_ville;
        this.nodeEnfants = nodeEnfants;
        this.nodeParent = nodeParent;
        this.distance = distance;
    }

    public String getNom_ville() {
        return nom_ville;
    }

    public double getDistance() {
        return distance;
    }

    public ArrayList<String> getNodeEnfants() {
        return nodeEnfants;
    }

    public Node getNodeParents() {
        return nodeParent;
    }

}
