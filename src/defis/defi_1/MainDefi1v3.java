package defis.defi_1;

import defis.defi_1.structure.Node;
import defis.defi_1.structure.Position;
import defis.defi_1.structure.Ville;
import defis.defi_1.tool.Loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MainDefi1v3 {
    public static void main(String[] args) {
        HashMap<String, Ville> villes = Loader.loadArraysLocal("ressources/villes.json");
        double latitude_depart = 40;
        double longitude_depart = 3;

        double latitude_arrivee = 48.6;
        double longitude_arrivee = 7;

        int mode = 1;

        if(mode == 1){
            for(Ville ville : villes.values()){
                ville.setCoefficientClass(3);
            }
        }

        Position depart = new Position(longitude_depart, latitude_depart);
        Position arrivee = new Position(longitude_arrivee, latitude_arrivee);

        ArrayList<String> children = new ArrayList<>(villes.keySet());

        ArrayList<Node> frontier = new ArrayList<>();
        Node node_depart = new Node("Depart", 0, null, children);
        Ville ville_depart = new Ville("Depart");
        ville_depart.setPosition(depart);
        ville_depart.setCoefficientClass(3);
        villes.put("Depart", ville_depart);

        Ville ville_arrivee = new Ville("Arrivée");
        ville_arrivee.setPosition(arrivee);
        ville_arrivee.setCoefficientClass(3);
        villes.put("Arrivée", ville_arrivee);


        frontier.add(node_depart);

        HashSet<Node> explored = new HashSet<>();

        Node end_node = null;

        while(!frontier.isEmpty()){
            Node node = frontier.removeFirst();
            explored.add(node);

            if(node.getNodeEnfants().isEmpty()){
                end_node = node;
                break;
            }

            for(String child : node.getNodeEnfants()){
                Node childNode = new Node(child);
                childNode.setNodeParent(node);

                ArrayList<String> list_children = new ArrayList<>(node.getNodeEnfants());
                list_children.remove(child);
                childNode.setNodeEnfants(list_children);

                childNode.setDistance(node.getDistance() +
                        Position.distanceEntre(
                                villes.get(node.getNom()).getPosition(), villes.get(child).getPosition()
                        ) * Math.min(villes.get(node.getNom()).getCoefficient(), villes.get(child).getCoefficient()));
                childNode.setDistance_heuristique(Position.distanceEntre(villes.get(child).getPosition(), arrivee));

                if (!explored.contains(childNode) && !frontier.contains(childNode)){

                    int i = 0;
                    while(i < frontier.size()){
                        Node n = frontier.get(i);
                        if(n.getDistance() + n.getDistance_heuristique() > childNode.getDistance() + childNode.getDistance_heuristique()){
                            frontier.add(i, childNode);
                            break;
                        }
                        i++;
                    }
                    if(i == frontier.size()){
                        frontier.add(childNode);
                    }
                }
            }
        }

        Node node_arrivee = new Node("Arrivée",
                end_node.getDistance() + Position.distanceEntre(arrivee, villes.get(end_node.getNom()).getPosition()) * 1,
                end_node,
                null);


        double total_distance = 0;
        while(node_arrivee.getNodeParents() != null){
            System.out.println(node_arrivee.getNom() + " [ " + villes.get(node_arrivee.getNom()).getPosition().getPosDegree()+ " ]");
            double temps_distance = Position.distanceEntre(villes.get(node_arrivee.getNom()).getPosition(), villes.get(node_arrivee.getNodeParents().getNom()).getPosition());
            total_distance += temps_distance;
            System.out.println("↑ " + temps_distance + " km");
            node_arrivee = node_arrivee.getNodeParents();
        }

        System.out.println("Départ [longitude: " + longitude_depart + " | latitude: " + latitude_depart + "]");
        System.out.println("Total distance: " + total_distance + " km");

    }
}
