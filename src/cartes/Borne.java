package cartes;

public class Borne extends Carte {
    private int km;

    public Borne(int km) {
        this.km = km;
    }

    public int getKm() {
        return km;
    }

    @Override
    public String toString() {
        return km + "KM";
    }
}