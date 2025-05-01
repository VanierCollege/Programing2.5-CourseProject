package ca.shahrestani.ali.edu.vanier.businesslogic;

import java.time.ZonedDateTime;

public class Organizer extends User {
    public Organizer(String name) {
        super(name, UserType.ORGANIZER);
    }

    public Organizer(String id, String name, UserType type, ZonedDateTime createdAt, ZonedDateTime lastSystemAccess) {
        super(id, name, type, createdAt, lastSystemAccess);
    }

    /* OVERRIDE METHODS */

    @Override
    public String toString() {
        return "Organizer{" +
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

    public static class OrganizerFactory extends UserFactory<Organizer> {
        @Override
        public Organizer load(String str) {
            return null;
        }
    }
}
