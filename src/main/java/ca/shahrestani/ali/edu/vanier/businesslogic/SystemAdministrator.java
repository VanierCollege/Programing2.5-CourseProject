package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.BracketAwareSplitter;
import ca.shahrestani.ali.edu.vanier.tool.Util;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SystemAdministrator extends User {
    public SystemAdministrator(String name) {
        super(name, UserType.SYSTEM_ADMINISTRATOR);
    }

    public SystemAdministrator(String id, String name, ZonedDateTime createdAt, ZonedDateTime lastSystemAccess) {
        super(id, name, UserType.SYSTEM_ADMINISTRATOR, createdAt, lastSystemAccess);
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
            // type, id, name, createdAt, lastSystemAccess

            List<String> userData = BracketAwareSplitter.splitIgnoringBrackets(str);

            String id = Util.requireStringNotEmpty(userData.get(1));
            String name = Util.requireStringNotEmpty(userData.get(2));
            ZonedDateTime createdAt = ZonedDateTime.parse(userData.get(3));
            ZonedDateTime lastSystemAccess = ZonedDateTime.parse(userData.get(4));  // TODO: Handle null

            return new SystemAdministrator(
                    id,
                    name,
                    createdAt,
                    lastSystemAccess
            );
        }
    }
}
