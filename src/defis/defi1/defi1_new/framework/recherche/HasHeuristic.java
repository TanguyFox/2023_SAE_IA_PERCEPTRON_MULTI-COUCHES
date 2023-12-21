package defis.defi1.defi1_new.framework.recherche;

/**
 * Ajoute la notion d'heuristique aux états de certains problèmes
 */

public interface HasHeuristic {

    /**
     * Retourne la valeur de l'heuristique 
     * @return Le résultat 
     */
    public abstract double getHeuristic();
    
}
