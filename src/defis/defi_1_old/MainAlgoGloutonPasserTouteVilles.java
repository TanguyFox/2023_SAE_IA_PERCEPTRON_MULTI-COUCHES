package defis.defi_1_old;

import defis.defi_1_old.structure.Node;
import defis.defi_1_old.structure.Position;
import defis.defi_1_old.structure.Ville;
import defis.defi_1_old.tool.Loader;

import java.util.*;

/**
 * Cherche le chemin le plus court entre toutes les villes
 * Algo glouton
 * À chaque fois, sélectionner la ville la plus proche de la position actuelle.
 * Obtenir une valeur approximative,
 * ce qui peut être utile lorsque l'on fait face à un volume de données extrêmement élevé.
 */
public class MainAlgoGloutonPasserTouteVilles {

    public static void main(String[] args) {
        HashMap<String, Ville> villes = Loader.loadArraysLocal("ressources/villes.json", 100);

        double latitude_depart = 45;
        double longitude_depart = 5;
        double latitude_arrivee = 44;
        double longitude_arrivee = 3;

        int mode = 2;

        if(mode == 1){
            for(Ville ville : villes.values()){
                ville.setCoefficientClass(3);
            }
        }

        double startTime = System.currentTimeMillis();
        double rate = -15;

        HashMap<Double, ArrayList<Node>> paths = new HashMap<>();

        Position arrivee = new Position(longitude_arrivee, latitude_arrivee);

        while(rate < 15){

            Position depart = new Position(longitude_depart, latitude_depart);

            HashSet<String> visited = new HashSet<>();
            ArrayList<Node> path = new ArrayList<>();

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

                    double coefficient;
                    if(path.isEmpty()){
                        coefficient = 1;
                    }else{
                        coefficient = Math.min(villes.get(path.getLast().getNom()).getCoefficient(), ville.getCoefficient());
                    }

                    double distance = distance_heuristique * -0.2 + distance_Reel * coefficient;
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

            paths.put(path.getLast().getDistance(), path);

            rate += 0.1;
        }

        ArrayList<Node> path = paths.get(paths.keySet().stream().min(Double::compareTo).get());

        double endTime = System.currentTimeMillis();

        double totalDistance = 0;
        System.out.println("Chemin le plus court :");
        System.out.println("Départ [longitude: " + longitude_depart + " | latitude: " + latitude_depart + "]");

        for(Node node : path){
            System.out.println("↓ Distance: " + node.getDistance()+ " km");
            System.out.print(node.getNom() + " ");
            if(!node.getNom().equals("Arrivée")){
                System.out.println("["+villes.get(node.getNom()).getPosition().getPosDegree()+"]");
            }else{
                System.out.println("["+arrivee.getPosDegree()+"]");
            }
            totalDistance += node.getDistance();
        }

        System.out.println("Total distance: " + totalDistance+ " km");
        System.out.println("Temps d'exécution: " + (endTime - startTime) + " ms");
    }
}
