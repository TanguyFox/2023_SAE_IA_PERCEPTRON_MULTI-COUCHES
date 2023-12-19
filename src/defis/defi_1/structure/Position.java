package defis.defi_1.structure;

import java.util.Objects;

public class Position {

    /**
     * Longtitude et latitude en radians
     */
    private double longitude;
    private double latitude;

    public Position(double longitude, double latitude) {
        this.longitude = Math.toRadians(longitude);
        this.latitude = Math.toRadians(latitude);
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getPosDegree() {
        return "longitude: " + Math.toDegrees(longitude) + " | latitude: " + Math.toDegrees(latitude);
    }


    /**
     * Distance between two positions
     * @param position
     * @return distance in km
     */
    public double distance(Position position){
        double a = Math.pow(Math.sin((position.getLatitude() - latitude) / 2), 2) +
                Math.cos(latitude) * Math.cos(position.getLatitude()) *
                Math.pow(Math.sin((position.getLongitude() - longitude) / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6371 * c;
    }

    public static double distanceEntre(Position p1, Position p2){
        return p1.distance(p2);
    }

    @Override
    public String toString() {
        return "Position{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return Double.compare(longitude, position.longitude) == 0 && Double.compare(latitude, position.latitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }

}
