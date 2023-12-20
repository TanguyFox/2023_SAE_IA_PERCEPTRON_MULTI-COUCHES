package defis.defi1_new.algo;



import defis.defi1_new.framework.common.Action;
import defis.defi1_new.framework.common.State;
import defis.defi1_new.framework.recherche.SearchNode;
import defis.defi1_new.framework.recherche.SearchProblem;
import defis.defi1_new.framework.recherche.TreeSearch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AStar extends TreeSearch {

    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public AStar(SearchProblem p, State s) {
        super(p, s);
    }

    @Override
    public boolean solve() {
        frontier = new PriorityQueue<>(Comparator.comparingDouble(e -> e.getHeuristic() + e.getCost()));
        explored = new HashSet<>();

        SearchNode node = SearchNode.makeRootSearchNode(intial_state);
        frontier.add(node);
        while(!frontier.isEmpty()){
            node = frontier.poll();
            explored.add(node.getState());

            if(problem.isGoalState(node.getState())){
                end_node = node;
                return true;
            }

            ArrayList<Action> actions = problem.getActions(node.getState());
            for(Action a : actions){
                SearchNode child = SearchNode.makeChildSearchNode(problem, node, a);
                if(explored.contains(child.getState())){
                    continue;
                }
                frontier.add(child);
            }
        }

        return false;
    }
}
