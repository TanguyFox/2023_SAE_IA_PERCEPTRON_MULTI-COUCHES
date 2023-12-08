import sae.mlp.MLP;

public class MainMLP {

    public static void main(String[] args) {
        int[][] table = {
            {0, 0, 0},
            {0, 1, 0},
            {1, 0, 0},
            {1, 1, 1}
        };

        int[] layers = {2};
        double learningRate = 0.5;

        MLP mlp = new MLP(layers, learningRate, new sae.mlp.SigmoideFunction());

        int maxPassage = 100;
        int nbPassage = 0;
        boolean appris = false;

        while(nbPassage < maxPassage && !appris) {
            for(int i = 0; i < table.length; i++) {
                double[] input = {table[i][0], table[i][1]};
                double[] output = {table[i][2]};
                mlp.train(input, output);
            }
            nbPassage++;
        }
    }
}
