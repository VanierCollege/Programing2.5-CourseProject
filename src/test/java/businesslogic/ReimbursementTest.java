package businesslogic;

import ca.shahrestani.ali.edu.vanier.businesslogic.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReimbursementTest {

    @Test
    public void testApprove_example() {
        Organizer requester = new Organizer("Test");
        Treasurer treasurer = new Treasurer("Test");
        Project project = new Project("Test", "Testing Project", null);
        Reimbursement reimbursement = new Reimbursement("TEST", 5, requester, project);
        Assertions.assertEquals(ReimbursementStatus.PENDING, reimbursement.getStatus());
        reimbursement.approve(treasurer);
        Assertions.assertEquals(ReimbursementStatus.APPROVED, reimbursement.getStatus());
    }

    @Test
    public void testApprove_null() {
        Organizer requester = new Organizer("Test");
        Treasurer treasurer = new Treasurer("Test");
        Project project = new Project("Test", "Testing Project", null);
        Reimbursement reimbursement = new Reimbursement("TEST", 5, requester, project);
        Assertions.assertThrows(NullPointerException.class, () -> reimbursement.approve(null));
    }

    @Test
    public void testApprove_alreadyApproved() {
        Organizer requester = new Organizer("Test");
        Treasurer treasurer = new Treasurer("Test");
        Project project = new Project("Test", "Testing Project", null);
        Reimbursement reimbursement = new Reimbursement("TEST", 5, requester, project);
        reimbursement.approve(treasurer);
        Assertions.assertEquals(ReimbursementStatus.APPROVED, reimbursement.getStatus());
        Assertions.assertThrows(LogicallyInvalidActionException.class, () -> reimbursement.approve(treasurer));
    }

    @Test
    public void testReject_example() {
        Organizer requester = new Organizer("Test");
        Treasurer treasurer = new Treasurer("Test");
        Project project = new Project("Test", "Testing Project", null);
        Reimbursement reimbursement = new Reimbursement("TEST", 5, requester, project);
        Assertions.assertEquals(ReimbursementStatus.PENDING, reimbursement.getStatus());
        reimbursement.reject(treasurer);
        Assertions.assertEquals(ReimbursementStatus.REJECTED, reimbursement.getStatus());
    }

    @Test
    public void testReject_null() {
        Organizer requester = new Organizer("Test");
        Treasurer treasurer = new Treasurer("Test");
        Project project = new Project("Test", "Testing Project", null);
        Reimbursement reimbursement = new Reimbursement("TEST", 5, requester, project);
        Assertions.assertThrows(NullPointerException.class, () -> reimbursement.reject(null));
    }

    @Test
    public void testReject_alreadyRejected() {
        Organizer requester = new Organizer("Test");
        Treasurer treasurer = new Treasurer("Test");
        Project project = new Project("Test", "Testing Project", null);
        Reimbursement reimbursement = new Reimbursement("TEST", 5, requester, project);
        reimbursement.reject(treasurer);
        Assertions.assertEquals(ReimbursementStatus.REJECTED, reimbursement.getStatus());
        Assertions.assertThrows(LogicallyInvalidActionException.class, () -> reimbursement.reject(treasurer));
    }
}
