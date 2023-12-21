import defis.commun.framework.common.ArgParse;
import defis.commun.framework.jeux.Game;
import defis.commun.framework.jeux.GameEngine;
import defis.commun.framework.jeux.GameState;
import defis.commun.framework.jeux.Player;

/**
 * Lance un une partie de jeux donné et affiche le resultat
 */
public class LancerJeux {

    public static void main(String[] args){

        // fixer le message d'aide
        ArgParse.setUsage
            ("Utilisation :\n\n"
             + "java LancerJeux [-game jeux] "
             + "[-p1 joueur1] [-p2 joueur1] "
             + "[-v] [-h]\n"
             + "-game : Le nom du jeux {tic, c4}. Par défautl tic\n"
             + "-p1/2 : L'agorithme joueur {rnd, hum, minmax, alphabeta}. Par défault rnd pour les deux\n"
                    + "-prof : La profondeur de recherche pour les joueurs alphabeta. Par défaut 7\n"
             + "-v    : Rendre bavard (mettre à la fin)\n"
             + "-h    : afficher ceci (mettre à la fin)"
             
             );
        
        // récupérer les option de la ligne de commande
        String game_name = ArgParse.getGameFromCmd(args);
        String p1_type = ArgParse.getPlayer1FromCmd(args);
        String p2_type = ArgParse.getPlayer2FromCmd(args);
        int profondeur = ArgParse.getProfondeurFromCmd(args);
 
        // créer un jeux, des joueurs et le moteur de jeux
        Game game = ArgParse.makeGame(game_name);
        Player p1 = ArgParse.makePlayer(p1_type, game, true, profondeur);
        Player p2 = ArgParse.makePlayer(p2_type, game, false, profondeur);
        GameEngine game_engine = new GameEngine(game, p1, p2);

        // on joue jusqu'à la fin
        long startTime = System.currentTimeMillis();
        GameState end_game = game_engine.gameLoop();
        long estimatedTime = System.currentTimeMillis() - startTime;

        // Partie fini 
        System.out.println(end_game);
        Player winner = game_engine.getWinner(end_game);
        if(winner != null){
            System.out.print("Le joueur "
                             +(game_engine.getEndGameValue(end_game) == 1 ? 1: 2)
                             +" ("+ winner.getName()+") a gagné, après "
                             +game_engine.getTotalMoves()
                             +" coups. ");
        } else
            System.out.print("Match nul. ");
        System.out.println("La partie à durée "+estimatedTime/1000.+" sec.");     
      
    }
}
