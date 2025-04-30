package ca.shahrestani.ali.edu.vanier.businesslogic;

public class Treasurer extends User<Treasurer> {

    /* SAVABLE METHODS */

    @Override
    public String export() {
        return "";
    }

    public static class TreasurerFactory extends UserFactory<Treasurer> {
        @Override
        public Treasurer load(String str) {
            return null;
        }
    }

    @Override
    public UserFactory<Treasurer> getFactory() {
        return null;
    }
}
