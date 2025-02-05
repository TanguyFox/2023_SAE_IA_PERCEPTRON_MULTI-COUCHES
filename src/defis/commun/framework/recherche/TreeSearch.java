package defis.commun.framework.recherche;

import defis.commun.framework.common.Action;
import defis.commun.framework.common.Misc;
import defis.commun.framework.common.State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;

/**
 * Une classe mère qui représente un algorithme de recherche
 * <p>Les algorithmes doivent concrétiser la méthode solve</p>
 *
 */
public abstract class TreeSearch {

    /**
     * Le problème à résoudre 
     */
    
    protected SearchProblem problem;

    /**
     * L'état initial
     */
    protected State intial_state;

    /** 
     * Le noeud trouvé par la recherche (la méthode solve)
     */
    protected SearchNode end_node;   

    /*
     * La liste des noeuds a étendre 
     */
    protected Queue<SearchNode> frontier = null;

    /*
     * La liste de noeuds déjà traités
     */
    protected HashSet<State> explored = new HashSet<State>();


    /**
     * Crée un algorithme de recherche
     * @param p Le problème à résoudre
     * @param s L'état initial 
     */
    public TreeSearch(SearchProblem p, State s){
        intial_state = s;
        problem = p;
    }

    /**
     * Lance la recherche pour résoudre le problème 
     * <p>A concrétiser pour chaque algorithme.</p>
     * <p>La solution devra être stockée dans end_node.</p>
     * @return Vrai si solution trouvé 
     */
    
    public abstract boolean solve();


    /**
     * Afficher la solution trouvée (la suite d'actions)
     * et quelques informations sur la recherche. 
     */
    
    public void printSolution() {
        
        // Récupérer la suite d'actions depuis la racine 
        ArrayList<Action> solution = end_node.getPathFromRoot(); 
       
        // Afficher des trucs 
        System.out.print("Solution: "+ intial_state+ " > "); 
        Misc.printCollection(solution, '>');
        System.out.println("Solved ! Explored "+
                           SearchNode.getTotalSearchNodes() +
                           " nodes. Max depth was "+
                           SearchNode.getMaxDepth() +
                           ". Solution cost is "+end_node.getCost());
    }
}
