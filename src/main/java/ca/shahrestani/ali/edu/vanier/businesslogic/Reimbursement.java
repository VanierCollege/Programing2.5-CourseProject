package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.BracketAwareSplitter;
import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;
import ca.shahrestani.ali.edu.vanier.tool.Util;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Reimbursement implements Savable, Comparable<Reimbursement> {
    private final String id;
    private ReimbursementStatus status;
    private String description;
    private final double amount;
    private final Organizer requester;
    private Treasurer treasurer;
    private final Project project;
    private final ZonedDateTime requestedAt;
    private ZonedDateTime approvedAt;

    public Reimbursement(String description, double amount, Organizer requester, Project project) {
        this(null, ReimbursementStatus.PENDING, description, amount, requester, null, project, null, null);
    }

    public Reimbursement(String id, ReimbursementStatus status, String description, double amount, Organizer requester, Treasurer treasurer, Project project, ZonedDateTime requestedAt, ZonedDateTime approvedAt) {
        this.id = Objects.requireNonNullElse(id,
                Util.generateID(5, true, false, false)
        );
        this.status = Objects.requireNonNull(status);
        this.description = Objects.requireNonNull(description);
        this.amount = Math.abs(amount);
        this.requester = Objects.requireNonNull(requester);
        this.treasurer = treasurer;
        this.project = Objects.requireNonNull(project);
        this.requestedAt = Objects.requireNonNullElse(requestedAt, Util.getNow());
        this.approvedAt = approvedAt;
    }

    /**
     * Mark the reimbursement as approved.
     * <p></p>
     * Note that this method does not transfer any funds itself.
     *
     * @param treasurer the treasurer who approved the reimbursement
     */
    public void approve(Treasurer treasurer) {
        if (!status.equals(ReimbursementStatus.PENDING)) {
            throw new LogicallyInvalidActionException("Reimbursement already approved/rejected");
        }
        Objects.requireNonNull(treasurer);

        this.treasurer = treasurer;
        status = ReimbursementStatus.APPROVED;
        approvedAt = Util.getNow();
    }

    /**
     * Mark the reimbursement as rejected.
     *
     * @param treasurer the treasurer who rejected the reimbursement
     */
    public void reject(Treasurer treasurer) {
        if (!status.equals(ReimbursementStatus.PENDING)) {
            throw new LogicallyInvalidActionException("Reimbursement already approved/rejected");
        }
        Objects.requireNonNull(treasurer);

        this.treasurer = treasurer;
        status = ReimbursementStatus.REJECTED;
    }

    /* OVERRIDE METHODS */

    @Override
    public int compareTo(Reimbursement otherReimbursement) {
        return requestedAt.compareTo(otherReimbursement.getRequestedAt());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement that = (Reimbursement) o;
        return Double.compare(getAmount(), that.getAmount()) == 0 && Objects.equals(getId(), that.getId()) && getStatus() == that.getStatus() && Objects.equals(getProject(), that.getProject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStatus(), getAmount(), getProject());
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", requester=" + requester +
                ", treasurer=" + treasurer +
                ", project=" + project +
                ", requestedAt=" + requestedAt +
                ", approvedAt=" + approvedAt +
                '}';
    }

    /* GETTERS & SETTERS */

    public String getId() {
        return id;
    }

    public ReimbursementStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public Organizer getRequester() {
        return requester;
    }

    public Treasurer getTreasurer() {
        return treasurer;
    }

    public Project getProject() {
        return project;
    }

    public ZonedDateTime getRequestedAt() {
        return requestedAt;
    }

    public ZonedDateTime getApprovedAt() {
        return approvedAt;
    }

    /* SAVABLE METHODS */

    @Override
    public String export() {
        return "";
    }

    public static class ReimbursementFactory implements SavableFactory<Reimbursement> {
        @Override
        public Reimbursement load(String str, Map<String, Object> dependencies) {
            // id, status, description, amount, requester, treasurer, project, requestedAt, approvedAt

            List<String> reimbursementData = BracketAwareSplitter.splitIgnoringBrackets(str);

            String id = Util.requireStringNotEmpty(reimbursementData.get(0));
            ReimbursementStatus status = ReimbursementStatus.valueOf(reimbursementData.get(1));
            String description = reimbursementData.get(2);
            double amount = Double.parseDouble(reimbursementData.get(3));
            String requesterReference = reimbursementData.get(4);
            String treasurerReference = reimbursementData.get(5); // TODO: Handle null
            String projectReference = reimbursementData.get(6);
            ZonedDateTime requestedAt = ZonedDateTime.parse(reimbursementData.get(7));
            ZonedDateTime approvedAt = ZonedDateTime.parse(reimbursementData.get(8)); // TODO: Handle null

            Project project = ((Map<String, Project>) dependencies.get("GLOBAL_PROJECTS")).getOrDefault(projectReference, null);
            if (project == null) {
                throw new IllegalArgumentException(
                        String.format("Reimbursement (rID:%s) references an invalid project (pNAME:%s)", id, projectReference)
                );
            }
            User requester = ((Map<String, User>) dependencies.get("GLOBAL_USERS")).getOrDefault(requesterReference, null);
            if (!(requester instanceof Organizer)) {
                throw new IllegalArgumentException(
                        String.format("Reimbursement (rID:%s) references an invalid requester (uID:%s)", id, requesterReference)
                );
            }
            User treasurer = ((Map<String, User>) dependencies.get("GLOBAL_USERS")).getOrDefault(treasurerReference, null);
            if (!(treasurer instanceof Treasurer)) { // TODO: Handle null
                throw new IllegalArgumentException(
                        String.format("Reimbursement (rID:%s) references an invalid treasurer (uID:%s)", id, treasurerReference)
                );
            }

            return new Reimbursement(
                    id,
                    status,
                    description,
                    amount,
                    (Organizer) requester,
                    (Treasurer) treasurer,
                    project,
                    requestedAt,
                    approvedAt
            );
        }
    }
}
