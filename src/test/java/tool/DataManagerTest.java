package tool;

import ca.shahrestani.ali.edu.vanier.businesslogic.Organizer;
import ca.shahrestani.ali.edu.vanier.tool.DataManager;
import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * Example tests, DataManager is not meant for unit testings
 */
public class DataManagerTest {

    @Test
    public void testRegisterFactory() {
        Class<Organizer> input = Organizer.class;
        DataManager.registerFactory(input, new Organizer.OrganizerFactory());
        Map<String, SavableFactory<? extends Savable>> result = DataManager.getFactories();

        Assertions.assertNotNull(result.get(input.getSimpleName()));
    }

    @Test
    public void testLoadSavable() {
        Class<Organizer> classInput = Organizer.class;
        SavableFactory<Organizer> factoryInput = new Organizer.OrganizerFactory();
        DataManager.registerFactory(classInput, factoryInput);
        System.out.printf(DataManager.getFactories().keySet().toString());

        Assertions.assertDoesNotThrow(() -> DataManager.loadSavable(classInput, "", null));
        Assertions.assertDoesNotThrow(() -> DataManager.loadSavable(classInput.getSimpleName(), "", null));
    }
}
