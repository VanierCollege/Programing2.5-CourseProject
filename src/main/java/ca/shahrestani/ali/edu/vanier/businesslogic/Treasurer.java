package ca.shahrestani.ali.edu.vanier.businesslogic;

import java.time.ZonedDateTime;

public class Treasurer extends User {
    public Treasurer(String name) {
        super(name, UserType.TREASURER);
    }

    public Treasurer(String id, String name, UserType type, ZonedDateTime createdAt, ZonedDateTime lastSystemAccess) {
        super(id, name, type, createdAt, lastSystemAccess);
    }

    /* OVERRIDE METHODS */

    @Override
    public String toString() {
        return "Treasurer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", lastSystemAccess=" + lastSystemAccess +
                '}';
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
