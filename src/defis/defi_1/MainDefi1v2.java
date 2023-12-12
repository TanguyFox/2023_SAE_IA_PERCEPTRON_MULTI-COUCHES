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

        HashSet<String> visited = new HashSet<>();
        ArrayDeque<Node> path = new ArrayDeque<>();

        while(visited.size() != villes.size()){
            double min = Double.MAX_VALUE;
            String villeMin = null;
            for(Ville ville : villes.values()){

                if (visited.contains(ville.getNom_ville())){
                    continue;
                }

                double distance = Position.distanceEntre(depart, ville.getPosition());
                if (distance < min){
                    min = distance;
                    villeMin = ville.getNom_ville();
                }

            }
            visited.add(villeMin);
            path.add(new Node(villeMin, min, null, null));
            depart = villes.get(villeMin).getPosition();
        }

        double totalDistance = 0;
        while(!path.isEmpty()){
            Node node = path.poll();
            System.out.println(node.getNom_ville());
            System.out.println("↓ Distance: " + node.getDistance());
            totalDistance += node.getDistance();
        }
        System.out.println("Total distance: " + totalDistance);




    }
}
