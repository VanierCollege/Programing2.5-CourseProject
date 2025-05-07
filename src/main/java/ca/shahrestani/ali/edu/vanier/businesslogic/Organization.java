package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

public class Organization implements Savable {
    private String name;
    private Set<User> users;
    private Set<Project> projects;
    private Set<Account> accounts;

    public Organization(String name) {
        this(name, null, null, null);
    }

    public Organization(String name, Set<User> users, Set<Project> projects, Set<Account> accounts) {
        this.name = name;
        this.users = Objects.requireNonNullElse(users, new HashSet<>());
        this.projects = Objects.requireNonNullElse(projects, new HashSet<>());
        this.accounts = Objects.requireNonNullElse(accounts, new HashSet<>());
    }

    /**
     * Add a user to the organization's list
     *
     * @param user the user to add
     * @return whether the user is new (already added) or not
     */
    public boolean addUser(User user) {
        Objects.requireNonNull(user);
        return users.add(user);
    }

    /**
     * Remove a user from the organization's list
     *
     * @param user the user to add
     * @return whether the user was present or not
     */
    public boolean removeUser(User user) {
        Objects.requireNonNull(user);
        return users.remove(user);
    }

    /**
     * Add a project to the organization's list
     *
     * @param project the project to add
     * @return whether the project is new (already added) or not
     */
    public boolean addProject(Project project) {
        Objects.requireNonNull(project);
        return projects.add(project);
    }

    /**
     * Remove a project from the organization's list
     *
     * @param project the project to add
     * @return whether the project was present or not
     */
    public boolean removeProject(Project project) {
        Objects.requireNonNull(project);
        return projects.remove(project);
    }



    /**
     * Add an account to the organization's list
     *
     * @param account the account to add
     * @return whether the account is new (already added) or not
     */
    public boolean addAccount(Account account) {
        Objects.requireNonNull(account);
        return accounts.add(account);
    }

    /**
     * Remove an account from the organization's list
     *
     * @param account the account to add
     * @return whether the account was present or not
     */
    public boolean removeAccount(Account account) {
        Objects.requireNonNull(account);
        return accounts.remove(account);
    }

    /* OVERRIDE METHODS */

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", users=" + users +
                ", projects=" + projects +
                ", accounts=" + accounts +
                '}';
    }

    /* GETTERS & SETTERS */

    public String getName() {
        return name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    /* SAVABLE METHODS */

    @Override
    public String export() {
        return "";
    }

    public static class OrganizationFactory implements SavableFactory<Organization> {
        @Override
        public Organization load(String str) {
            return null;
        }
    }
}
