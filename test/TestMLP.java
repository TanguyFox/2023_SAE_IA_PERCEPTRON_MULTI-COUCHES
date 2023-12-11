import org.junit.jupiter.api.*;
import sae.function.SigmoideFunction;
import sae.function.TangeanteHyperboliqueFunction;
import sae.function.TransferFunction;
import sae.mlp.MLP;
import sae.tools.ArgParse;
import sae.tools.Constantes;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class TestMLP {
    @Test
    public void TestMLP_ET_SIG(){
        int[] layers = new int[]{2, 1};
        double learningRate = 0.6;
        TransferFunction transferFunction = new SigmoideFunction();
        double[][] inputs = Constantes.INPUT_BINARY2;
        double[][] outputs = Constantes.OUTPUT_BINARY2_AND;

        MLP mlp = new MLP(layers, learningRate, transferFunction);
        boolean appris = false;
        int nbInter = 0;
        int maxRep = 5000;
        Boolean[] apprentissage = new Boolean[inputs.length];
        Arrays.fill(apprentissage, false);

        while(Arrays.asList(apprentissage).contains(false) && nbInter < maxRep){
            for(int i =0; i< 10; i++) {
                for (int j = 0; j < inputs.length; j++) {
                    mlp.backPropagate(inputs[j], outputs[j]);
                }
            }
            for (int k = 0; k < inputs.length; k++) {
                double[] output = mlp.execute(inputs[k]);
                if(Math.abs(output[0] - outputs[k][0]) < 0.1) {
                    apprentissage[k] = true;
                }
            }
            nbInter++;
        }
        assertTrue(nbInter < maxRep);
    }

    @Test
    public void TestMLP_OU_SIG(){
        int[] layers = new int[]{2, 1};
        double learningRate = 0.6;
        TransferFunction transferFunction = new SigmoideFunction();
        double[][] inputs = Constantes.INPUT_BINARY2;
        double[][] outputs = Constantes.OUTPUT_BINARY2_OR;

        MLP mlp = new MLP(layers, learningRate, transferFunction);
        boolean appris = false;
        int nbInter = 0;
        int maxRep = 5000;
        Boolean[] apprentissage = new Boolean[inputs.length];
        Arrays.fill(apprentissage, false);

        while(Arrays.asList(apprentissage).contains(false) && nbInter < maxRep){
            for(int i =0; i< 10; i++) {
                for (int j = 0; j < inputs.length; j++) {
                    mlp.backPropagate(inputs[j], outputs[j]);
                }
            }
            for (int k = 0; k < inputs.length; k++) {
                double[] output = mlp.execute(inputs[k]);
                if(Math.abs(output[0] - outputs[k][0]) < 0.1) {
                    apprentissage[k] = true;
                }
            }
            nbInter++;
        }
        assertTrue(nbInter < maxRep);
    }

    @Test
    public void TestMLP_XOR_SIG(){
        int[] layers = new int[]{2, 2, 1};
        double learningRate = 0.6;
        TransferFunction transferFunction = new SigmoideFunction();
        double[][] inputs = Constantes.INPUT_BINARY2;
        double[][] outputs = Constantes.OUTPUT_BINARY2_XOR;

        MLP mlp = new MLP(layers, learningRate, transferFunction);
        boolean appris = false;
        int nbInter = 0;
        int maxRep = 5000;
        Boolean[] apprentissage = new Boolean[inputs.length];
        Arrays.fill(apprentissage, false);

        while(Arrays.asList(apprentissage).contains(false) && nbInter < maxRep){
            for(int i =0; i< 10; i++) {
                for (int j = 0; j < inputs.length; j++) {
                    mlp.backPropagate(inputs[j], outputs[j]);
                }
            }
            for (int k = 0; k < inputs.length; k++) {
                double[] output = mlp.execute(inputs[k]);
                if(Math.abs(output[0] - outputs[k][0]) < 0.1) {
                    apprentissage[k] = true;
                }
            }
            nbInter++;
        }
        assertTrue(nbInter < maxRep);
    }
}
