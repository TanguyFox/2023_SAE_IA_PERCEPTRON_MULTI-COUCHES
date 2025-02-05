package defis.commun.problemes;

import java.util.Arrays;

import defis.commun.framework.common.State;
import defis.commun.framework.common.Misc;
import defis.commun.framework.jeux.GameState;
import defis.commun.framework.recherche.HasHeuristic;

public class ConnectFourState extends GameState implements HasHeuristic {

    public static final int O = 'O';
    public static final int X = 'X';
    public static final int EMPTY = ' ';

    private int cols;
    private int rows;

    private int[][] board = null;

    /**
     * Construire une grille vide
     *
     * @param r nombre de lignes
     * @param c nombre de colonnes
     */
    public ConnectFourState(int r, int c) {

        cols = c;
        rows = r;

        board = new int[r][c];
        for (int i = 0; i < r; i++)
            Arrays.fill(board[i], EMPTY);

        // -1: pas fini,1: X gagne, 0: O Gagne, 0.5 match nul
        game_value = -1;
        player_to_move = X; // convention X comence

    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public ConnectFourState cloneState() {
        ConnectFourState new_s = new ConnectFourState(rows, cols);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                new_s.board[r][c] = board[r][c];

        new_s.player_to_move = player_to_move;
        new_s.game_value = game_value;
        return new_s;
    }

    public int hashState() {
        return Arrays.deepHashCode(board);

    }

    public boolean equalsState(State o) {
        ConnectFourState other = (ConnectFourState) o;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (other.board[r][c] != board[r][c])
                    return false;
        return super.equalsState(o);
    }

    public String toString() {
        // int numDigits = String.valueOf(1000).length();

        String res = "";
        for (int i = 0; i < cols; i++)
            res += " " + i + "  ";
        res += "\n";
        res += Misc.dupString("+---", cols);
        res += "+\n";
        for(int i=rows-1;i>=0; i--){
            //for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                res += "| " + (char) board[i][j] + " ";
            res += "|\n";
            res += Misc.dupString("+---", cols);
            res += "+\n";
        }
        return res;
    }

    /**
     * on peut jouer là ?
     *
     * @param idx indice de la colonne
     * @return vrai si encore de l'espace dans cette colonne
     */
    public boolean isLegal(int idx) {
        return getFreeRow(idx) != -1;
    }


    /**
     * Le joueur courent (player_to_move) joue à la colonne col
     *
     * @param col l'index de la case
     */

    public void play(int col) {

        //System.out.println("Playing at "+col ) ;

        // quelle ligne
        int row = getFreeRow(col);

        //System.out.println("Row ="+row );

        // poser la pièce
        board[row][col] = player_to_move;

        // Calculer la valeur du jeux -1,1,0.5,0
        game_value = -1;


        //System.out.println("Filled after play "
        //                   +getNumberOfMarkedPositions()
        //                   +" out of "+rows*cols) ;

        // tout remplie, match nul
        if (getNumberOfMarkedPositions() == rows * cols)
            game_value = 0.5;

        // un gagnant, lequel ?
        if (isWiningMove(player_to_move))
            game_value = player_to_move == X ? 1 : 0;

        // change de joueur
        player_to_move = (player_to_move == X ? O : X);

        //System.out.println("Game value after play "+game_value ) ;

    }

    /**
     * vérifier s'il y a pas une ligne de 4 de faite (fin du jeux)
     *
     * @param player le joueur pour lequel on verifie
     * @return vrai si partie fini
     */
    public boolean isWiningMove(int player) {
        return (isAnyColumnComplete(player) ||
                isAnyRowComplete(player) ||
                isAnyDiagComplete(player));
    }


    // l'API privé

    /**
     * Retourne la premier case vide de la colonne col
     * -1 si c'est full
     */
    private int getFreeRow(int col) {
        if (col >= 0 && col < cols) {
            //System.out.println("Testing "+col);
            for (int r = 0; r < rows; r++) {
                //System.out.print("Free "+r+" ?");
                if (board[r][col] == EMPTY) {
                    //System.out.println(" yes");
                    return r;
                }
                //System.out.println(" No");
            }
        }
        return -1;
    }

    // le nombre de cases déjà remplies
    public int getNumberOfMarkedPositions() {
        int count = 0;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (board[r][c] != EMPTY)
                    count++;
        return count;
    }

    // Verifier les diagonales
    private boolean isAnyDiagComplete(int player) {

        for (int c = 0; c < cols - 3; c++)
            for (int r = 0; r < rows - 3; r++)
                if (board[r][c] == player &&
                        board[r][c] == board[r + 1][c + 1] &&
                        board[r][c] == board[r + 2][c + 2] &&
                        board[r][c] == board[r + 3][c + 3])
                    return true;

        for (int c = 3; c < cols; c++)
            for (int r = 0; r < rows - 3; r++)
                if (board[r][c] == player &&
                        board[r][c] == board[r + 1][c - 1] &&
                        board[r][c] == board[r + 2][c - 2] &&
                        board[r][c] == board[r + 3][c - 3])
                    return true;

        return false;
    }


    // verifier s'il y a pas une ligne vertical de faite
    private boolean isAnyColumnComplete(int player) {

        for (int c = 0; c < cols; c++)
            for (int r = 0; r < rows - 3; r++)
                if (board[r][c] == player &&
                        board[r][c] == board[r + 1][c] &&
                        board[r][c] == board[r + 2][c] &&
                        board[r][c] == board[r + 3][c])
                    return true;
        return false;
    }


    // vérifier s'il y a pas une ligne horizontale de faite
    private boolean isAnyRowComplete(int player) {

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols - 3; c++)
                if (board[r][c] == player &&
                        board[r][c] == board[r][c + 1] &&
                        board[r][c] == board[r][c + 2] &&
                        board[r][c] == board[r][c + 3])
                    return true;
        return false;
    }


