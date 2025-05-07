package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Savable {
    private String name;
    private List<User> users;
    private List<Project> projects;
    private List<Account> accounts;

    public Organization(String name) {
        this(name, null, null, null);
    }

    public Organization(String name, List<User> users, List<Project> projects, List<Account> accounts) {
        this.name = name;
        this.users = Objects.requireNonNullElse(users, new ArrayList<>());
        this.projects = Objects.requireNonNullElse(projects, new ArrayList<>());
        this.accounts = Objects.requireNonNullElse(accounts, new ArrayList<>());
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

    public List<User> getUsers() {
        return users;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<Account> getAccounts() {
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
