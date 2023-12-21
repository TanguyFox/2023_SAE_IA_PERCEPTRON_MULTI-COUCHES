package defis.defi1.defi1_new;

import defis.defi1.defi1_new.algo.AStar;
import defis.defi1.defi1_new.framework.common.State;
import defis.defi1.defi1_new.framework.recherche.SearchProblem;
import defis.defi1.defi1_new.framework.recherche.TreeSearch;
import defis.defi1.defi1_new.problems.GPSMap;
import defis.defi1.defi1_old.structure.Position;

public class LancerRecherche {

    public static void main(String[] args){


//        SearchProblem p = new RomaniaMap();
//        State s = RomaniaMap.ARAD;
//        TreeSearch algo = new AStar(p,s);
//
//        // resoudre
//        if( algo.solve() )
//            algo.printSolution();

        Position depart = new Position(32,3);
        Position arrivee = new Position(50,3);


        SearchProblem p = new GPSMap(depart, arrivee);
        State s = GPSMap.DEPART;
        TreeSearch algo = new AStar(p,s);
        if(algo.solve())
            algo.printSolution();
    }
}
