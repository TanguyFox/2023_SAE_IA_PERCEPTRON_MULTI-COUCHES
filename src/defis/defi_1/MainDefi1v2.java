package defis.defi_1;

import defis.defi_1.structure.Node;
import defis.defi_1.structure.Position;
import defis.defi_1.structure.Ville;
import defis.defi_1.tool.Loader;

import java.util.*;

/**
 * Cherche le chemin le plus court entre toutes les villes
 * Algo glouton
 * "À chaque fois, sélectionner la ville la plus proche de la position actuelle.
 * Obtenir une valeur approximative,
 * ce qui peut être utile lorsque l'on fait face à un volume de données extrêmement élevé.
 */
public class MainDefi1v2 {

    public static void main(String[] args) {
        HashMap<String, Ville> villes = Loader.loadArraysLocal("ressources/villes.json");

        Scanner scanner = new Scanner(System.in);
//        System.out.print("latitude :");
//        double latitude_depart = scanner.nextDouble();
        double latitude_depart = 40;

//        System.out.print("longitude :");
//        double longitude_depart = scanner.nextDouble();
        double longitude_depart = 3;

        double latitude_arrivee = 48.6;
        double longitude_arrivee = 7;

//        System.out.println("latitude :" + latitude_depart + " longitude :" + longitude_depart);

        Position depart = new Position(longitude_depart, latitude_depart);
        Position arrivee = new Position(longitude_arrivee, latitude_arrivee);

        HashSet<String> visited = new HashSet<>();
        ArrayDeque<Node> path = new ArrayDeque<>();

        while(visited.size() != villes.size()){
            double min = Double.MAX_VALUE;
            double min_distance = -1;
            String villeMin = null;
            for(Ville ville : villes.values()){

                if (visited.contains(ville.getNom_ville())){
                    continue;
                }

                double distance_heuristique = Position.distanceEntre(ville.getPosition(), arrivee);
                double distance_Reel = Position.distanceEntre(depart, ville.getPosition());
                double distance = distance_heuristique * -1 + distance_Reel;
                if (distance < min){
                    min = distance;
                    min_distance = distance_Reel;
                    villeMin = ville.getNom_ville();
                }

            }
            visited.add(villeMin);
            path.add(new Node(villeMin, min_distance, null, null));
            depart = villes.get(villeMin).getPosition();
        }

        path.add(new Node("Arrivée", Position.distanceEntre(villes.get(path.getLast().getNom()).getPosition(), arrivee), null, null));

        double totalDistance = 0;
        System.out.println("Chemin le plus court :");
        System.out.println("Départ [longitude: " + longitude_depart + " | latitude: " + latitude_depart + "]");

        while(!path.isEmpty()){
            Node node = path.poll();
            System.out.println("↓ Distance: " + node.getDistance());
            System.out.print(node.getNom() + " ");
            if(!node.getNom().equals("Arrivée")){
                System.out.println("["+villes.get(node.getNom()).getPosition().getPosDegree()+"]");
            }else{
                System.out.println("["+arrivee.getPosDegree()+"]");
            }
            totalDistance += node.getDistance();
        }
        System.out.println("Total distance: " + totalDistance);




    }
}
