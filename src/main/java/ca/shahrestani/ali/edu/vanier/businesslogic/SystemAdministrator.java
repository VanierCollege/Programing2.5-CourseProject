package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;

import java.time.ZonedDateTime;

public class SystemAdministrator extends User {
    public SystemAdministrator(String name) {
        super(name, UserType.SYSTEM_ADMINISTRATOR);
    }

    public SystemAdministrator(String id, String name, UserType type, ZonedDateTime createdAt, ZonedDateTime lastSystemAccess) {
        super(id, name, type, createdAt, lastSystemAccess);
    }

    /* SAVABLE METHODS */

    @Override
    public String export() {
        return "";
    }

    public static class SystemAdministratorFactory extends UserFactory<SystemAdministrator> {
        @Override
        public SystemAdministrator load(String str) {
            return null;
        }
    }
}
