package defis.defi_3.algo.recherche;

import defis.defi_3.framework.common.Action;
import defis.defi_3.framework.common.ArgParse;
import defis.defi_3.framework.common.State;
import defis.defi_3.framework.recherche.SearchNode;
import defis.defi_3.framework.recherche.SearchProblem;
import defis.defi_3.framework.recherche.TreeSearch;

import java.util.ArrayList;
import java.util.List;

public class DFS extends TreeSearch {

    private final List<SearchNode> frontiere = new ArrayList<>();
    private static final int MAX_DEPTH = 100;

    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public DFS(SearchProblem p, State s) {
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
        State state = node.getState();

        //Ajout du noeud initial dans la liste des noeuds à parcourir
        frontiere.add(node);

        if(ArgParse.DEBUG) {
            System.out.println("["+state);
        }

        //Tant qu'il y a des noeuds à parcourir et que l'on est pas à la profondeur max, on continue
        while(!frontiere.isEmpty()) {

            //On récupère le premier élément à inspecter
            //On le supprime de cette liste et on l'ajoute dans la liste d'élément à parcourir
            node = frontiere.get(0);
            frontiere.remove(node);
            explored.add(node.getState());
            System.out.println(node.getDepth());

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

                if (!frontiere.contains(son) && !explored.contains(son.getState())){
                    frontiere.add(0, son);
                }
            }
        }
        System.out.println("Aucune solution trouvée // MAX DEPTH : " + node.getDepth());
        return false;
    }
}
