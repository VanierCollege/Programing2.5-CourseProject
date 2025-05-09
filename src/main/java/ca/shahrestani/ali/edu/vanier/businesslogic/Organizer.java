package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.BracketAwareSplitter;
import ca.shahrestani.ali.edu.vanier.tool.Util;

import java.time.ZonedDateTime;
import java.util.*;

public class Organizer extends User {
    private PersonalAccount account;
    private Set<Reimbursement> completedReimbursements;
    private Set<Reimbursement> pendingReimbursements;

    public Organizer(String name) {
        this(null, name, null, null, null, null, null);
    }

    public Organizer(String id, String name, ZonedDateTime createdAt, ZonedDateTime lastSystemAccess, PersonalAccount account, Set<Reimbursement> completedReimbursements, Set<Reimbursement> pendingReimbursements) {
        super(id, name,  UserType.ORGANIZER, createdAt, lastSystemAccess);
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

    /**
     * Add a pending reimbursement to the pending list.
     *
     * @param reimbursement the reimbursement to record
     */
    public void recordPendingReimbursement(Reimbursement reimbursement) {
        Objects.requireNonNull(reimbursement);
        if (!reimbursement.getStatus().equals(ReimbursementStatus.PENDING)) {
            throw new LogicallyInvalidActionException("Cannot record a completed reimbursement as pending");
        }

        pendingReimbursements.add(reimbursement);
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
//                ", completedReimbursements=" + completedReimbursements + // Recursive
//                ", pendingReimbursements=" + pendingReimbursements + // Recursive
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
        public Organizer load(String str, Map<String, Object> dependencies) {
            // type, id, name, createdAt, lastSystemAccess, personalAccount

            List<String> userData = BracketAwareSplitter.splitIgnoringBrackets(str);

            String id = Util.requireStringNotEmpty(userData.get(1));
            String name = Util.requireStringNotEmpty(userData.get(2));
            ZonedDateTime createdAt = ZonedDateTime.parse(userData.get(3));
            ZonedDateTime lastSystemAccess = ZonedDateTime.parse(userData.get(4));  // TODO: Handle null
            String personalAccountReference = userData.get(5);

            Account account = ((Map<String, Account>) dependencies.get("GLOBAL_ACCOUNTS")).getOrDefault(personalAccountReference, null);
            if (!(account instanceof PersonalAccount)) {
                throw new IllegalArgumentException(
                        String.format("Organizer (uID:%s) references an invalid account (aID:%s)", id, personalAccountReference)
                );
            }

            return new Organizer(
                    id,
                    name,
                    createdAt,
                    lastSystemAccess,
                    (PersonalAccount) account,
                    null,
                    null
            );
        }
    }
}
