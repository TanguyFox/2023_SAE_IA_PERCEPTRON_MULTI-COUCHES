package defis.defi1.structure;

import java.util.ArrayList;
import java.util.Objects;

public class Node {

    private String nom_ville;

    private Node nodeParent;

    private ArrayList<String> nodeEnfants;

    private double distance;

    private double distance_heuristique;

    public Node(String nom_ville, double distance, Node nodeParent, ArrayList<String> nodeEnfants) {
        this.nom_ville = nom_ville;
        this.nodeEnfants = nodeEnfants;
        this.nodeParent = nodeParent;
        this.distance = distance;
    }

    public Node(String nom_ville){
        this.nom_ville = nom_ville;
    }

    public String getNom() {
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

    public double getDistance_heuristique() {
        return distance_heuristique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node node)) return false;
        return Objects.equals(nom_ville, node.nom_ville);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom_ville);
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setNodeEnfants(ArrayList<String> nodeEnfants) {
        this.nodeEnfants = nodeEnfants;
    }

    public void setNodeParent(Node nodeParent) {
        this.nodeParent = nodeParent;
    }

    public void setNom_ville(String nom_ville) {
        this.nom_ville = nom_ville;
    }

    public void setDistance_heuristique(double distance_heuristique) {
        this.distance_heuristique = distance_heuristique;
    }

    public double getTotalDistance(){
        return this.distance + this.getDistance_heuristique();
    }
}
