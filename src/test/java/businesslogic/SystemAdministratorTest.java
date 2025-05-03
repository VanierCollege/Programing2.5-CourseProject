package businesslogic;

import ca.shahrestani.ali.edu.vanier.businesslogic.SystemAdministrator;
import ca.shahrestani.ali.edu.vanier.businesslogic.UserType;
import ca.shahrestani.ali.edu.vanier.tool.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class SystemAdministratorTest {

    @Test
    public void testNewDefaultSystemAdministrator_example() {
        String input = "ABC Corp.";
        SystemAdministrator result = SystemAdministrator.newDefaultSystemAdministrator(input);
        Duration tolerance = Duration.ofSeconds(5);
        Assertions.assertTrue(result != null
                && result.getType().equals(UserType.SYSTEM_ADMINISTRATOR)
                && result.getName().equals(input + " System Administrator")
                && Duration.between(result.getCreatedAt(), Util.getNow()).abs().compareTo(tolerance) <= 0
        );
    }

    @Test
    public void testNewDefaultSystemAdministrator_null() {
        String input = null;
        Assertions.assertThrows(NullPointerException.class, () -> SystemAdministrator.newDefaultSystemAdministrator(input));
    }
}
