package defis.defi_1;
import defis.defi_1.structure.Node;
import defis.defi_1.structure.Ville;
import defis.defi_1.structure.Position;
import defis.defi_1.tool.Loader;

import java.util.*;


/**
 * Cherche le chemin le plus court entre toutes les villes
 * À chaque fois, placer le nœud le plus proche du point de départ
 * dans la file de priorité. Lorsqu'un nœud n'a plus d'endroit où aller,
 * c'est-à-dire qu'un chemin le plus court a été trouvé.
 */
public class MainDefi1v1 {
    public static void main(String[] args) {
        HashMap<String, Ville> villes = Loader.loadArrays("ressources/communes.json");

        Scanner scanner = new Scanner(System.in);
        System.out.print("latitude :");
//        double latitude_depart = scanner.nextDouble();
        double latitude_depart = 40;

        System.out.print("longitude :");
//        double longitude_depart = scanner.nextDouble();
        double longitude_depart = 3;

        System.out.println("latitude :" + latitude_depart + " longitude :" + longitude_depart);
        Position depart = new Position(Math.toRadians(longitude_depart), Math.toRadians(latitude_depart));

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(Node::getDistance));
        for(String ville_name : villes.keySet()){

            double distance = Position.distanceEntre(depart, villes.get(ville_name).getPosition());

            ArrayList<String> enfants = new ArrayList<>();
            for (String v : villes.keySet()){
                enfants.add(v);
            }
            enfants.remove(ville_name);
            Node node = new Node(ville_name, distance, null, enfants);
            priorityQueue.add(node);
        }

//        while(!priorityQueue.isEmpty()){
//            Node node = priorityQueue.poll();
//            System.out.println(node.getNom_ville() + " : " + node.getDistance());
//            System.out.println("[");
//            for(String ville_name : node.getNodeEnfants()){
//                System.out.println(ville_name);
//            }
//            System.out.println("]");
//        }


        boolean allVisited = false;
        Node bestNode = null;

        while (!allVisited){
            Node node = priorityQueue.poll();

            if (node.getNodeEnfants().isEmpty()){
                bestNode = node;
                allVisited = true;
            } else {
                for (String ville_name : node.getNodeEnfants()){
                    Position positionCourrante = villes.get(node.getNom_ville()).getPosition();

                    double distance = Position.distanceEntre(positionCourrante, villes.get(ville_name).getPosition());

                    ArrayList<String> enfants = new ArrayList<>();
                    for(String ancientEnfants : node.getNodeEnfants()){
                        enfants.add(ancientEnfants);
                    }

                    enfants.remove(ville_name);

                    Node nouveauNode = new Node(ville_name, node.getDistance() + distance, node, enfants);
                    priorityQueue.add(nouveauNode);
                }
            }
        }

        System.out.println("Le chemin le plus court: " + bestNode.getDistance() + " km");
        while(bestNode.getNodeParents() != null){
            System.out.println(bestNode.getNom_ville() + " " + bestNode.getDistance() + " km");
            System.out.println(villes.get(bestNode.getNom_ville()).getPosition().getPosDegree());
            System.out.println("↑");
            bestNode = bestNode.getNodeParents();
        }

    }
}

