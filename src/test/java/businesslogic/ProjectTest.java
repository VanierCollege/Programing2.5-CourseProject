package businesslogic;

import ca.shahrestani.ali.edu.vanier.businesslogic.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProjectTest {

    @Test
    public void testRecordCompletedReimbursement_example() {
        Organizer organizer = new Organizer("TEST");
        Project project = new Project("Test", "Testing Project", null);
        Reimbursement reimbursement = new Reimbursement("Test", 5.00, organizer, project);
        reimbursement.approve(new Treasurer("Test"));
        project.recordCompletedReimbursement(reimbursement);
        Assertions.assertTrue(project.getPendingReimbursements().isEmpty()
                && project.getCompletedReimbursements().size() == 1
                && project.getCompletedReimbursements().contains(reimbursement));
    }

    @Test
    public void testRecordCompletedReimbursement_notComplete() {
        Organizer organizer = new Organizer("TEST");
        Project project = new Project("Test", "Testing Project", null);
        Reimbursement reimbursement = new Reimbursement("Test", 5.00, organizer, project);
        Assertions.assertThrows(LogicallyInvalidActionException.class, () -> project.recordCompletedReimbursement(reimbursement));
    }

    @Test
    public void testAddExpense_example() {
        double balance = 3.00;
        double input = 1.00;
        BudgetAccount account = new BudgetAccount("Test", balance);
        Project project = new Project("Test", "Testing Project", account);
        project.addExpense(input);
        Transaction transaction = account.getTransactionList().getFirst();
        Assertions.assertTrue(account.getBalance() == balance - input
                && transaction.getType().equals(TransactionType.EXPENSE)
                && transaction.getAmount() == input
                && transaction.getDescription().contains(project.getName()));
    }

    @Test
    public void testAddExpense_negative() {
        double input = 1.00;
        Project project = new Project("Test", "Testing Project", null);
        BudgetAccount account = project.getFundingAccount();
        project.addExpense(input);
        Transaction transaction = account.getTransactionList().getFirst();
        Assertions.assertTrue(account.getBalance() == -input
                && transaction.getType().equals(TransactionType.EXPENSE)
                && transaction.getAmount() == input
                && transaction.getDescription().contains(project.getName()));
    }
}
