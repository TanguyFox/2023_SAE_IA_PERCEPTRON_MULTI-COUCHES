package defis.defi_3.algo.recherche;

import defis.defi_3.framework.common.Action;
import defis.defi_3.framework.common.ArgParse;
import defis.defi_3.framework.common.State;
import defis.defi_3.framework.recherche.SearchNode;
import defis.defi_3.framework.recherche.SearchProblem;
import defis.defi_3.framework.recherche.TreeSearch;

import java.util.ArrayList;
import java.util.Random;

public class RandomSearch extends TreeSearch{
       
    public RandomSearch(SearchProblem prob, State intial_state){
        super(prob, intial_state);
    }

    public boolean solve() {
         Random rng = new Random();
         
        // On commence à létat initial
        SearchNode node = SearchNode.makeRootSearchNode(intial_state);
        State state = node.getState();

        if (ArgParse.DEBUG)
            System.out.print("["+state);

        while( !problem.isGoalState(state) ) {
            // Les actions possibles depuis cette état
            ArrayList<Action> actions = problem.getActions(state);
            
            // En chosir une au hasard
            Action a = actions.get(rng.nextInt(actions.size()));

            // Executer et passer a l'état suivant
            node = SearchNode.makeChildSearchNode(problem, node, a);
            state = node.getState();

            if (ArgParse.DEBUG)
                System.out.print(" + " +a+ "] -> ["+state);
        } 

        // Enregistrer le noeud final
        end_node = node;
        
        if (ArgParse.DEBUG)
            System.out.println("]");

        return true;
    }
}
