package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.BracketAwareSplitter;
import ca.shahrestani.ali.edu.vanier.tool.Util;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Treasurer extends User {
    public Treasurer(String name) {
        super(name, UserType.TREASURER);
    }

    public Treasurer(String id, String name, ZonedDateTime createdAt, ZonedDateTime lastSystemAccess) {
        super(id, name, UserType.TREASURER, createdAt, lastSystemAccess);
    }

    /**
     * Approve a reimbursement and transfer the funds.
     *
     * @param reimbursement the reimbursement to approve
     */
    public void approveReimbursement(Reimbursement reimbursement) {
        Objects.requireNonNull(reimbursement);
        Project project = reimbursement.getProject();
        if (project.getFundingAccount().willOverdraft(reimbursement.getAmount())) {
            throw new LogicallyInvalidActionException("No sufficient fund to complete the reimbursement");
        }

        String msg = String.format("REIMBURSEMENT (rID:[%s]) FOR PROJECT (pNAME:%s) APPROVED BY (uID:[%s])", reimbursement.getId(), project.getName(), id);
        reimbursement.approve(this);        // Pass/Fail Moment
        Transaction deposit = new Transaction(
                TransactionType.REIMBURSEMENT,
                msg,
                reimbursement.getAmount()
        );
        Transaction transfer = new Transaction(
                TransactionType.EXPENSE,
                msg,
                reimbursement.getAmount()
        );
        reimbursement.getRequester().getAccount().recordTransaction(deposit);
        reimbursement.getRequester().recordCompletedReimbursement(reimbursement);
        project.getFundingAccount().recordTransaction(transfer);
        project.recordCompletedReimbursement(reimbursement);
    }

    /**
     * Reject a reimbursement and don't transfer the funds.
     *
     * @param reimbursement the reimbursement to reject
     */
    public void rejectReimbursement(Reimbursement reimbursement) {
        Objects.requireNonNull(reimbursement);
        Project project = reimbursement.getProject();

        String msg = String.format("REIMBURSEMENT (rID:[%s]) FOR PROJECT (pNAME:%s) APPROVED BY (uID:[%s])", reimbursement.getId(), project.getName(), id);
        reimbursement.reject(this);        // Pass/Fail Moment
        project.recordCompletedReimbursement(reimbursement);
        reimbursement.getRequester().recordCompletedReimbursement(reimbursement);
    }

    /* OVERRIDE METHODS */

    @Override
    public String toString() {
        return "Treasurer{" +
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

    public static class TreasurerFactory extends UserFactory<Treasurer> {
        @Override
        public Treasurer load(String str, Map<String, Object> dependencies) {
            // type, id, name, createdAt, lastSystemAccess

            List<String> userData = BracketAwareSplitter.splitIgnoringBrackets(str);

            String id = Util.requireStringNotEmpty(userData.get(1));
            String name = Util.requireStringNotEmpty(userData.get(2));
            ZonedDateTime createdAt = ZonedDateTime.parse(userData.get(3));
            ZonedDateTime lastSystemAccess = ZonedDateTime.parse(userData.get(4));  // TODO: Handle null

            return new Treasurer(
                    id,
                    name,
                    createdAt,
                    lastSystemAccess
            );
        }
    }
}
