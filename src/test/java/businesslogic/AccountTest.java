package businesslogic;

import ca.shahrestani.ali.edu.vanier.businesslogic.Account;
import ca.shahrestani.ali.edu.vanier.businesslogic.PersonalAccount;
import ca.shahrestani.ali.edu.vanier.businesslogic.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTest {

    @Test
    public void testAddTransaction_example() {

    }

    @Test
    public void testAddTransaction_null() {
        Account account = new PersonalAccount("Test", 5.00);
        Transaction transaction = null;
        Assertions.assertThrows(NullPointerException.class, () -> account.addTransaction(transaction));
    }
}
