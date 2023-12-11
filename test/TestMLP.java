import org.junit.jupiter.api.*;
import sae.mlp.MLP;
import sae.tools.ArgParse;
import sae.tools.Constantes;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class TestMLP {
    @Test
    public void testMLP_ET_SIG() {
        MLP mlp = new MLP(ArgParse.makeLayers("[2,1]"), 0.03, ArgParse.makeFunction("sig"));
        mlp.train(Constantes.INPUT_BINARY_2, ArgParse.makeOutput("and"), 30000);
        System.out.println(Arrays.toString(mlp.execute(new double[]{0, 0})));
        
    }

    @Test
    public void testMLP_OU_SIG() {
        MLP mlp = new MLP(ArgParse.makeLayers("[2,1]"), 0.03, ArgParse.makeFunction("sig"));
        mlp.train(Constantes.INPUT_BINARY_2, ArgParse.makeOutput("or"), 30000);

    }

    @Test
    public void testMLP_XOR_SIG() {
        MLP mlp = new MLP(ArgParse.makeLayers("[2,2,1]"), 0.03, ArgParse.makeFunction("sig"));
        mlp.train(Constantes.INPUT_BINARY_2, ArgParse.makeOutput("xor"), 30000);

    }





}
