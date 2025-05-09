package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;
import ca.shahrestani.ali.edu.vanier.tool.Util;

import java.time.ZonedDateTime;
import java.util.*;

public class Project implements Savable {
    private String name;
    private String description;
    private BudgetAccount fundingAccount;
    private Set<Reimbursement> completedReimbursements;
    private Set<Reimbursement> pendingReimbursements;
    private final ZonedDateTime createdAt;

    public Project(String name, String description, BudgetAccount fundingAccount) {
        this(name, description, fundingAccount, null, null, null);
    }

    public Project(String name, String description, BudgetAccount fundingAccount, Set<Reimbursement> completedReimbursements, Set<Reimbursement> pendingReimbursements, ZonedDateTime createdAt) {
        this.name = Objects.requireNonNull(name);
        this.description = Objects.requireNonNull(description);
        this.fundingAccount = Objects.requireNonNullElse(fundingAccount, new BudgetAccount("Budget Account for " + name + " Project"));
        this.completedReimbursements = Objects.requireNonNullElse(completedReimbursements, new LinkedHashSet<>());
        this.pendingReimbursements = Objects.requireNonNullElse(pendingReimbursements, new HashSet<>());;
        this.createdAt = Objects.requireNonNullElse(createdAt, Util.getNow());
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

    /**
     * Helper method to record a new expense in the project's funding account.
     * <p></p>
     * Note that this method will not prevent overdrafts.
     *
     * @param amount the expense amount
     */
    public void addExpense(double amount) {
        amount = Math.abs(amount);

        Transaction transaction = new Transaction(
                TransactionType.EXPENSE,
                String.format("EXPENSE FOR PROJECT (pNAME:%s)", name),
                amount
        );
        fundingAccount.recordTransaction(transaction);
    }

    /* OVERRIDE METHODS */

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(getName(), project.getName()) && Objects.equals(getFundingAccount(), project.getFundingAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getFundingAccount());
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", fundingAccount=" + fundingAccount +
                ", completedReimbursements=" + completedReimbursements +
                ", pendingReimbursements=" + pendingReimbursements +
                ", createdAt=" + createdAt +
                '}';
    }

    /* GETTERS & SETTERS */

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BudgetAccount getFundingAccount() {
        return fundingAccount;
    }

    public void setFundingAccount(BudgetAccount fundingAccount) {
        this.fundingAccount = fundingAccount;
    }

    public List<Reimbursement> getCompletedReimbursements() {
        return new ArrayList<>(completedReimbursements);
    }

    public List<Reimbursement> getPendingReimbursements() {
        return new ArrayList<>(pendingReimbursements);
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    /* SAVABLE METHODS */

    @Override
    public String export() {
        return "";
    }

    public static class ProjectFactory implements SavableFactory<Project> {
        @Override
        public Project load(String str, Map<String, Object> dependencies) {
            return null;
        }
    }
}
