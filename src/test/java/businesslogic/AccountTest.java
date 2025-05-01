package businesslogic;

import ca.shahrestani.ali.edu.vanier.businesslogic.Account;
import ca.shahrestani.ali.edu.vanier.businesslogic.PersonalAccount;
import ca.shahrestani.ali.edu.vanier.businesslogic.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AccountTest {

    @Test // TODO
    public void testAddTransaction_example() {
        Account account = new PersonalAccount("Test", 5.00);
        Transaction transaction = new Transaction();
        account.addTransaction(transaction);
        List<Transaction> result = account.getTransactionList();
        Assertions.assertTrue(result.contains(transaction) && account.getBalance() == 5);
    }

    @Test // TODO
    public void testAddTransaction_noBalance() {
        Account account = new PersonalAccount("Test");
        Transaction transaction = new Transaction();
        account.addTransaction(transaction);
        List<Transaction> result = account.getTransactionList();
        Assertions.assertTrue(result.contains(transaction) && account.getBalance() == 0);
    }

    @Test
    public void testAddTransaction_null() {
        Account account = new PersonalAccount("Test", 5.00);
        Transaction transaction = null;
        Assertions.assertThrows(NullPointerException.class, () -> account.addTransaction(transaction));
    }
}
