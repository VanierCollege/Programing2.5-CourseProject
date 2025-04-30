import ca.shahrestani.ali.edu.vanier.Main;
import ca.shahrestani.ali.edu.vanier.businesslogic.Organization;
import ca.shahrestani.ali.edu.vanier.businesslogic.Project;
import ca.shahrestani.ali.edu.vanier.businesslogic.Reimbursement;
import ca.shahrestani.ali.edu.vanier.businesslogic.Transaction;
import ca.shahrestani.ali.edu.vanier.tool.DataManager;
import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class MainTest {

    /**
     * This method tests Main.registerBusinessFactories() and DataManager.registerFactory()
     */
    @Test
    public void testRegisterBusinessFactories() {
        Main.registerBusinessFactories();
        Map<String, SavableFactory<? extends Savable>> result = DataManager.getFactories();

        Assertions.assertTrue(result.size() >= 9);
        Assertions.assertTrue(
//                result.containsKey("User")
                result.containsKey("Organizer")
                && result.containsKey("Treasurer")
                && result.containsKey("SystemAdministrator")

//                result.containsKey("Account")
                && result.containsKey("PersonalAccount")
                && result.containsKey("BudgetAccount")

                && result.containsKey("Organization")
                && result.containsKey("Project")
                && result.containsKey("Transaction")
                && result.containsKey("Reimbursement")
        );
    }
}
