package defis.commun.algo.jeux;


import defis.commun.framework.common.Action;
import defis.commun.framework.jeux.Game;
import defis.commun.framework.jeux.GameState;
import defis.commun.framework.jeux.Player;

/**
 * Définie un joueur Humain
 *
 */

public class HumanPlayer extends Player {

    /**
     * Crée un joueur human
     * @param g l'instance du jeux
     * @param p1 vrai si joueur 1
     */
    public HumanPlayer(Game g, boolean p1){
        super(g, p1);
        name = "Human";
    }
    
    /**
     * {@inheritDoc}
     * <p>Demande un coup au joueur humain</p>
     */
    public Action getMove(GameState state){
        return game.getHumanMove(state);
    }


}
