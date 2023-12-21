import defis.defi1.defi1_old.structure.Node;
import defis.defi1.defi1_old.structure.Position;
import defis.defi1.defi1_old.structure.Ville;
import defis.defi1.defi1_old.tool.Loader;

import java.util.*;

/**
 * Defi 1
 * Cherche le chemin le plus court entre toutes les villes
 * ou le chemin le plus rapide entre toutes les villes
 */
public class MainAStar {
    public static void main(String[] args) {
        HashMap<String, Ville> villes = Loader.loadArraysLocal("ressources/villes.json", 100);
        Scanner scanner = new Scanner(System.in);

//        double latitude_depart = 42.33;
//        double longitude_depart = 3.13;
//        double latitude_arrivee = 50.99920;
//        double longitude_arrivee = 2.428398;

        System.out.println("Entrez la latitude du point de départ");
        double latitude_depart = scanner.nextDouble();
        System.out.println("Entrez la longitude du point de départ");
        double longitude_depart = scanner.nextDouble();
        System.out.println("Entrez la latitude du point d'arrivée");
        double latitude_arrivee = scanner.nextDouble();
        System.out.println("Entrez la longitude du point d'arrivée");
        double longitude_arrivee = scanner.nextDouble();


        boolean mode;


        System.out.println("Entrez le mode de calcul(true-> le plus court chemin, false-> le plus rapide)");
        try{
            mode = scanner.nextBoolean();
        } catch (Exception e){
            System.out.println("Mode par défaut: le plus court chemin");
            mode = true;
        }

        if(mode){
            for(Ville ville : villes.values()){
                ville.setCoefficientClass(3);
            }
        }

        double startTime = System.currentTimeMillis();

        Position depart = new Position(longitude_depart, latitude_depart);
        Position arrivee = new Position(longitude_arrivee, latitude_arrivee);

        boolean estDansVDepart = false;
        boolean estDansVArrivee = false;
        String villeMemeDepart = "";
        String villeMemeArrivee = "";
        for(Ville ville : villes.values()){
            if(ville.getPosition().equals(depart)){
                estDansVDepart = true;
                villes.remove(ville.getNom_ville());
                villeMemeDepart = ville.getNom_ville();
                break;
            }
        }

        for(Ville ville : villes.values()){
            if(ville.getPosition().equals(arrivee)){
                estDansVArrivee = true;
                villes.remove(ville.getNom_ville());
                villeMemeArrivee = ville.getNom_ville();
                break;
            }
        }

        ArrayList<String> children = new ArrayList<>(villes.keySet());
        children.add("Arrivée");

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
                        ) * Math.max(villes.get(node.getNom()).getCoefficient(), villes.get(child).getCoefficient());
                childNode.setDistance(distance);

                double distance_heuristique = Position.distanceEntre(villes.get(child).getPosition(), arrivee);
                childNode.setDistance_heuristique(distance_heuristique);


                if (!explored.contains(childNode) && !frontier.contains(childNode)){
                    frontier.add(childNode);
                }else if(frontier.contains(childNode)){
//                    System.out.println(1);
                    int index = frontier.indexOf(childNode);
                    if(frontier.get(index).getDistance() > childNode.getDistance()){
                        frontier.remove(index);
                        frontier.add(childNode);
                    }
                }
            }

            frontier.sort(Comparator.comparingDouble(Node::getTotalDistance));
        }

        Node node_arrivee = end_node;

        double endTime = System.currentTimeMillis();


        double total_distance = 0;
        while(node_arrivee.getNodeParents() != null){
            System.out.println(node_arrivee.getNom() + " [ " + villes.get(node_arrivee.getNom()).getPosition().getPosDegree()+ " ]");
            double temps_distance = Position.distanceEntre(villes.get(node_arrivee.getNom()).getPosition(), villes.get(node_arrivee.getNodeParents().getNom()).getPosition());
            total_distance += temps_distance;
            System.out.println("↑ " + temps_distance + " km");
            node_arrivee = node_arrivee.getNodeParents();
        }

        System.out.println("Départ [longitude: " + longitude_depart + " | latitude: " + latitude_depart + "]");
        if(estDansVDepart){
            System.out.println("Départ dans la ville: " + villeMemeDepart);
        }
        if(estDansVArrivee){
            System.out.println("Arrivée dans la ville: " + villeMemeArrivee);
        }
        System.out.println("Total distance: " + total_distance + " km");
        System.out.println("Temps de calcul: " + (endTime - startTime) + " ms");

    }
}
