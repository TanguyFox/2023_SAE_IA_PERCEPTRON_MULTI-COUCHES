package defis.defi_3.algo.jeux;

import defis.defi_3.framework.common.Action;
import defis.defi_3.framework.common.ActionValuePair;
import defis.defi_3.framework.jeux.Game;
import defis.defi_3.framework.jeux.GameState;
import defis.defi_3.framework.jeux.Player;

public class MinMaxPlayer extends Player {

    /**
     * Represente un joueur
     *
     * @param g          l'instance du jeux
     * @param player_one si joueur 1
     */
    public MinMaxPlayer(Game g, boolean player_one) {
        super(g, player_one);
    }

    @Override
    public Action getMove(GameState state) {
        ActionValuePair coup;
        if(state.getPlayerToMove() == 2) {
            coup = maxValeur(state);
        } else {
            coup = minValeur(state);
        }
        return coup.getAction();
    }

    public ActionValuePair maxValeur(GameState state) {
        if(game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }

        double valeurMax = Double.NEGATIVE_INFINITY;
        Action coupMax = null;

        for(Action coup : game.getActions(state)) {
            GameState gs = (GameState) game.doAction(state, coup);
            ActionValuePair coupMin = minValeur(gs);
            if(coupMin.getValue() > valeurMax) {
                valeurMax = coupMin.getValue();
                coupMax = coup;
            }
        }
        return new ActionValuePair(coupMax, valeurMax);
    }

    public ActionValuePair minValeur(GameState state) {
        if(game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }

        double valeurMin = Double.POSITIVE_INFINITY;
        Action coupMin = null;

        for(Action coup : game.getActions(state)) {
            GameState gs = (GameState) game.doAction(state, coup);
            ActionValuePair coupMax = maxValeur(gs);
            if(coupMax.getValue() < valeurMin) {
                valeurMin = coupMax.getValue();
                coupMin = coup;
            }
        }
        return new ActionValuePair(coupMin, valeurMin);
    }
}
