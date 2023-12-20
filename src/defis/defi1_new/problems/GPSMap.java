package defis.defi1_new.problems;

import defis.defi1_new.framework.common.Action;
import defis.defi1_new.framework.common.State;
import defis.defi1_new.framework.recherche.Problem;
import defis.defi1_new.framework.recherche.Transitions;
import defis.defi_1_old.structure.Position;
import defis.defi_1_old.structure.Ville;
import defis.defi_1_old.tool.Loader;

import java.util.ArrayList;
import java.util.HashMap;

public class GPSMap extends Problem {

    public static ArrayList<GPSMapState> villes = new ArrayList<GPSMapState>();

    public static ArrayList<Action> actions = new ArrayList<Action>();

    public static GPSMapState DEPART;

    public static GPSMapState ARRIVEE;

    public GPSMap(Position point_depart, Position point_arrivee){

        HashMap<String, Ville> cities = Loader.loadArraysLocal("ressources/villes.json", 100);

        for (Ville ville : cities.values()) {
            GPSMapState s = new GPSMapState(ville.getNom_ville(), Position.distanceEntre(point_arrivee, ville.getPosition()));
            villes.add(s);

            Action a = new Action("goto " + ville.getNom_ville());
            actions.add(a);
        }

        for (int i = 0; i < villes.size(); i++){
            GPSMapState gpsMapState = villes.get(i);
            for (int j = 0; j < villes.size(); j++){
                if (i != j){

                    double coefficient =
                            Math.min(
                                    cities.get(gpsMapState.getName()).getCoefficient(),
                                    cities.get(villes.get(j).getName()).getCoefficient());

                    coefficient = 1;

                    TRANSITIONS.addTransition(
                            gpsMapState,
                            actions.get(j),
                            villes.get(j),
                            Position.distanceEntre(
                                    cities.get(gpsMapState.getName()).getPosition(),
                                    cities.get(villes.get(j).getName()).getPosition()
                            ) * coefficient);
                }
            }
        }


        DEPART = new GPSMapState("Départ", Position.distanceEntre(point_arrivee, point_depart));

        ARRIVEE = new GPSMapState("Arrivée", 0);

        State[] states = new State[villes.size() + 2];
        for (int i = 0; i < villes.size(); i++) {
            states[i] = villes.get(i);
        }
        states[villes.size()] = DEPART;
        states[villes.size() + 1] = ARRIVEE;
        STATES = states;

        for(int i = 0; i < villes.size(); i++){
            TRANSITIONS.addTransition(DEPART,
                    actions.get(i),
                    villes.get(i),
                    Position.distanceEntre(point_depart, cities.get(villes.get(i).getName()).getPosition()));
        }

        for(int i = 0; i < villes.size(); i++){
            TRANSITIONS.addTransition(villes.get(i),
                    actions.get(i),
                    ARRIVEE,
                    Position.distanceEntre(point_arrivee, cities.get(villes.get(i).getName()).getPosition()));
        }

//        for(int i = 0; i < STATES.length; i++){
//            System.out.println(STATES[i]);
//        }


    }


    @Override
    public boolean isGoalState(State s) {
        return s.equals(ARRIVEE);
    }
}
