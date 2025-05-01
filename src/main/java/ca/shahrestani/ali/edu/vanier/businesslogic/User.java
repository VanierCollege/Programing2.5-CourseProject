package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;
import ca.shahrestani.ali.edu.vanier.tool.Util;

import java.time.ZonedDateTime;
import java.util.Objects;

public abstract class User implements Savable, Comparable<User> {
    protected String id;
    protected String name;
    protected UserType type;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime lastSystemAccess;

    public User(String name, UserType type) {
        this(null, name, type, null, null);
    }

    public User(String id, String name, UserType type, ZonedDateTime createdAt, ZonedDateTime lastSystemAccess) {
        this.id = Objects.requireNonNullElse(id,
                Util.generateID(5, false, true, true)
        );
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
        this.createdAt = Objects.requireNonNullElse(createdAt, Util.getNow());
        this.lastSystemAccess = lastSystemAccess;
    }

    /* OVERRIDE METHODS */

    @Override
    public int compareTo(User otherUser) {
        return Integer.compare(this.type.level, otherUser.type.level);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getName(), user.getName()) && getType() == user.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getType());
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", lastSystemAccess=" + lastSystemAccess +
                '}';
    }

    /* GETTERS & SETTERS */

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

    /* SAVABLE METHODS */

    public abstract static class UserFactory<T extends User> implements SavableFactory<T> {
        @Override
        public abstract T load(String str);
    }
}
