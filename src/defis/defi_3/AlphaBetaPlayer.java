package defis.defi_3;

import defis.commun.framework.common.Action;
import defis.commun.framework.common.ActionValuePair;
import defis.commun.framework.jeux.Game;
import defis.commun.framework.jeux.GameState;
import defis.commun.framework.jeux.Player;
import defis.commun.problemes.ConnectFourState;

public class AlphaBetaPlayer extends Player {
    private int profondeur;
    /**
     * Represente un joueur
     *
     * @param g          l'instance du jeux
     * @param player_one si joueur 1
     */
    public AlphaBetaPlayer(Game g, boolean player_one, int prof) {
        super(g, player_one);
        profondeur = prof;
    }

    @Override
    public Action getMove(GameState state) {
        long startTime = System.currentTimeMillis();
        ActionValuePair coup = null;
        if(state.getPlayerToMove() == ConnectFourState.X) {
            coup = maxValeur(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, profondeur);
        } else {
            coup = minValeur(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,profondeur);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Temps de calcul : " + (endTime - startTime) + "ms");
        return coup.getAction();
    }

    public ActionValuePair maxValeur(GameState state, double alpha, double beta, int depth) {
        if(game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        } else if (depth == 0) {
            return new ActionValuePair(null, ((ConnectFourState)state).getHeuristic());
        }

        double valeurMax = Double.NEGATIVE_INFINITY;
        Action coupMax = null;
        for(Action coup : game.getActions(state)) {
            GameState gs = (GameState) game.doAction(state, coup);
            ActionValuePair coupMin = minValeur(gs, alpha, beta, depth-1);
            if(coupMin.getValue() > valeurMax) {
                valeurMax = coupMin.getValue();
                coupMax = coup;

                if (valeurMax > alpha) {
                    alpha = valeurMax;
                }
            }
            if(valeurMax >= beta ) return new ActionValuePair(coupMax, valeurMax);
        }
        return new ActionValuePair(coupMax, valeurMax);
    }

    public ActionValuePair minValeur(GameState state, double alpha, double beta, int depth) {
        if(game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        } else if(depth == 0) {
            return new ActionValuePair(null, ((ConnectFourState)state).getHeuristic());
        }

        double valeurMin = Double.POSITIVE_INFINITY;
        Action coupMin = null;
        for(Action coup : game.getActions(state)) {
            GameState gs = (GameState) game.doAction(state, coup);
            ActionValuePair coupMax = maxValeur(gs, alpha, beta, depth-1);
            if(coupMax.getValue() < valeurMin) {
                valeurMin = coupMax.getValue();
                coupMin = coup;

                if (valeurMin < beta) {
                    beta = valeurMin;
                }
            }
            if(valeurMin <= alpha ) return new ActionValuePair(coupMin, valeurMin);
        }
        return new ActionValuePair(coupMin, valeurMin);
    }

}
