package defis.defi1_new.problems;

import defis.defi1_new.framework.common.State;
import defis.defi1_new.framework.recherche.HasHeuristic;

public class GPSMapState extends State implements HasHeuristic {

    private String name=null;

    private double dist_to_goal=0;

    public GPSMapState(String n, double d){
        name = n;
        dist_to_goal = d; // l'heuristique
    }

    public State cloneState(){
        return new GPSMapState(name, dist_to_goal);
    }

    public boolean equalsState(State o){
        GPSMapState other = (GPSMapState) o;
        return (other.dist_to_goal == dist_to_goal) &&
            (name.equals(other.name));
    }

    public int hashState(){
        return 31 * Double.hashCode(dist_to_goal) + name.hashCode();
    }

    public String toString() {
        return "{"+name+","+dist_to_goal+"}";
    }

    public double getHeuristic(){
        return dist_to_goal;
    }

    public String getName() {
        return name;
    }
}
