package defis.commun.algo.jeux;


import defis.commun.framework.common.Action;
import defis.commun.framework.jeux.Game;
import defis.commun.framework.jeux.GameState;
import defis.commun.framework.jeux.Player;

/**
 * Définie un joueurAléatoire
 *
 */

public class RandomPlayer extends Player {

    /**
     * Crée un joueur Aléatoire 
     * @param g l'instance du jeux
     * @param p1 vrai si joueur 1
     */
    public RandomPlayer(Game g, boolean p1){
        super(g, p1);
        name = "Random";
    }
    
    
    
    /**
     * {@inheritDoc}
     * <p>Retourn un coup aléatoire</p>
     */
    public Action getMove(GameState state){
        return game.getRandomMove(state);
    }


}
