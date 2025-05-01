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

    /**
     * Create a new (default) system administrator to be the first user in an organization
     *
     * @param orgName the organization's name
     * @return the default system administrator
     */
    public static SystemAdministrator newDefaultSystemAdministrator(String orgName) {
        return new SystemAdministrator(
                null,
                orgName + " System Administrator",
                UserType.SYSTEM_ADMINISTRATOR,
                null,
                null
        );
    }

    /* OVERRIDE METHODS */

    @Override
    public String toString() {
        return "SystemAdministrator{" +
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

    public static class SystemAdministratorFactory extends UserFactory<SystemAdministrator> {
        @Override
        public SystemAdministrator load(String str) {
            return null;
        }
    }
}
