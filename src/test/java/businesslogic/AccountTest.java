package businesslogic;

import ca.shahrestani.ali.edu.vanier.businesslogic.Account;
import ca.shahrestani.ali.edu.vanier.businesslogic.PersonalAccount;
import ca.shahrestani.ali.edu.vanier.businesslogic.Transaction;
import ca.shahrestani.ali.edu.vanier.businesslogic.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


public class AccountTest {

    @Test
    public void testRecordTransaction_example() {
        Account account = new PersonalAccount("Test", 5.00);
        Transaction input = new Transaction(TransactionType.EXPENSE, "TEST", 2.00);
        double newBalance = account.recordTransaction(input);
        List<Transaction> result = account.getTransactionList();
        Assertions.assertTrue(result.contains(input) && newBalance == 3);
    }

    @Test
    public void testRecordTransaction_noAmount() {
        Account account = new PersonalAccount("Test");
        Transaction input = new Transaction(TransactionType.DEPOSIT, "TEST", 0.00);
        account.recordTransaction(input);
        List<Transaction> result = account.getTransactionList();
        Assertions.assertTrue(result.contains(input) && account.getBalance() == 0);
    }

    @Test
    public void testRecordTransaction_unsupportedType() {
        Account account = new PersonalAccount("Test");
        Transaction input = new Transaction(TransactionType.TRANSFER, "TEST", 4.00);
        Assertions.assertThrows(IllegalArgumentException.class, () -> account.recordTransaction(input));
    }


    @Test
    public void testRecordTransaction_null() {
        Account account = new PersonalAccount("Test", 5.00);
        Transaction input = null;
        Assertions.assertThrows(NullPointerException.class, () -> account.recordTransaction(input));
    }

    @Test
    public void testTransfer_example() {
        Account account1 = new PersonalAccount("Test1", 7.00);
        Account account2 = new PersonalAccount("Test2", 3.00);
        Transaction t1 = new Transaction(TransactionType.TRANSFER, "GIFT", 5.00);
        double newBalance = account1.transfer(t1, account2);
        Assertions.assertTrue(newBalance == 2.00
                && account1.getBalance() == 2.00
                && account2.getBalance() == 8.00);
    }

    @Test
    public void testTransfer_unsupportedType() {
        Account account1 = new PersonalAccount("Test1");
        Account account2 = new PersonalAccount("Test2");
        Transaction input = new Transaction(TransactionType.DEPOSIT, "TEST", 4.00);
        Assertions.assertThrows(IllegalArgumentException.class, () -> account1.transfer(input, account2));
    }

    @Test
    public void testTransfer_nullTransaction() {
        Account account1 = new PersonalAccount("Test", 5.00);
        Account account2 = new PersonalAccount("Test", 5.00);
        Transaction input = null;
        Assertions.assertThrows(NullPointerException.class, () -> account1.transfer(input, account2));
    }

    @Test
    public void testTransfer_nullAccount() {
        Account account1 = new PersonalAccount("Test", 5.00);
        Account account2 = null;
        Transaction input = new Transaction(TransactionType.TRANSFER, "TEST", 7.00);
        Assertions.assertThrows(NullPointerException.class, () -> account1.transfer(input, account2));
    }

    @Test
    public void testGetFilteredTransactions() {
        Account account1 = new PersonalAccount("Test1", 5.00);
        Account account2 = new PersonalAccount("Test2", 8.00);

        Assertions.assertTrue(account1.getTransactionList().isEmpty());

        Transaction t1 = new Transaction(TransactionType.DEPOSIT, "TEST", 1.00);
        Transaction t2 = new Transaction(TransactionType.REIMBURSEMENT, "TEST", 2.00);
        Transaction t3 = new Transaction(TransactionType.EXPENSE, "TEST", 3.00);
        Transaction t4 = new Transaction(TransactionType.EXPENSE, "TEST", 4.00);
        Transaction t5 = new Transaction(TransactionType.DEPOSIT, "TEST", 5.00);
        Transaction t6 = new Transaction(TransactionType.REIMBURSEMENT, "TEST", 6.00);
        Transaction t7 = new Transaction(TransactionType.REIMBURSEMENT, "TEST", 7.00);
        Transaction t8 = new Transaction(TransactionType.TRANSFER, "TEST", 13.00);

        account1.recordTransaction(t1);
        account1.recordTransaction(t2);
        account1.recordTransaction(t3);
        account1.recordTransaction(t4);
        account1.recordTransaction(t5);
        account1.recordTransaction(t6);
        account1.recordTransaction(t7);
        account1.transfer(t8, account2);

        List<Transaction> result1 = account1.getFilteredTransactions(TransactionType.DEPOSIT);
        List<Transaction> result2 = account1.getFilteredTransactions(TransactionType.EXPENSE);
        List<Transaction> result3 = account1.getFilteredTransactions(TransactionType.REIMBURSEMENT);
        List<Transaction> result4 = account1.getFilteredTransactions(TransactionType.TRANSFER);

        Assertions.assertTrue(result1.size() == 2
                && result1.containsAll(Arrays.asList(t1, t5)));
        Assertions.assertTrue(result2.size() == 2
                && result2.containsAll(Arrays.asList(t3, t4)));
        Assertions.assertTrue(result3.size() == 3
                && result3.containsAll(Arrays.asList(t2, t6, t7)));
        Assertions.assertTrue(result4.size() == 1
                && result4.containsAll(Arrays.asList(t8)));
    }

}
