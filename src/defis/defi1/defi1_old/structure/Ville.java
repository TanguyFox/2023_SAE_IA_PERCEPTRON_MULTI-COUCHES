package defis.defi1.defi1_old.structure;

public class Ville {
    private String nom_ville;
    private Position position;

    private int population;

    private double coefficient;

    public Ville(String nom_ville) {
        this.nom_ville = nom_ville;
    }

    public void setCoefficientClass(int classe) {
        switch (classe){
            case 1 -> this.coefficient = 3;
            case 2 -> this.coefficient = 5;
            case 3 -> this.coefficient = 10;
            default -> throw new IllegalArgumentException("Classe inconnue");
        }
    }

    public String getNom_ville() {
        return nom_ville;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getPopulation() {
        return population;
    }

    public double getCoefficient() {
        return coefficient;
    }
}
