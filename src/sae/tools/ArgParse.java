package sae.tools;

import sae.function.TransferFunction;

import java.util.Arrays;

/**
 * Quelques méthodes rudimentaires pour lire la ligne de commande
 * et lancer le Schmilblick
 */

public class ArgParse {

    /**
     * Pour afficher plus de chose
     */
    public static boolean DEBUG = false;

    /**
     * Stock le message d'aide
     */
    public static String msg = null;

    /**
     * spécifie le message d'aide
     */
    public static void setUsage(String m) {
        msg = m;
    }

    /**
     * Affiche un message d'utilisation
     */
    public static void usage() {
        System.err.println(msg);
    }

    /**
     * Retourne la valeur d'un champ demandé
     *
     * @param args Le tableau de la ligne de commande
     * @param arg  Le paramètre qui nous intéresse
     * @return La valeur du paramètre
     */
    public static String getArgFromCmd(String[] args, String arg) {
        if (args.length > 0) {
            int idx = Arrays.asList(args).indexOf(arg);
            if (idx % 2 == 0 && idx < args.length - 1)
                return args[idx + 1];
            if (idx < 0)
                return null;
            usage();
        }
        return null;

    }

    /**
     * Pour vérifier l'existence d'une option donnée
     *
     * @param args Le tableau de la ligne de commande
     * @param arg  L'option qui nous intéresse
     * @return vrai si l'option existe
     */
    public static boolean getFlagFromCmd(String[] args, String arg) {
        int idx = Arrays.asList(args).indexOf(arg);
        if (idx >= 0)
            return true;
        return false;

    }

    /**
     * Retourne le nom problème choisi
     *
     * @param args Le tableau de la ligne de commande
     * @return le nom du problème ou null
     */
    public static String getTabFromCmd(String[] args) {
        handleFlags(args);
        return getArgFromCmd(args, "-des");
    }

    public static String getLayersFromCmd(String[] args) {
        handleFlags(args);
        return getArgFromCmd(args, "-lay");
    }

    public static String getFunctionFromCmd(String[] args) {
        handleFlags(args);
        return getArgFromCmd(args, "-func");
    }

    public static String getMaxRepFromCmd(String[] args) {
        handleFlags(args);
        return getArgFromCmd(args, "-max");
    }


    /**
     * Traitement des options -v, -h
     *
     * @param args Le tableau de la ligne de commande
     */
    public static void handleFlags(String[] args) {
        DEBUG = getFlagFromCmd(args, "-v");
        if (getFlagFromCmd(args, "-h")) {
            usage();
            System.exit(0);
        }
    }


    /**
     * Factory qui retourne une instance du problème choisie ou celui par défaut
     *
     * @param tab Le nom du problème ou null
     * @return Une instance du problème
     */

    public static double[][] makeOutput(String tab) {
        if (tab == null)
            tab = "and";
        switch (tab) {
            case "and":
                return new double[][]{{0}, {0}, {0}, {1}};
            case "or":
                return new double[][]{{0}, {1}, {1}, {1}};
            case "xor":
                return new double[][]{{0}, {1}, {1}, {0}};
            default:
                System.out.println("Table inconnu");
                usage();
                System.exit(1);
        }

        return null; // inatteignable, faire plaisir a javac
    }

    public static int[] makeLayers(String tab) {
        if (tab == null)
            tab = "[2, 1]";

        if (!tab.startsWith("[") || !tab.endsWith("]")) {
            System.out.println("Erreur : l'argument -lay doit être de la forme [1, 2, 3]");
            System.exit(1);
        }

        tab = tab.substring(1, tab.length() - 1);
        String[] layers = tab.split(",");
        int[] res = new int[layers.length];
        for (int i = 0; i < layers.length; i++) {
            res[i] = Integer.parseInt(layers[i]);
        }
        return res;
    }

    public static TransferFunction makeFunction(String tab) {
        if (tab == null)
            tab = "sig";
        switch (tab) {
            case "sig":
                return new sae.function.SigmoideFunction();
            case "tanh":
                return new sae.function.TangeanteHyperboliqueFunction();
            default:
                System.out.println("Fonction inconnue");
                usage();
                System.exit(1);
        }

        return null; // inatteignable, faire plaisir a javac
    }

    public static double getLearningRate(String[] args) {
        String lr = getArgFromCmd(args, "-lr");
        if (lr == null)
            return 0.6;
        return Double.parseDouble(lr);
    }

    public static int getMaxRep(String[] args) {
        String max = getArgFromCmd(args, "-max");
        if (max == null)
            return 5000;
        return Integer.parseInt(max);
    }


}



