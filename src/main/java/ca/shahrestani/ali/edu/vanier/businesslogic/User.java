package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;
import ca.shahrestani.ali.edu.vanier.tool.Util;

import java.time.ZonedDateTime;

public abstract class User implements Savable {
    protected String id;
    protected String name;
    protected UserType type;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime lastSystemAccess;

    public User(String name, UserType type) {
        this(Util.generateID(6, false, true, true),
                name, type, Util.getNow(), null);
    }

    public User(String id, String name, UserType type, ZonedDateTime createdAt, ZonedDateTime lastSystemAccess) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
        this.lastSystemAccess = lastSystemAccess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getLastSystemAccess() {
        return lastSystemAccess;
    }

    public void setLastSystemAccess(ZonedDateTime lastSystemAccess) {
        this.lastSystemAccess = lastSystemAccess;
    }

    public String getId() {
        return id;
    }

    public UserType getType() {
        return type;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public abstract static class UserFactory<T extends User> implements SavableFactory<T> {
        @Override
        public abstract T load(String str);
    }
}
