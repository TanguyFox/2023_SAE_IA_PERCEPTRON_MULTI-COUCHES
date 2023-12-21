package defis.commun.algo.recherche;

import defis.commun.framework.common.Action;
import defis.commun.framework.common.ArgParse;
import defis.commun.framework.common.State;
import defis.commun.framework.recherche.SearchNode;
import defis.commun.framework.recherche.SearchProblem;
import defis.commun.framework.recherche.TreeSearch;

import java.util.ArrayList;

public class AStar extends TreeSearch {

    private ArrayList<SearchNode> frontiere = new ArrayList<>();

    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public AStar(SearchProblem p, State s) {
        super(p, s);
    }

    /**
     * Lance la recherche pour résoudre le problème
     * <p>A concrétiser pour chaque algorithme.</p>
     * <p>La solution devra être stockée dans end_node.</p>
     *
     * @return Vrai si solution trouvé
     */
    @Override
    public boolean solve() {
        SearchNode node = SearchNode.makeRootSearchNode(this.intial_state);
        //Ajout du noeud initial dans la liste des noeuds à parcourir
        frontiere.add(node);
        if(ArgParse.DEBUG) {
            System.out.println("["+ node.getState());
        }

        //Tant qu'il y a des noeuds à parcourir, on continue
        while(!frontiere.isEmpty()) {

            //On récupère le premier élément à inspecter
            //On le supprime de cette liste et on l'ajoute dans la liste d'élément à parcourir
            node = frontiere.get(0);
            frontiere.remove(node);
            explored.add(node.getState());

            //Pour le noeud courant, on créer ses noeuds fils à partir de chaque actions possibles
            //Si le noeud fils crée n'est pas le but, on l'ajoute à la liste des noeuds à parcourir sinon, le problème est résolu
            for (Action a : this.problem.getActions(node.getState())) {
                SearchNode son = SearchNode.makeChildSearchNode(this.problem, node, a);
                if(ArgParse.DEBUG) {
                    System.out.println(" + " + a + "] -> [" + son.getState());
                }
                if(this.problem.isGoalState(son.getState())) {
                    this.end_node = son;
                    if (ArgParse.DEBUG) {
                        System.out.println("]");
                    }
                    return true;
                }

                if (!explored.contains(son.getState()) && !frontiere.contains(son)){
                    int i = 0;
                    while(i < frontiere.size()) {
                        SearchNode n = frontiere.get(i);
                        if(n.getCost() + n.getHeuristic() > son.getCost() + son.getHeuristic()) {
                            frontiere.add(i, son);
                            break;
                        }
                        i++;
                    }
                    if(i == frontiere.size()) {
                        frontiere.add(son);
                    }
                }
            }
        }
        System.out.println("Aucune solution trouvée");
        return false;
    }
}
