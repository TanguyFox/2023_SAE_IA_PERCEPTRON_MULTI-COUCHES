package defis.defi1_new.framework.recherche;

import defis.defi1_new.framework.common.Action;
import defis.defi1_new.framework.common.BaseProblem;
import defis.defi1_new.framework.common.State;



public abstract class SearchProblem extends BaseProblem {
    
    /** 
     * Test si état final (but)
     * @param s Un état à tester
     * @return Vrai si c'est un but
     */
    public abstract boolean isGoalState(State s);

    /**
     * Retourne le coût de faire une action dans un état. L'action n'est pas exécutée. 
     * @param s L'état en question 
     * @param a L'action en question
     * @return Le coût 
     */
    public abstract double getActionCost(State s, Action a);
}
