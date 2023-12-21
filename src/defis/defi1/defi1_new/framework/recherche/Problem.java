package defis.defi1.defi1_new.framework.recherche;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collections;

import defis.defi1.defi1_new.framework.common.Action;
import defis.defi1.defi1_new.framework.common.Misc;
import defis.defi1.defi1_new.framework.common.State;
import defis.defi1.defi1_new.framework.common.StateActionPair;



public abstract class Problem extends SearchProblem {


    protected static State[] STATES = null ;


    protected static Transitions TRANSITIONS = new Transitions();
    
    
    public ArrayList<Action> getActions(State s){
        // rechercher dans toutes les transitions celle qui partent de s
        Set<StateActionPair> sa = TRANSITIONS.getKeys();
        List<StateActionPair> state = sa.stream()
            .filter(k -> s.equals(k.getState()))
            .collect(Collectors.toList());

        // récupérer les action depuis s
        List<Action> actions = state
            .stream()
            .map(k -> k.getAction())
            .collect(Collectors.toList());

        // On trie pour les avoir dans le même ordre a chaque fois 
        ArrayList<Action> result = new ArrayList<Action>(actions);
        Collections.sort(result) ;
        return result;
    }
    
    // exécute l'action a dans l'état s, retourne le nouvel état
    public State doAction(State s, Action a){
        return TRANSITIONS.getTransition(s,a);
    }
    
    // retourne le coût de faire l'action a dans l'état s
    public double getActionCost(State s, Action a){
        return TRANSITIONS.getCost(s,a);
    }

    /**
     * Affiche le graphe des états du problème.
     */ 
    public void printStateGraph(){
        String res ="";
        for(int i=0; i< STATES.length; i++){
            res = res + STATES[i] + "{"
                + Misc.collection2string(getActions(STATES[i]), ',')
                + "}\n" ; 
        }
        
        System.out.println(res);
    }

    
}
