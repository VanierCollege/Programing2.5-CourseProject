package ca.shahrestani.ali.edu.vanier.businesslogic;

public class Organizer extends User<Organizer> {

    /* SAVABLE METHODS */

    @Override
    public String export() {
        return "";
    }

    public static class OrganizerFactory extends UserFactory<Organizer> {
        @Override
        public Organizer load(String str) {
            return null;
        }
    }
}
