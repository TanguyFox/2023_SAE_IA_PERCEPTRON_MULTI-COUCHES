import defis.defi_1_old.structure.Node;
import defis.defi_1_old.structure.Position;
import defis.defi_1_old.structure.Ville;
import defis.defi_1_old.tool.Loader;

import java.util.*;

public class MainAStar {
    public static void main(String[] args) {
        HashMap<String, Ville> villes = Loader.loadArraysLocal("ressources/villes.json", 100);

        double latitude_depart = 48.17146344840425;
        double longitude_depart = 6.450965041214875;
        double latitude_arrivee = 40;
        double longitude_arrivee = 0;

        int mode = 1;

        if(mode == 1){
            for(Ville ville : villes.values()){
                ville.setCoefficientClass(3);
            }
        }

        double startTime = System.currentTimeMillis();

        Position depart = new Position(longitude_depart, latitude_depart);
        Position arrivee = new Position(longitude_arrivee, latitude_arrivee);

        Ville ville_arrivee = new Ville("Arrivée");
        ville_arrivee.setPosition(arrivee);
        ville_arrivee.setCoefficientClass(3);
        villes.put("Arrivée", ville_arrivee);

        ArrayList<String> cities = new ArrayList<>(villes.keySet());

        Ville ville_depart = new Ville("Depart");
        ville_depart.setPosition(depart);
        ville_depart.setCoefficientClass(3);
        villes.put("Depart", ville_depart);

        Node node_depart = new Node("Depart", 0, null, cities);

        Comparator<Node> nodeComparator = Comparator.comparingDouble(Node::getTotalDistance);
        PriorityQueue<Node> frontier = new PriorityQueue<>(nodeComparator);

        frontier.add(node_depart);

        HashSet<Node> explored = new HashSet<>();

        Node end_node = null;

        while(!frontier.isEmpty()){
            Node node = frontier.poll();
            explored.add(node);

            if(node.getNom().equals("Arrivée")){
                end_node = node;
                break;
            }

            for(String child : node.getNodeEnfants()){

                if(node.getNom().equals("Depart") && child.equals("Arrivée")){
                    continue;
                }

                Node childNode = new Node(child);
                childNode.setNodeParent(node);

                ArrayList<String> list_children = new ArrayList<>(node.getNodeEnfants());
                list_children.remove(child);
                childNode.setNodeEnfants(list_children);

                double distance = node.getDistance() +
                        Position.distanceEntre(
                                villes.get(node.getNom()).getPosition(), villes.get(child).getPosition()
                        ) * Math.min(villes.get(node.getNom()).getCoefficient(), villes.get(child).getCoefficient());
                childNode.setDistance(distance);



                double distance_heuristique = Position.distanceEntre(villes.get(child).getPosition(), arrivee);
                childNode.setDistance_heuristique(distance_heuristique);

//                System.out.println(node.getNom() + " -> " + child + " : " + distance);
//                System.out.println("--------------Distance heuristique: " + distance_heuristique);
//                System.out.println("--------------Total distance: " + (distance + distance_heuristique));

                if (!explored.contains(childNode) && !frontier.contains(childNode)){
                    frontier.add(childNode);
                }
            }
        }

        double endTime = System.currentTimeMillis();

        double total_distance = 0;
        while(end_node.getNodeParents() != null){
            System.out.println(end_node.getNom() + " [ " + villes.get(end_node.getNom()).getPosition().getPosDegree()+ " ]");
            double temps_distance = Position.distanceEntre(villes.get(end_node.getNom()).getPosition(), villes.get(end_node.getNodeParents().getNom()).getPosition());
            total_distance += temps_distance;
            System.out.println("↑ " + temps_distance + " km");
            end_node = end_node.getNodeParents();
        }
        System.out.println("Départ [longitude: " + longitude_depart + " | latitude: " + latitude_depart + "]");
        System.out.println("Total distance: " + total_distance + " km");
        System.out.println("Temps d'exécution: " + (endTime - startTime) + " ms");
    }
}
