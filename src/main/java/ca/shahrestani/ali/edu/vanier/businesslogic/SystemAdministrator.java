package ca.shahrestani.ali.edu.vanier.businesslogic;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;

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
     * @return a new system administrator
     */
    public static SystemAdministrator newDefaultSystemAdministrator(String orgName) {
        Objects.requireNonNull(orgName);

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
        public SystemAdministrator load(String str, Map<String, Object> dependencies) {
            return null;
        }
    }
}
