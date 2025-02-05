package defis.commun.framework.recherche;

import defis.commun.framework.common.Action;
import defis.commun.framework.common.Misc;
import defis.commun.framework.common.State;
import defis.commun.framework.common.StateActionPair;
import defis.commun.problemes.Vacuum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
* Un Probleme où les états et les transitions sont spécifiées. 
*
*
* <p>Cette classe ajoute un ensemble détat et de transition à {@link SearchProblem}. Pour un exemple, voir la classe {@link Vacuum})</p>
*
*/

public abstract class Problem extends SearchProblem {

    /**
     * La liste des états a remplir (voir {@link Vacuum})
     */
    
    protected static State[] STATES = null ;

    /**
     * La liste des transition a remplir (voir {@link Vacuum})
     */
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