    /*
     * non fonctional code bellow
     * Quick and dirty fix above
     * Need to recheck the bounds for next year
     *
     */


    // vérifier s'il y a pas une ligne horizontale de faite
    private boolean is4inRow(int i, int j) {
        //System.out.println("Checking row, after last Move "+i+", "+j);

        int min = j - 3 >= 0 ? j - 3 : 0;
        int max = j + 3 <= cols ? j + 3 : cols;
        //System.out.println("Column bounds: "+min+" "+max);
        for (int c = min; c < max - 3; c++) {
            //System.out.println("Check row:"+i+" cols:"+c+"->"+(c+3));
            if (board[i][c] != EMPTY &&
                    board[i][c] == board[i][c + 1] &&
                    board[i][c] == board[i][c + 2] &&
                    board[i][c] == board[i][c + 3])
                return true;
        }
        return false;

    }


    // vérifier s'il y a pas une ligne vertical de faite
    private boolean is4inCol(int i, int j) {

        //System.out.println("Checking col, after last Move "+i+", "+j);
        int min = i - 3 >= 0 ? i - 3 : 0;
        int max = i + 3 <= rows ? i + 3 : rows;
        //System.out.println("Row bounds: "+min+" "+max);
        for (int r = min; r < max - 3; r++) {
            //System.out.println("Check col: "+j+" rows"+r+"->"+(r+3));
            if (board[r][j] != EMPTY &&
                    board[r][j] == board[r + 1][j] &&
                    board[r][j] == board[r + 2][j] &&
                    board[r][j] == board[r + 3][j])
                return true;
        }
        return false;
    }


    // vérifier s'il y a pas une ligne diagonale de faite
    private boolean is4inDiag(int r, int c) {


        //System.out.println("Checking diag up, after last Move "+r+","+c);


        for (int i = -3; i <= 0; i++) {
            //System.out.println("Checking row: "+i);
            if (r + i >= 0 && c + i >= 0 &&
                    r + i + 3 < rows && c + i + 3 < cols) {


                //System.out.println("Fits cells: ("
                //                  +(r+i)+","+(c+i)+") -> ("
                //                  +(r+i+3)+","+(c+i+3)+")" );
                if (board[r + i][c + i] != EMPTY &&
                        board[r + i][c + i] == board[r + i + 1][c + i + 1] &&
                        board[r + i][c + i] == board[r + i + 2][c + i + 2] &&
                        board[r + i][c + i] == board[r + i + 3][c + i + 3])
                    return true;
            }
        }


        //System.out.println("Checking diag down, after last Move "+r+","+c);

        for (int i = 0; i <= 3; i++) {
            //System.out.println("Checking row: "+i);
            if (r - i - 3 >= 0 && c + i >= 0 &&
                    r - i < rows && c + i + 3 < cols) {
                //System.out.println("Fits cells: ("
                //                  +(r-i)+","+(c+i)+") -> ("
                //                   +(r-i-3)+","+(c+i+3)+")" );
                if (board[r - i][c + i] != EMPTY &&
                        board[r - i][c + i] == board[r - i - 1][c + i + 1] &&
                        board[r - i][c + i] == board[r - i - 2][c + i + 2] &&
                        board[r - i][c + i] == board[r - i - 3][c + i + 3])
                    return true;


            }
        }

        return false;
    }

