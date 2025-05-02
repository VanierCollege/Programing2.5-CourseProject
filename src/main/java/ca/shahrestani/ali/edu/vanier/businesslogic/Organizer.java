package ca.shahrestani.ali.edu.vanier.businesslogic;

import java.time.ZonedDateTime;
import java.util.*;

public class Organizer extends User {
    private PersonalAccount account;
    private Stack<Reimbursement> completedReimbursements;
    private Queue<Reimbursement> pendingReimbursements;

    public Organizer(String name) {
        this(null, name, UserType.ORGANIZER, null, null, null, null, null);
    }

    public Organizer(String id, String name, UserType type, ZonedDateTime createdAt, ZonedDateTime lastSystemAccess, PersonalAccount account, Stack<Reimbursement> completedReimbursements, Queue<Reimbursement> pendingReimbursements) {
        super(id, name, type, createdAt, lastSystemAccess);
        this.account = Objects.requireNonNullElse(account, new PersonalAccount(name + "'s Personal Account"));
        this.completedReimbursements = Objects.requireNonNullElse(completedReimbursements, new Stack<>());
        this.pendingReimbursements = Objects.requireNonNullElse(pendingReimbursements, new PriorityQueue<>());
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

    /* GETTERS & SETTERS */

    public PersonalAccount getAccount() {
        return account;
    }

    public List<Reimbursement> getCompletedReimbursements() {
        return new ArrayList<>(completedReimbursements);
    }

    public Queue<Reimbursement> getPendingReimbursements() {
        return pendingReimbursements;
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
