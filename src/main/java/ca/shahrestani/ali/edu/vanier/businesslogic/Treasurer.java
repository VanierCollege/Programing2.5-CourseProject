package ca.shahrestani.ali.edu.vanier.businesslogic;

import java.time.ZonedDateTime;

public class Treasurer extends User<Treasurer> {
    public Treasurer(String name) {
        super(name, UserType.TREASURER);
    }

    public Treasurer(String id, String name, UserType type, ZonedDateTime createdAt, ZonedDateTime lastSystemAccess) {
        super(id, name, type, createdAt, lastSystemAccess);
    }

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
}