    /**
     * Retourne vrai si le joueur a nbTokenAligned jetons alignés en ligne, colonne ou diagonale
     * Pourrait être améliorer en vérifiant qu'il y a assez de case vide après le jeton pour atteindre 4 jetons alignés (prendre en compte le bord du plateau)
     *
     * @param player Le joueur
     * @param nbTokenAligned Le nombre de jetons alignés
     * @return Vrai si le joueur a nbTokenAligned jetons alignés
     */
    private boolean checkAlignedToken (int player, int nbTokenAligned) {
        // Est ce qu'une ligne à nbTokenAligned jetons alignés ?
        for (int i = 0; i < rows; i++) {
            int count = 0;
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == player) {
                    count++;
                    if (count == nbTokenAligned) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }

        // Est ce qu'une colonne à nbTokenAligned jetons alignés ?
        for (int j = 0; j < cols; j++) {
            int count = 0;
            for (int i = 0; i < rows; i++) {
                if (board[i][j] == player) {
                    count++;
                    if (count == nbTokenAligned) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }

        // Est ce qu'une diagonale (haut gauche vers bas droite) à nbTokenAligned jetons alignés ?
        for (int k = 0; k < rows + cols - 1; k++) {
            int count = 0;
            for (int j = Math.max(0, k - rows + 1); j <= Math.min(k, cols - 1); j++) {
                int i = k - j;
                if (i >= 0 && i < rows && j >= 0 && j < cols && board[i][j] == player) {
                    count++;
                    if (count == nbTokenAligned) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }

        // Est ce qu'une diagonale (bas gauche vers haut droite) à nbTokenAligned jetons alignés ?
        for (int k = 1 - rows; k < cols; k++) {
            int count = 0;
            for (int j = Math.max(0, k); j <= Math.min(k + rows - 1, cols - 1); j++) {
                int i = j - k;
                if (i >= 0 && i < rows && j >= 0 && j < cols && board[i][j] == player) {
                    count++;
                    if (count == nbTokenAligned) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }

        return false;
    }

    /**
     * Retourne la valeur de l'heuristique
     * Pourrait être améliorer en prenant en compte les bordures du plateau
     * @return Le résultat
     */
    @Override
    public double getHeuristic() {
        double points = 0;

        // Est ce que le joueur courant a une position gagnante ? Si oui, cette action est priviléugiée
        if (isWiningMove(player_to_move)) {
            return 1000000000;
        }

        // Est ce que l'adversaire a une position gagnante ? Si oui, cette action est à éviter
        int opponent = (player_to_move == X ? O : X);
        if (isWiningMove(opponent)) {
            return -1000000000;
        }

        // Est ce que le joueur courant a une ligne de 3 jetons alignés ? Si oui, cette action a une valeur importante
        if (checkAlignedToken(player_to_move, 3)) points += 1000000;
        // Est ce que l'adversaire a une ligne de 3 jetons alignés ? Si oui, cette action a une valeur importante et doit être bloquée
        if (checkAlignedToken(opponent, 3)) points -= 1000000;

        // Est ce que le joueur courant a une ligne de 2 jetons alignés ? Si oui, cette action est à considérée
        if (checkAlignedToken(player_to_move, 2)) points += 500;
        // Est ce que l'adversaire a une ligne de 2 jetons alignés ? Si oui, cette action est à surveillée
        if (checkAlignedToken(opponent, 2)) points -= 500;

        // Est ce que le joueur courant a une ligne de 1 jeton aligné ? Si oui, cette action est un bon point de départ
        if (checkAlignedToken(player_to_move, 1)) points += 100;
        // Est ce que l'adversaire a une ligne de 1 jeton aligné ? Si oui, cette action est à surveillée
        if (checkAlignedToken(opponent, 1)) points -= 100;

        int center = cols / 2;
        // Est ce que le joueur courant a joué au centre ? Si oui, cette action offre plus de possibilités de victoire
        //Ne semble pas marcher
        //if (board[0][center] == player_to_move) points += 250;
        return points;
    }
}