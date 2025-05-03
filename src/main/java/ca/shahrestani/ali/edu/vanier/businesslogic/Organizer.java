package ca.shahrestani.ali.edu.vanier.businesslogic;

import java.time.ZonedDateTime;
import java.util.*;

public class Organizer extends User {
    private PersonalAccount account;
    private Set<Reimbursement> completedReimbursements;
    private Set<Reimbursement> pendingReimbursements;

    public Organizer(String name) {
        this(null, name, UserType.ORGANIZER, null, null, null, null, null);
    }

    public Organizer(String id, String name, UserType type, ZonedDateTime createdAt, ZonedDateTime lastSystemAccess, PersonalAccount account, Set<Reimbursement> completedReimbursements, Set<Reimbursement> pendingReimbursements) {
        super(id, name, type, createdAt, lastSystemAccess);
        this.account = Objects.requireNonNullElse(account, new PersonalAccount(name + "'s Personal Account"));
        this.completedReimbursements = Objects.requireNonNullElse(completedReimbursements, new LinkedHashSet<>());
        this.pendingReimbursements = Objects.requireNonNullElse(pendingReimbursements, new HashSet<>());
    }

    /**
     * Move the reimbursement from the pending list to the completed list.
     * <p></p>
     * If the reimbursement is not present in the pending list, it will still be added to the completed list.
     *
     * @param reimbursement the reimbursement to record
     */
    public void recordCompletedReimbursement(Reimbursement reimbursement) {
        Objects.requireNonNull(reimbursement);
        if (reimbursement.getStatus().equals(ReimbursementStatus.PENDING)) {
            throw new LogicallyInvalidActionException("Cannot record a pending reimbursement as completed");
        }

        pendingReimbursements.remove(reimbursement);
        completedReimbursements.add(reimbursement);
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

    public List<Reimbursement> getPendingReimbursements() {
        return new ArrayList<>(pendingReimbursements);
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
