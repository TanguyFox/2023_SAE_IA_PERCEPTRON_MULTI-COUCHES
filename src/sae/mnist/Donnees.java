package sae.mnist;

import java.util.ArrayList;
import java.util.List;

public class Donnees {

    public List<Imagette> imagettes;

    public Donnees() {
        this.imagettes = new ArrayList<>();
    }

    public void shuffleImagettes() {
        for (int i = 0; i < this.imagettes.size(); i++) {
            int randomIndexToSwap = (int) (Math.random() * this.imagettes.size());
            Imagette temp = this.imagettes.get(randomIndexToSwap);
            this.imagettes.set(randomIndexToSwap, this.imagettes.get(i));
            this.imagettes.set(i, temp);
        }
        System.out.println("Data shuffled.");
    }
}
