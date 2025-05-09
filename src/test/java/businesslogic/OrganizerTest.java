package businesslogic;

import ca.shahrestani.ali.edu.vanier.businesslogic.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrganizerTest {

    @Test
    public void testRecordCompletedReimbursement_example() {
        Organizer organizer = new Organizer("TEST");
        Project project = new Project("Test", "Testing Project", null);
        Reimbursement reimbursement = new Reimbursement("Test", 5.00, organizer, project);
        reimbursement.approve(new Treasurer("Test"));
        organizer.recordCompletedReimbursement(reimbursement);
        Assertions.assertTrue(organizer.getPendingReimbursements().isEmpty()
                && organizer.getCompletedReimbursements().size() == 1
                && organizer.getCompletedReimbursements().contains(reimbursement));
    }

    @Test
    public void testRecordCompletedReimbursement_notComplete() {
        Organizer organizer = new Organizer("TEST");
        Project project = new Project("Test", "Testing Project", null);
        Reimbursement reimbursement = new Reimbursement("Test", 5.00, organizer, project);
        Assertions.assertThrows(LogicallyInvalidActionException.class, () -> organizer.recordCompletedReimbursement(reimbursement));
    }

    @Test
    public void testRecordPendingReimbursement_example() {
        Organizer organizer = new Organizer("TEST");
        Project project = new Project("Test", "Testing Project", null);
        Reimbursement reimbursement = new Reimbursement("Test", 5.00, organizer, project);
        organizer.recordPendingReimbursement(reimbursement);
        Assertions.assertTrue(organizer.getCompletedReimbursements().isEmpty()
                && organizer.getPendingReimbursements().size() == 1
                && organizer.getPendingReimbursements().contains(reimbursement));
    }

    @Test
    public void testRecordCPendingReimbursement_complete() {
        Organizer organizer = new Organizer("TEST");
        Project project = new Project("Test", "Testing Project", null);
        Reimbursement reimbursement = new Reimbursement("Test", 5.00, organizer, project);
        reimbursement.approve(new Treasurer("Test"));
        Assertions.assertThrows(LogicallyInvalidActionException.class, () -> organizer.recordPendingReimbursement(reimbursement));
    }
}
